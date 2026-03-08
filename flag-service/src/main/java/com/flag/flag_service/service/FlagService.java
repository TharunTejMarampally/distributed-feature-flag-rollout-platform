package com.flag.flag_service.service;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flag.flag_service.entity.Feature;
import com.flag.flag_service.entity.Flag;
import com.flag.flag_service.repository.FlagRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Service
public class FlagService {

    private final FlagRepository flagRepository;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public FlagService(FlagRepository flagRepository) {
        this.flagRepository = flagRepository;
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

}
