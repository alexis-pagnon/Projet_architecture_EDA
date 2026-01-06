// Accès à la BDD et gestion des étudiants

package com.insa.fr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.insa.fr.entity.Students;
import com.insa.fr.mappers.StudentMapper;

@Service
public class Services_implements implements Services_Interface {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final StudentMapper studentMapper = new StudentMapper();

    @Override
    public int createStudent(Students stud) {
        String sql = "INSERT INTO students (nom, prenom, mail, formation) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            stud.getNom(), 
            stud.getPrenom(), 
            stud.getMail(), 
            stud.getFormation()
        );
    }

    @Override
    public List<Students> getStudents() {
        String sql = "SELECT id, nom, prenom, mail, formation FROM students";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        
        List<Students> students = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            students.add(studentMapper.mapping(new Students(), row));
        }
        return students;
    }

}
