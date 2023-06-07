package com.skomane.student.service.impl;

import com.skomane.student.constants.StudentConstants;
import com.skomane.student.dto.StudentDto;
import com.skomane.student.exceptions.StudentAlreadyExistsException;
import com.skomane.student.exceptions.StudentDoesNotExistException;
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

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public List<Student> getAllStudent() {
        log.info("Inside getAllStudent() {} ");

        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new StudentDoesNotExistException();
        }
    }

    @Override
    public Student getSingleStudentById(Long studentId) {
        log.info("Inside getSingleStudentById() {} ", studentId);

        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentDoesNotExistException());
    }

    @Override
    public ResponseEntity<String> updateStudent(StudentDto student, Long studentId) {
        log.info("Inside updateStudent() {} ", student);

        try {
            Student existingStudent = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentDoesNotExistException());

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

    @Override
    public ResponseEntity<String> deleteStudent(Long studentId) {
        log.info("Inside updateStudent() {} ", studentId);

        try {
            Student existingStudent = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentDoesNotExistException());

            studentRepository.delete(existingStudent);

            return StudentUtils.getResponseEntity("Student deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Student getStudentFromMap(StudentDto student) {
        Student newStudent = new Student();
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setAge(student.getAge());
        newStudent.setPhone(student.getPhone());
        newStudent.setEmail(student.getEmail());
        newStudent.setPassword(setPasswordEncode(student.getPassword()));
        return newStudent;
    }

    private String setPasswordEncode(String password) {
        return passwordEncoder.encode(password);
    }

}
