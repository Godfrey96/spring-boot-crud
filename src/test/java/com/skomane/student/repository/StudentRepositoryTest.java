package com.skomane.student.repository;

import com.skomane.student.dto.StudentDto;
import com.skomane.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private StudentDto studentDto;
    private StudentDto studentDto1;

    /**
     * Initializes the test setup before each test case.
     */
    @BeforeEach
    void init() {

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

    /**
     * Tests the saving of a student in the database.
     */
    @Test
    @DisplayName("should save the student in the database")
    void shouldSaveStudent() {

        Student newStudent = new Student();
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setPhone(studentDto.getPhone());
        newStudent.setAge(studentDto.getAge());
        newStudent.setEmail(studentDto.getEmail());
        newStudent.setPassword(studentDto.getPassword());

        Student saveStudent = studentRepository.save(newStudent);

        assertNotNull(saveStudent.getId());
        assertEquals("Godfrey", saveStudent.getFirstName());
    }

    /**
     * Tests the fetching of all students from the database.
     */
    @Test
    @DisplayName("should fetch all the students from the database")
    void shouldFetchAllStudents() {

        Student firstStudent = new Student();
        firstStudent.setFirstName(studentDto.getFirstName());
        firstStudent.setLastName(studentDto.getLastName());
        firstStudent.setPhone(studentDto.getPhone());
        firstStudent.setAge(studentDto.getAge());
        firstStudent.setEmail(studentDto.getEmail());
        firstStudent.setPassword(studentDto.getPassword());

        Student secondStudent = new Student();
        secondStudent.setFirstName(studentDto1.getFirstName());
        secondStudent.setLastName(studentDto1.getLastName());
        secondStudent.setPhone(studentDto1.getPhone());
        secondStudent.setAge(studentDto1.getAge());
        secondStudent.setEmail(studentDto1.getEmail());
        secondStudent.setPassword(studentDto1.getPassword());

        studentRepository.save(firstStudent);
        studentRepository.save(secondStudent);

        List<Student> students = studentRepository.findAll();

        assertNotNull(students);
        assertEquals(2, students.size());
    }

    /**
     * Tests the retrieval of a student by ID from the database.
     */
    @Test
    @DisplayName("should return student by id")
    void shouldReturnStudentById() {

        Student newStudent = new Student();
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setPhone(studentDto.getPhone());
        newStudent.setAge(studentDto.getAge());
        newStudent.setEmail(studentDto.getEmail());
        newStudent.setPassword(studentDto.getPassword());

        Student saveStudent = studentRepository.save(newStudent);

        Student getStudent = studentRepository.findById(saveStudent.getId()).get();

        assertNotNull(getStudent);
        assertEquals("Godfrey", getStudent.getFirstName());
    }

    /**
     * Tests the update of an existing student's first name in the database.
     */
    @Test
    @DisplayName("should update an existing student from the database")
    void shouldUpdateStudentFirstName() {

        Student newStudent = new Student();
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setPhone(studentDto.getPhone());
        newStudent.setAge(studentDto.getAge());
        newStudent.setEmail(studentDto.getEmail());
        newStudent.setPassword(studentDto.getPassword());

        Student saveStudent = studentRepository.save(newStudent);

        Student updateStudent = studentRepository.findById(saveStudent.getId()).get();

        assertNotNull(updateStudent);
        assertEquals("Godfrey", updateStudent.getFirstName());
    }

    /**
     * Tests the deletion of an existing student from the database.
     */
    @Test
    @DisplayName("should delete an existing student from the database")
    void shouldDeleteStudent() {
        Student newStudent = new Student();
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setPhone(studentDto.getPhone());
        newStudent.setAge(studentDto.getAge());
        newStudent.setEmail(studentDto.getEmail());
        newStudent.setPassword(studentDto.getPassword());

        Student saveStudent = studentRepository.save(newStudent);

        Long studentId = saveStudent.getId();

        studentRepository.delete(saveStudent);

        List<Student> students = studentRepository.findAll();

        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        assertEquals(0, students.size());
        assertThat(optionalStudent).isEmpty();

    }

}
