package com.insa.fr.services;

import com.insa.fr.entity.Students;
import java.util.List;

public interface Services_Interface {
    
   public List<Students> getStudents(); 
   public int createStudent(Students stud);

}
