package com.example.Classes;

public abstract class User
{
    private String username;
    private String email;
    private String password;

    protected User(String username, String email, String password)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}