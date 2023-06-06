package com.skomane.student.service;

import com.skomane.student.dto.StudentDto;
import com.skomane.student.model.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {

    Student addNewStudent(StudentDto student);
    List<Student> getAllStudent();
    Student getSingleStudentById(Long studentId);
    Student updateStudent(StudentDto student, Long studentId);
    ResponseEntity<String> deleteStudent(Long studentId);
}
