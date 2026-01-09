package com.insa.fr.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.request}")
    private String requestTopic;

    @Value("${kafka.topic.response}")
    private String responseTopic;

    @Bean
    public NewTopic studentsRequestTopic() {
        return TopicBuilder.name(requestTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic studentsResponseTopic() {
        return TopicBuilder.name(responseTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
