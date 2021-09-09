package com.stackroute.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {
    String message = "Order not found!";

    public OrderNotFoundException() {
        super("Order not found!");
    }

    public OrderNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}