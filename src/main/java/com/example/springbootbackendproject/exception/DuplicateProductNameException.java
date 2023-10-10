package com.example.springbootbackendproject.exception;

public class DuplicateProductNameException  extends RuntimeException{
    public DuplicateProductNameException(String s){
        super(s);

    }
}
