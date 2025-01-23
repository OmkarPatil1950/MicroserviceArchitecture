package com.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
   
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("user-registration")
                .partitions(3)          // Set to the number of partitions you want (e.g., 3)
                .replicas(1)            // Set to the number of replicas (e.g., 1 for a simple setup)
                .build();
    }
   
}