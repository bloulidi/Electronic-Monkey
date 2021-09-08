package com.stackroute.orderservice.exception;

public class OrderAlreadyExistsException extends RuntimeException {
    private String message = "Order already exists!";
    public OrderAlreadyExistsException(){
        super("Order already exists!");
    }
    public OrderAlreadyExistsException(String message){
        super(message);
        this.message = message;
    }
}