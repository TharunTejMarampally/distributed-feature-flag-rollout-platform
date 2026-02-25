package com.admin.service.services;

import com.admin.service.entities.Flag;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate,
                                ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUser(String topic, Flag flag) {
        try {
            String json = objectMapper.writeValueAsString(flag);

            kafkaTemplate.send(topic, json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            System.err.println("Kafka send failed: " + ex.getMessage());
                        } else {
                            System.out.println("Message sent to topic: " + topic);
                        }
                    });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
