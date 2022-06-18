package com.example.Classes;

public class Employee extends User
{
    private String employeeName;
    private int employeeAge;
    private String employeeMobile;

    public Employee(String employeeName, String username, String email, String password, String employeeMobile, int employeeAge)
    {
        super(username, email, password);
        this.employeeName = employeeName ;
        this.employeeAge = employeeAge ;
        this.employeeMobile = employeeMobile ;
    }
}