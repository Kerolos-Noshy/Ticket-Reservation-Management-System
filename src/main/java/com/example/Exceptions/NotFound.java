package com.example.Exceptions;

public class NotFound extends Exception{
    public NotFound(String msg) {
        super(msg + " Not Found");
    }
}
