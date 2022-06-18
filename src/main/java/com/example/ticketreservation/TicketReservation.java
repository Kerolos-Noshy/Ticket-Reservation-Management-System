package com.example.ticketreservation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class TicketReservation extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        TicketReservation.stage = stage;
        TicketReservation.stage.setResizable(false);
        TicketReservation.stage.setTitle("Tickets Reservation App");
        userType();
    }

    public static void userType() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(TicketReservation.class.getResource("Usertype.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void adminLogin() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Admin/AdminLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void adminHome() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Admin/AdminHome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void manageCategory() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Admin/ManageCategories.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void manageEvents() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Admin/ManageEvent.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void employeeLogin() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Employee/EmployeeLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void employeeRegister() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Employee/EmployeeRegistration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void employeeHome() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Employee/EmployeeHome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void showEmployeeClients() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Employee/Clients.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void showEmployeeEvents() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Employee/Events.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void showEmployeeBooking() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicketReservation.class.getResource("Employee/Booking.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}