// Message Kafka sortant

package com.insa.fr.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.response}")
    private String responseTopic;

    public void sendMessage(String key, Object message) {
        kafkaTemplate.send(responseTopic, key, message);
    }

    public void sendToTopic(String topic, String key, Object message) {
        kafkaTemplate.send(topic, key, message);
    }
}
