package com.skomane.student.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skomane.student.dto.StudentDto;
import com.skomane.student.entity.Student;
import com.skomane.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;
    private StudentDto studentDto;
    private StudentDto studentDto1;

    @BeforeEach
    void setUp() throws Exception {
        studentDto = new StudentDto();
        studentDto.setFirstName("Godfrey");
        studentDto.setLastName("Ngwatle");
        studentDto.setAge(15);
        studentDto.setPhone("0720461090");
        studentDto.setEmail("godfrey@gmail.com");
        studentDto.setPassword("123456789");

        studentDto1 = new StudentDto();
        studentDto1.setFirstName("Mogau");
        studentDto1.setLastName("Mohlala");
        studentDto1.setAge(20);
        studentDto1.setPhone("0820888267");
        studentDto1.setEmail("mogau@gmail.com");
        studentDto1.setPassword("123456789");
    }

    @Test
    @DisplayName("should add new student in the database")
    void shouldAddNewStudent() throws Exception {

        Student newStudent = new Student();
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setPhone(studentDto.getPhone());
        newStudent.setAge(studentDto.getAge());
        newStudent.setEmail(studentDto.getEmail());
        newStudent.setPassword(passwordEncoder.encode(studentDto.getPassword()));

        when(studentService.addNewStudent(any(StudentDto.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Student updated successfully"));

        mockMvc.perform(post("/api/v1/student/new-student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.firstName", is(studentDto.getFirstName())))
                .andExpect((ResultMatcher) jsonPath("$.lastName", is(studentDto.getLastName())))
                .andExpect((ResultMatcher) jsonPath("$.phone", is(studentDto.getPhone())))
                .andExpect((ResultMatcher) jsonPath("$.age", is(studentDto.getAge())))
                .andExpect((ResultMatcher) jsonPath("$.email", is(studentDto.getEmail())))
                .andExpect((ResultMatcher) jsonPath("$.password", is(studentDto.getPassword())));
    }

}
