package com.stackroute.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> userAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return new ResponseEntity<String>("User already exists!", HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> userNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<String>("User not found!", HttpStatus.NOT_FOUND);
    }
}
