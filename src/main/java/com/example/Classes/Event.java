package com.example.Classes;

import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Event
{
    private String eventName;
    private String location;
    private int availableTickets;
    private Date date;
    private String sTime;
    private String eTime;

    public Event(String eventName, String location, int availableTickets, Date date, String sTime, String eTime) {
        this.eventName = eventName;
        this.location = location;
        this.availableTickets = availableTickets;
        this.date = date;
        this.sTime = sTime;
        this.eTime = eTime;
    }

    public static boolean isEventBooked(String eventName, String clientName, Label label, Connection connection) {
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("select serialNumber from clients where clientName = '" + clientName + "'");
            resultSet.next();
            int clientSerialNumber = resultSet.getInt(1);
            resultSet = statement.executeQuery("select * from upcomingEvents where clientSerial = '" + clientSerialNumber + "'");
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(eventName))
                    return true;
            }
        } catch (SQLException e) {
            System.out.println("Client Not Found");
            throw new RuntimeException(e);
        }
        return false;
    }
}