package com.skomane.student.utils;

import com.skomane.student.dto.StudentDto;
import com.skomane.student.entity.Student;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StudentMapper {

    private final PasswordEncoder passwordEncoder;

    public StudentMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public static Student mapStudentDtoToStudent(Student newStudent, StudentDto student) {
        if (newStudent  == null) {
            newStudent = new Student();
        }
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setAge(student.getAge());
        newStudent.setPhone(student.getPhone());
        newStudent.setEmail(student.getEmail());
        newStudent.setPassword(student.getPassword());
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
