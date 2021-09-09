package com.stackroute.catalog.exception;

public class ProductNotFoundException extends RuntimeException {
    String message = "Product not found!";

    public ProductNotFoundException() {
        super("Product not found!");
    }

    public ProductNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}