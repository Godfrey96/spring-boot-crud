package com.skomane.student.exceptions;

public class StudentAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StudentAlreadyExistsException() {
        super("The email provided is already taken");
    }
}
