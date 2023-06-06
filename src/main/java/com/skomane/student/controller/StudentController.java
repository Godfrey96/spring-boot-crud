package com.skomane.student.controller;

import com.skomane.student.constants.StudentConstants;
import com.skomane.student.dto.StudentDto;
import com.skomane.student.exceptions.StudentAlreadyExistsException;
import com.skomane.student.exceptions.StudentDoesNotExistException;
import com.skomane.student.model.Student;
import com.skomane.student.service.StudentService;
import com.skomane.student.utils.StudentUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @ExceptionHandler({StudentAlreadyExistsException.class})
    public ResponseEntity<String> handleStudentTaken() {
        return new ResponseEntity<String>("The email provided is already in use by another student.", HttpStatus.CONFLICT);
    }

    @PostMapping("/new-student")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody StudentDto student) {
        return new ResponseEntity<>(studentService.addNewStudent(student), HttpStatus.CREATED);
    }

    @ExceptionHandler({StudentDoesNotExistException.class})
    public ResponseEntity<String> handleStudentDoesNotExist() {
        return new ResponseEntity<String>("The student you are looking does not exist.", HttpStatus.CONFLICT);
    }

    @GetMapping("/get-all-students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudent(), HttpStatus.OK);
    }

    @GetMapping("/get-single-student/{studentId}")
    public ResponseEntity<Student> getSingleStudent(@PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(studentService.getSingleStudentById(studentId), HttpStatus.OK);
    }

    @PutMapping("/update-student/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") Long studentId, @Valid @RequestBody StudentDto student) {
        return new ResponseEntity<>(studentService.updateStudent(student, studentId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-student/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId) {
        try {
            return studentService.deleteStudent(studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
