package com.stackroute.catalog.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    private String message = "Product already exists!";
    public ProductAlreadyExistsException(){
        super("Product already exists!");
    }
    public ProductAlreadyExistsException(String message){
        super(message);
        this.message = message;
    }
}