package com.insa.fr.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Kafka Producer for sending messages to the integration service
 */
@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.request}")
    private String requestTopic;

    /**
     * Send a message to the request topic
     * @param key message key
     * @param message message payload
     */
    public void sendMessage(String key, Object message) {
        System.out.println("Sending message to topic " + requestTopic + ": " + message);
        kafkaTemplate.send(requestTopic, key, message);
    }

    /**
     * Request to get all students
     */
    public void requestGetStudents() {
        Map<String, Object> message = Map.of("action", "getStudents");
        sendMessage("getStudents", message);
    }

    /**
     * Request to add a new student
     * @param nom student last name
     * @param prenom student first name
     * @param mail student email
     * @param formation student course/formation
     */
    public void requestAddStudent(String nom, String prenom, String mail, String formation) {
        Map<String, Object> message = Map.of(
            "action", "addStudent",
            "nom", nom,
            "prenom", prenom,
            "mail", mail,
            "formation", formation
        );
        sendMessage("addStudent", message);
    }
}