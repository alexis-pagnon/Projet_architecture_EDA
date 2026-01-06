// Message Kafka entrant

package com.insa.fr.kafka;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.insa.fr.entity.Students;
import com.insa.fr.services.Services_Interface;

@Service
public class Consumer {

    @Autowired
    private Services_Interface studentsService;

    @Autowired
    private Producer producer;

    @KafkaListener(topics = "${kafka.topic.request}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> message) {
        System.out.println("Message received: " + message);

        /*
        Expected message format:
        {
            "action": "getStudents" | "addStudent",
            // For addStudent
            "nom": "name",
            "prenom": "surname",
            "mail": "email",
            "formation": "course"
        }

        */

        if (message == null || !message.containsKey("action")) {
            System.out.println("Invalid message format, ignoring.");
            return;
        }

        String action = (String) message.get("action");

        switch (action) {
            case "getStudents" -> {
                // Call the service to get all students
                List<Students> students = studentsService.getStudents();
                System.out.println("Fetched " + (students != null ? students.size() : 0) + " students");
                
                // Write the response back to Kafka
                producer.sendMessage("getStudents-response", students);
            }

            case "addStudent" -> {
                // Create a new student from message data
                try {
                    Students newStudent = new Students();
                    newStudent.setNom((String) message.get("nom"));
                    newStudent.setPrenom((String) message.get("prenom"));
                    newStudent.setMail((String) message.get("mail"));
                    newStudent.setFormation((String) message.get("formation"));

                    int result = studentsService.createStudent(newStudent);
                    System.out.println("Student created with result: " + result);
                    
                    // Write the response back to Kafka
                    producer.sendMessage("addStudent-response", Map.of("success", result > 0));

                } catch (Exception e) {
                    System.out.println("Error creating student: " + e.getMessage());
                    producer.sendMessage("error", "Error creating student: " + e.getMessage());
                }
            }

            default -> {
                System.out.println("Unknown action: " + action);
                producer.sendMessage("error", "Unknown action: " + action);
            }
        }
    }
}
