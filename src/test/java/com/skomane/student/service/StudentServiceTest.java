package com.skomane.student.service;

import com.skomane.student.dto.StudentDto;
import com.skomane.student.entity.Student;
import com.skomane.student.repository.StudentRepository;
import com.skomane.student.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Mock
    private PasswordEncoder passwordEncoder;

    private Student student1;
    private Student student2;
    private StudentDto studentDto1;
    private StudentDto studentDto2;

    /**
     * Initializes the test setup before each test case.
     */
    @BeforeEach
    void init() {

        student1 = new Student();
        student1.setId(1L);

        student2 = new Student();
        student2.setId(2L);

        studentDto1 = new StudentDto();
        studentDto1.setFirstName("Godfrey");
        studentDto1.setLastName("Ngwatle");
        studentDto1.setAge(15);
        studentDto1.setPhone("0720461090");
        studentDto1.setEmail("godfrey@gmail.com");
        studentDto1.setPassword("123456789");

        studentDto2 = new StudentDto();
        studentDto2.setFirstName("Godfrey");
        studentDto2.setLastName("Ngwatle");
        studentDto2.setAge(15);
        studentDto2.setPhone("0720461090");
        studentDto2.setEmail("godfrey@gmail.com");
        studentDto2.setPassword("123456789");
    }

    /**
     * Tests the success of adding a new student.
     */
    @Test
    @DisplayName("should add a new student")
    void shouldAddNewStudentSuccess() {

        when(studentRepository.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<String> response = studentService.addNewStudent(studentDto1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("{\"message\":\"New student added successfully\"}", response.getBody());
        verify(studentRepository, times(1)).findByEmail(anyString());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    /**
     * Tests the case where the email already exists in the student repository.
     */
    @Test
    @DisplayName("should test email exists")
    void shouldTestEmailExists() {

        when(studentRepository.findByEmail(anyString())).thenReturn(new Student());

        ResponseEntity<String> response = studentService.addNewStudent(studentDto1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Student email already exists\"}", response.getBody());
        verify(studentRepository, times(1)).findByEmail(anyString());
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    /**
     * Tests the case where an exception occurs while adding a new student.
     */
    @Test
    @DisplayName("should test add student exception if errors occurs")
    void shouldTestAddStudentException() {

        when(studentRepository.findByEmail(anyString())).thenThrow(new RuntimeException());

        ResponseEntity<String> response = studentService.addNewStudent(studentDto1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"message\":\"Something Went Wrong\"}", response.getBody());
        verify(studentRepository, times(1)).findByEmail(anyString());
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    /**
     * Tests the fetching of all students and verifies the size of the returned list.
     */
    @Test
    @DisplayName("Should fetch all students of size 2")
    void shouldFetchAllStudent() {

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepository.findAll()).thenReturn(studentList);

        List<Student> students = studentService.getAllStudent();

        assertEquals(2, students.size());
        assertNotNull(students);
    }

    /**
     * Tests the retrieval of a single student by ID.
     */
    @Test
    @DisplayName("Should get single student by id")
    void shouldGetStudentById() {

        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(student1));

        Student existingStudent = studentService.getSingleStudentById(1L);

        assertNotNull(existingStudent);
        assertEquals(existingStudent.getId(), 1L);
    }

    /**
     * Tests the deletion of an existing student.
     */
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
