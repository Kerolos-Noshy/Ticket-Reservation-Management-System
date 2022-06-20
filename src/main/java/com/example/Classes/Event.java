package com.example.Classes;

import com.example.Exceptions.NotFound;
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

    public static boolean isEventBooked(String eventName, String clientName, Statement statement) {
        ResultSet resultSet;
        try {
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

    public static void retrieveEvent(String categoryName,Label label, Statement statement) {
        String events ="";
        int counter = 1;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM events WHERE category ='" + categoryName + "'");
            while (resultSet.next()) {
                String eventName = resultSet.getString(2);
                events += counter + "- " + eventName + "\n";
                counter++;
            }
        } catch (SQLException e) {
            System.out.println("\nNo Events in this Category");
            throw new RuntimeException(e);
        }
        label.setText(events);
    }

    public static boolean isEventExist(String eventName, Statement statement) {
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM events");
            while(resultSet.next()) {
                if (resultSet.getString(2).equals(eventName))
                    return true;
            }
        } catch (SQLException e) {
            System.out.println("Event Not Found");
            throw new RuntimeException(e);
        }
        return false;
    }

    public static String[] getEventData(String eventName, Statement statement){
        String[] eventData =  null;
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM events WHERE eventName = '" + eventName + "'");
            resultSet.next();
            String location = resultSet.getString(3);
            int availableTickets = resultSet.getInt(4);
            int day = resultSet.getInt(5);
            int month = resultSet.getInt(6);
            int year = resultSet.getInt(7);
            String startTime = resultSet.getString(8);
            String endTime = resultSet.getString(9);
            eventData = new String[]{location, String.valueOf(availableTickets), startTime, endTime, ("Date: "+ day+ "/" + month + "/" + year)};

        } catch (SQLException e) {
            System.out.println("Event Not Found");
            throw new RuntimeException(e);
        }
        return eventData;
    }

    public static void searchEvent(String eventName, Label label, Statement statement) {
        int counter = 1;
        String events = "";
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM events WHERE eventName LIKE '%" + eventName + "%'");
            while (resultSet.next()) {
                String eventName1 = resultSet.getString(2);
                events += counter + "- " + eventName1 + "\n";
                counter++;
            }
            if (counter == 1) {
                try {
                    throw new NotFound(eventName);
                } catch (NotFound e2) {
                    label.setText(e2.getMessage());
                }
            } else {
                label.setText(events);
            }
        } catch (SQLException e) {
            System.out.println("\nNo Events Found");
            throw new RuntimeException(e);
        }
    }
}