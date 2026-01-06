package com.insa.fr.services;

import com.insa.fr.entity.Students;
import java.util.List;

/**
 *
 * @author herve
 */
public interface Services_Interface {
    
   public List<Students> getStudent(String id);
   public List<Students> getStudents(); 
   public int createStudent(Students stud);

}
