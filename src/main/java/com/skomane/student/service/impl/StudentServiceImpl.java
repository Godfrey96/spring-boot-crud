package com.skomane.student.service.impl;

import com.skomane.student.constants.StudentConstants;
import com.skomane.student.dto.StudentDto;
import com.skomane.student.exceptions.StudentDoesNotExistException;
import com.skomane.student.exceptions.StudentErrorException;
import com.skomane.student.model.Student;
import com.skomane.student.repository.StudentRepository;
import com.skomane.student.service.StudentService;
import com.skomane.student.utils.StudentUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * This method is used to add a new student in the application
     * @param student the StudentDto object representing the new student to be added
     * @return A ResponseEntity<String> indicating the result of the operation
     * If the student is added successfully, it returns a success message with HTTP status 201 (Created)
     * If the student with the given email already exists, it returns an error message with HTTP status 400 (Bad Request)
     * If an exception occurs during the operation, it returns an error message with HTTP status 500 (Internal Server Error)
     */
    @Override
    public ResponseEntity<String> addNewStudent(StudentDto student) {
        log.info("Inside addNewStudent() {} ", student);

        try {
            Student findStudent = studentRepository.findByEmail(student.getEmail());
            if (findStudent == null) {
                studentRepository.save(getStudentFromMap(student));
                return StudentUtils.getResponseEntity("New student added successfully", HttpStatus.CREATED);
            } else {
                return StudentUtils.getResponseEntity("Student email already exists", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method is used to retrieves a list of all students
     * @return A list of Student objects representing all the students available in the application
     * @throws StudentErrorException if an error occurs while retrieving the students
     */
    @Override
    public List<Student> getAllStudent() {
        log.info("Inside getAllStudent() {} ");

        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new StudentErrorException();
        }
    }

    /**
     * This method is used to retrieves a single student by their ID
     * @param studentId The ID of the student to retrieve
     * @return The Student object representing the student with the given ID
     * @throws StudentDoesNotExistException if the student with the given ID does not exist.
     */
    @Override
    public Student getSingleStudentById(Long studentId) {
        log.info("Inside getSingleStudentById() {} ", studentId);

        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentDoesNotExistException(studentId));
    }

    /**
     * This method is used to update the existing student in the application
     * @param student The StudentDto object representing the updated student information
     * @param studentId The ID of the student to update
     * @return A ResponseEntity<String> indicating the result of the operation
     * If the student is updated successfully, it returns a success message with HTTP status 200 (OK)
     * If the student with the given ID does not exist, it throws a StudentDoesNotExistException
     * If an exception occurs during the operation, it returns an error message with HTTP status 500 (Internal Server Error)
     */
    @Override
    public ResponseEntity<String> updateStudent(StudentDto student, Long studentId) {
        log.info("Inside updateStudent() {} ", student);

        try {
            Student existingStudent = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentDoesNotExistException(studentId));

            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setAge(student.getAge());
            existingStudent.setPhone(student.getPhone());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setPassword(setPasswordEncode(student.getPassword()));

            studentRepository.save(existingStudent);
            return StudentUtils.getResponseEntity("Student updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method is used to delete and existing student in the application
     * @param studentId The ID of the student to delete
     * @return A ResponseEntity<String> indicating the result of the operation
     * If the student is deleted successfully, it returns a success message with HTTP status 200 (OK)
     * If the student with the given ID does not exist, it throws a StudentDoesNotExistException
     * If an exception occurs during the operation, it returns an error message with HTTP status 500 (Internal Server Error)
     */
    @Override
    public ResponseEntity<String> deleteStudent(Long studentId) {
        log.info("Inside updateStudent() {} ", studentId);

        try {
            Student existingStudent = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentDoesNotExistException(studentId));

            studentRepository.delete(existingStudent);

            return StudentUtils.getResponseEntity("Student deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Converts a StudentDto object to a Student object
     * @param student The StudentDto object to convert
     * @return A new Student object with the information from the StudentDto object
     */
    public Student getStudentFromMap(StudentDto student) {
        Student newStudent = new Student();
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setAge(student.getAge());
        newStudent.setPhone(student.getPhone());
        newStudent.setEmail(student.getEmail());
        newStudent.setPassword(setPasswordEncode(student.getPassword()));
        return newStudent;
    }

    /**
    * This method is used to encodes a password using the password encoder
     * @param password The password to encode
     * @return The encoded version of the password
     * */
    private String setPasswordEncode(String password) {
        return passwordEncoder.encode(password);
    }

}
