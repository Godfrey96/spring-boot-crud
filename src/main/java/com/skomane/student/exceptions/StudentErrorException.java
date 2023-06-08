package com.skomane.student.exceptions;

public class StudentErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StudentErrorException() {
        super("Error while fetching students");
    }
}
