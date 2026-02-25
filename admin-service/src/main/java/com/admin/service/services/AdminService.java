package com.admin.service.services;

import com.admin.service.entities.Flag;
import com.admin.service.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final KafkaProducerService kafkaProducerService;
    private static final String KAFKA_TOPIC = "Flag";

    public AdminService(AdminRepository adminRepository,
                        KafkaProducerService kafkaProducerService) {
        this.adminRepository = adminRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Flag createFlag(Flag flag) {

        Flag response = adminRepository.save(flag);

        kafkaProducerService.sendUser(KAFKA_TOPIC, response);

        return response;
    }

    public Flag updateFlag(Flag flag) {

        Optional<Flag> existingFlag = adminRepository.findById(flag.getId());

        if (existingFlag.isEmpty()) {
            throw new RuntimeException("Entity not found with id");
        }

        Flag response = adminRepository.save(flag);

        kafkaProducerService.sendUser(KAFKA_TOPIC, response);

        return response;
    }
}
