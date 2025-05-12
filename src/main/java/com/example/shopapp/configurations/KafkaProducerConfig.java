package com.example.shopapp.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerConfig {
    private final String topic = "user_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    public void sendMessage(String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }
}
