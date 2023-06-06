package com.skomane.student.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StudentUtils {

    private StudentUtils(){}

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<>("{\"message\":\""+ responseMessage +"\"}", httpStatus);
    }
}
