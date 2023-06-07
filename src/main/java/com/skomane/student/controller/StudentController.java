package com.skomane.student.controller;

import com.skomane.student.constants.StudentConstants;
import com.skomane.student.dto.StudentDto;
import com.skomane.student.exceptions.StudentAlreadyExistsException;
import com.skomane.student.exceptions.StudentDoesNotExistException;
import com.skomane.student.model.Student;
import com.skomane.student.service.StudentService;
import com.skomane.student.utils.StudentUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
@Tag(name = "Student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/new-student")
    public ResponseEntity<String> addStudent(@Valid @RequestBody StudentDto student) {
        try {
            return studentService.addNewStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<String> updateStudent(@PathVariable("studentId") Long studentId, @Valid @RequestBody StudentDto student) {
        try {
            return studentService.updateStudent(student, studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
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
