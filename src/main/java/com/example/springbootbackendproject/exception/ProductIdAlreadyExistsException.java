package com.example.springbootbackendproject.exception;

public class ProductIdAlreadyExistsException extends RuntimeException{
    public ProductIdAlreadyExistsException(String s){
        super(s);
    }
}

