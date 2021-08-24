package com.stackroute.userservice.exception;

public class UserNotFoundException extends RuntimeException {
    String message = "User not found!";
    public UserNotFoundException() {
        super("User not found!");
    }
    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}