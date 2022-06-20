package com.example.Classes;

import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Client
{
    private String clientName;
    private String id;
    private String gender;
    private String  clientMobile;
    private final int serialNumber;

    public Client(String clientName, String id, String gender, String clientMobile,int number) {
        this.clientName = clientName;
        this.id = id;
        this.gender = gender;
        this.clientMobile = clientMobile;
        serialNumber = number;
    }

    public static boolean isClientExist(String clientName, Statement statement) {
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM clients");
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(clientName))
                    return true;
            }
        } catch (SQLException e) {
            System.out.println("Client Not Found");
            throw new RuntimeException(e);
        }
        return false;
    }
}