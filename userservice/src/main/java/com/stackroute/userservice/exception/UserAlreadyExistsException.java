package com.stackroute.userservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
    private String message = "User already exists!";

    public UserAlreadyExistsException() {
        super("User already exists!");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}