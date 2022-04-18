package com.example.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class handleException {
    public ResponseEntity handleUserNotFoundException(){
        return new ResponseEntity<>("UserNotFoundException", HttpStatus.NOT_FOUND);
    }
}
