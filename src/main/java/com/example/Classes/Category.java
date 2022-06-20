package com.example.Classes;

import com.example.Exceptions.NotFound;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

public class Category {
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static boolean isCategoryExist(String categoryName, Statement statement) {
        ResultSet resultSet;
        try {
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

    public static void viewEvents(String eventName, Label label, Statement statement) {
        String event ="";
        if (Event.isEventExist(eventName,statement)){
            try {
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
}