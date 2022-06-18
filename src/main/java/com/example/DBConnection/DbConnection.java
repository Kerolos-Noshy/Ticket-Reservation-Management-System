package com.example.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    Connection connection = null;

    public static Connection ConnectionDB() {
        try {
            String url = "jdbc:sqlite:src/main/java/com/example/DB/Data.db";
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(url);
            //System.out.println("connected successfully");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
