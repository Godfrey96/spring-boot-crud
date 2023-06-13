package com.skomane.student.repository;

import com.skomane.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);
}
