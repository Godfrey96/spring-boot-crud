package com.skomane.student.service;

import com.skomane.student.config.ApplicationConfig;
import com.skomane.student.dto.StudentDto;
import com.skomane.student.model.Student;
import com.skomane.student.repository.StudentRepository;
import com.skomane.student.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentServiceImpl studentService;
    @Mock
    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;

    private Student student1;
    private Student student2;
    private StudentDto studentDto1;

    @BeforeEach
    void init() {
        student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("Mogau");
        student1.setLastName("Ngwatle");
        student1.setAge(25);
        student1.setPhone("0720461090");
        student1.setEmail("mogau@gmail.com");

        student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Skomane");
        student2.setLastName("Mohlala");
        student2.setAge(30);
        student2.setPhone("0720461090");
        student2.setEmail("skomane@gmail.com");

        studentDto1 = new StudentDto();
        studentDto1.setFirstName("Godfrey");
        studentDto1.setLastName("Ngwatle");
        studentDto1.setAge(15);
        studentDto1.setPhone("0720461090");
        studentDto1.setEmail("godfrey@gmail.com");
        studentDto1.setPassword("123456789");
//        studentDto1.setPassword(passwordEncoder.encode("123456789"));

        System.out.println("student-pass: " + studentDto1.getPassword());
    }

//    @Test
//    @DisplayName("Should save the student to database")
//    void shouldSaveStudent() {
//
////        when().thenReturn(new BCryptPasswordEncoder());
//
//        System.out.println("student: " + studentDto1);
//        student1.setPassword(passwordEncoder.encode("123456789"));
//
//        Student newStudent = studentService.addNewStudent(studentDto1);
//
//        assertNotNull(newStudent);
//        assertThat(newStudent.getFirstName()).isEqualTo("Godfrey");
//
//    }

    @Test
    @DisplayName("Should fetch all students od size 2")
    void shouldFetchAllStudent() {

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepository.findAll()).thenReturn(studentList);

        List<Student> students = studentService.getAllStudent();

        assertEquals(2, students.size());
        assertNotNull(students);
    }

    @Test
    @DisplayName("Should get single student by id")
    void shouldGetStudentById() {

        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(student1));

        Student existingStudent = studentService.getSingleStudentById(1L);

        assertNotNull(existingStudent);
        assertEquals(existingStudent.getId(), 1L);
    }

    @Test
    @DisplayName("Should delete an existing student")
    void shouldDeleteStudent() {

        Long studentId = 1L;
        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(student1));
        doNothing().when(studentRepository).delete(any(Student.class));

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).delete(student1);
    }

}
