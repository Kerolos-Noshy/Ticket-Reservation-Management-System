package com.example.Exceptions;

public class TooYoung extends Exception{
    public TooYoung() {
        super("You need to be 20 Years or older");
    }
}
