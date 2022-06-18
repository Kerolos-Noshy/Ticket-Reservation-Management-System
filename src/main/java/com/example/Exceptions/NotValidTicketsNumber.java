package com.example.Exceptions;

public class NotValidTicketsNumber extends Exception {
    public NotValidTicketsNumber(String msg) {
        super(msg);
    }
}
