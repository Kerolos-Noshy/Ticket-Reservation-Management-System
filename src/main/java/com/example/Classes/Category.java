package com.example.Classes;

import com.example.Exceptions.NotFound;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Category {
    private String categoryName;

    private final ArrayList<Event> events = new ArrayList<>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }


    public static String[] getEventData(String eventName, Connection connection){
        String[] eventData =  null;
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
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

    public static void searchEvent(String eventName, Label label,Connection connection) {
        int counter = 1;
        String events = "";
        try {
            Statement statement = connection.createStatement();
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

    public static boolean isEventExist(String eventName, Label label, Connection connection) {
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
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

    public static boolean isCategoryExist(String categoryName, Label label, Connection connection) {
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM categories");
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(categoryName))
                    return true;
            }
        } catch (SQLException e) {
            System.out.println("Category Not Found");
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void viewEvent(String eventName,Label label, Connection connection) {
        String event ="";
        if (isEventExist(eventName,label ,connection)){
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM events WHERE eventName = '" + eventName + "'");

                resultSet.next();
                String eventName1 = resultSet.getString(2);
                String location = resultSet.getString(3);
                int availableTickets = resultSet.getInt(4);
                int day = resultSet.getInt(5);
                int month = resultSet.getInt(6);
                int year = resultSet.getInt(7);
                String startTime = resultSet.getString(8);
                String endTime = resultSet.getString(9);
                Date date = new Date(year, month,day);

                event += "\nEvent Name: " + eventName1 + "\nLocation: " + location +
                        "\nNumber of Available Tickets: " + availableTickets+
                        "\nDate: " + LocalDate.of(year, month, day).getDayOfWeek().name()+"  "+ date.getDate()+
                        "/"+(date.getMonth())+"/"+ date.getYear() +"\nStart Time: " + startTime+
                        "\nEnd Time: " + endTime;
                label.setText(event);


            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                if (event.equals(""))
                    throw new NotFound(eventName);
            } catch (NotFound e2) {
                label.setText(e2.getMessage());
            }
        }

    }

    public void retrieveEvent(String categoryName,Label label, Connection connection) {
        String events ="";
        int counter = 1;
        try {
            Statement statement = connection.createStatement();
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
}