package com.skomane.student.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 30, message = "length must be between 2 and 20 characters")
    @NotBlank(message = "First name field cannot be empty")
    private String firstName;

    @Size(min = 3, max = 30, message = "length must be between 2 and 20 characters")
    @NotBlank(message = "Last name field cannot be empty")
    private String lastName;

    @Size(min = 10, max = 10, message = "length cannot be less or greater than 10")
    @NotNull(message = "Phone number field cannot be empty")
    private String phone;

    @NotNull(message = "Age field cannot be empty")
    private Integer age;

    @NotBlank(message = "Email is mandatory")
    @Email(regexp = "[\\w\\.]{2,50}@[\\w\\.]{2,20}", message = "email address is invalid")
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password field cannot be empty")
    private String password;
}
