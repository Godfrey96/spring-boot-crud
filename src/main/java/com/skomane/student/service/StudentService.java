package com.skomane.student.service;

import com.skomane.student.dto.StudentDto;
import com.skomane.student.entity.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {

    ResponseEntity<String> addNewStudent(StudentDto student);
    List<Student> getAllStudent();
    Student getSingleStudentById(Long studentId);
    ResponseEntity<String> updateStudent(StudentDto student, Long studentId);
    ResponseEntity<String> deleteStudent(Long studentId);
}
