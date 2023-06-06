package com.skomane.student.exceptions;

public class StudentDoesNotExistException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StudentDoesNotExistException() {
        super("No student found");
    }
}
