package com.skomane.student.controller;

import com.skomane.student.constants.StudentConstants;
import com.skomane.student.dto.StudentDto;
import com.skomane.student.model.Student;
import com.skomane.student.service.StudentService;
import com.skomane.student.utils.StudentUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
@Tag(name = "Student")
public class StudentController {

    private final StudentService studentService;

    /**
     * Adds a new student by processing the HTTP POST request
     * @param student The StudentDto object representing the new student to be added
     * @return A ResponseEntity<String> indicating the result of the operation
     * If the student is added successfully, it returns the response received from the studentService
     * If an exception occurs during the operation, it returns an error message with HTTP status 500 (Internal Server Error)
     */
    @PostMapping("/new-student")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> addStudent(@Valid @RequestBody StudentDto student) {
        try {
            return studentService.addNewStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Retrieves all students by processing the HTTP GET request
     * @return A ResponseEntity<List<Student>> containing the list of all students and HTTP status 200 (OK)
     */
    @GetMapping("/get-all-students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudent(), HttpStatus.OK);
    }

    /**
     * Retrieves a single student by their ID by processing the HTTP GET request
     * @param studentId The ID of the student to retrieve
     * @return A ResponseEntity<Student> containing the student with the given ID and HTTP status 200 (OK)
     */
    @GetMapping("/get-single-student/{studentId}")
    public ResponseEntity<Student> getSingleStudent(@PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(studentService.getSingleStudentById(studentId), HttpStatus.OK);
    }

    /**
     * Updates an existing student by processing the HTTP PUT request
     * @param studentId The ID of the student to update
     * @param student The StudentDto object containing the updated information for the student
     * @return A ResponseEntity<String> indicating the result of the operation
     * If the student is updated successfully, it returns the response received from the studentService
     * If an exception occurs during the operation, it returns an error message with HTTP status 500 (Internal Server Error)
     */
    @PutMapping("/update-student/{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable("studentId") Long studentId, @Valid @RequestBody StudentDto student) {
        try {
            return studentService.updateStudent(student, studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StudentUtils.getResponseEntity(StudentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Deletes a student by their ID by processing the HTTP DELETE request
     * @param studentId The ID of the student to delete
     * @return A ResponseEntity<String> indicating the result of the operation
     * If the student is deleted successfully, it returns the response received from the studentService
     * If an exception occurs during the operation, it returns an error message with HTTP status 500 (Internal Server Error)
     */
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
