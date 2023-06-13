package com.skomane.student.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class StudentDto {
    private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private String email;
    private String password;


}
