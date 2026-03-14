package com.flag.flag_service.service;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flag.flag_service.entity.Feature;
import com.flag.flag_service.entity.Flag;
import com.flag.flag_service.entity.UserValidationRequestDTO;
import com.flag.flag_service.repository.FlagRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FlagService {

    private final FlagRepository flagRepository;
    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public FlagService(FlagRepository flagRepository, CacheManager cacheManager) {
        this.flagRepository = flagRepository;
        this.cacheManager = cacheManager;
    }
    @PostConstruct
    public void loadAllFlagsIntoCache() {
        Cache cache = cacheManager.getCache("flags");
        flagRepository.findAll().forEach(feature -> {
            cache.put(feature.getFlagId(), feature);
        });

    }
   @KafkaListener(topics = "Flag", groupId = "flag-service-group")
    public void consumeKafkaMessages(String message) throws Exception {

        System.out.println("Raw Kafka Message: " + message);

        Flag flag = objectMapper.readValue(message, Flag.class);

        Feature feature = new Feature();

        Map<String, Object> flagInfoMap = new HashMap<>();
        flagInfoMap.put("flagName", flag.getFlagName());
        flagInfoMap.put("flagAction", flag.getFlagAction());
        flagInfoMap.put("enabled", flag.isEnabled());
        flagInfoMap.put("rollOutPercentage", flag.getRollOutPercentage());
        flagInfoMap.put("flagConfigurations", flag.getFlagConfigurations());

        feature.setFlagId(flag.getId());
        feature.setFlagInfo(flagInfoMap);

        flagRepository.save(feature);
    }

    @Cacheable(value = "flags", key = "#flagKey")
    public Feature getFlag(UUID flagKey) {
        return flagRepository.findByFlagId(flagKey);
    }
    public boolean validateUser(UserValidationRequestDTO userValidationRequestDTO){
        String flagId=userValidationRequestDTO.getFlagId();
        Feature feature = getFlag(UUID.fromString(flagId));
        if (feature == null) {
            throw new RuntimeException("Flag not found for id: " + flagId);
        }
        String userId = userValidationRequestDTO.getUserId();
        Map<String, Object> userAttributes = userValidationRequestDTO.getUserInfo();
        Map<String, Object> flagConfigurations = (Map<String, Object>) feature.getFlagInfo().get("flagConfigurations");
        if(!matchUserAttributes(userAttributes,flagConfigurations)){
            return false;
        }
        Boolean enabled = (Boolean) feature.getFlagInfo().get("enabled");
        if (!enabled) {
            return false;
        }
        double rolloutPercentage =
                ((Number) feature.getFlagInfo().get("rollOutPercentage")).doubleValue();

        return isUserInRollout(feature.getFlagId().toString(), userId, rolloutPercentage);
    }


    private boolean isUserInRollout(String flagKey, String userId, double rolloutPercentage) {

        int bucket = getDeterministicBucket(flagKey, userId);

        return bucket < rolloutPercentage;
    }

    private int getDeterministicBucket(String flagKey, String userId) {
        try {
            String key = flagKey + ":" + userId;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(key.getBytes(StandardCharsets.UTF_8));

            int hashInt = ((hash[0] & 0xff) << 24)
                    | ((hash[1] & 0xff) << 16)
                    | ((hash[2] & 0xff) << 8)
                    | (hash[3] & 0xff);

            hashInt = Math.abs(hashInt);

            return hashInt % 100; // bucket 0-99

        } catch (Exception e) {
            throw new RuntimeException("Hashing error", e);
        }
    }
    public boolean matchUserAttributes(Map<String, Object> userAttributes,
                                       Map<String, Object> flagConfigurations) {

        for (Map.Entry<String, Object> entry : flagConfigurations.entrySet()) {

            String key = entry.getKey();
            Object expectedValue = entry.getValue();

            if (!userAttributes.containsKey(key)) {
                return false;
            }

            Object userValue = userAttributes.get(key);

            if (!expectedValue.equals(userValue)) {
                return false;
            }
        }

        return true;
    }
}
