package com.insa.fr.services;

import com.insa.fr.entity.Students;
import com.insa.fr.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service implementation using Kafka for communication with the integration service
 * @author herve (modified for Kafka)
 */
@Service
public class Services_implements implements Services_Interface {
    
    @Autowired
    private KafkaProducer kafkaProducer;


    
    /**
     * Get all students via Kafka
     * @return list of students
     */
    @Override
    public int getStudents() {
        try {

            // Send request to integration service via Kafka
            kafkaProducer.requestGetStudents();

            return 1;
            
        } catch (Exception e) {
            System.err.println("Error getting students via Kafka: " + e.getMessage());
            return -1;
        }
    }


    /**
     * Create a new student via Kafka
     * @param stud student data
     * @return 1 if request was sent successfully, -1 otherwise
     */
    @Override
    public int createStudent(Students stud) {
        try {
            
            // Send create request to integration service via Kafka
            kafkaProducer.requestAddStudent(
                stud.getNom(),
                stud.getPrenom(),
                stud.getMail(),
                stud.getFormation()
            );
            return 1;
        } catch (Exception e) {
            System.err.println("Error creating student via Kafka: " + e.getMessage());
            return -1;
        }
    }


}
