package com.example.ticketreservation;

import com.example.DBConnection.DbConnection;
import com.example.Exceptions.*;
import com.example.Classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.sql.*;


public class SystemManager {
    Connection connection = DbConnection.ConnectionDB();
    PreparedStatement preparedStatement;

    @FXML
    private Label label;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private TextField name;
    @FXML
    private TextField name1;
    @FXML
    private TextField name2;
    @FXML
    private TextField name3;
    @FXML
    private TextField name4;
    @FXML
    private TextField age;
    @FXML
    private TextField name5;
    @FXML
    private TextField name6;
    @FXML
    private TextField name7;
    @FXML
    private TextField name8;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField number;
    @FXML
    private TextField number1;
    @FXML
    private TextField password;
    @FXML
    private DatePicker date;
    @FXML
    private DatePicker date1;
    @FXML
    private TextField stime;
    @FXML
    private TextField stime1;
    @FXML
    private TextField etime;
    @FXML
    private TextField etime1;




    // User Type Page
    @FXML
    private void goAdmin() throws IOException {
        TicketReservation.adminLogin();
    }

    @FXML
    private void goEmployee() throws IOException {
        TicketReservation.employeeLogin();
    }

    @FXML
    private void goBackLogin() throws IOException {
        TicketReservation.employeeLogin();
    }


    // Admin Login Page
    @FXML
    private void logA() throws IOException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM admins");
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(username.getText()) && resultSet.getString(2).equals(password.getText())) {
                    TicketReservation.adminHome();
                    checkEventsDate(statement);

                }
                else
                    label.setText("Wrong username or password!");
            }
        } catch (SQLException e) {
            System.out.println("Wrong name or password");
        }
    }

    // Employee Login Page
    @FXML
    private void logE() throws IOException {
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(username.getText()) && resultSet.getString(2).equals(password.getText())) {
                    TicketReservation.employeeHome();
                    checkEventsDate(statement);

                }
                else
                    label.setText("Wrong username or password!");
            }

        } catch (SQLException e) {
            System.out.println("Wrong name or password");
            throw new RuntimeException(e);
        }
    }

    // Employee Registration Page
    @FXML
    private void employeeRegistration() throws IOException {
        TicketReservation.employeeRegister();
    }

    @FXML
    private void register() throws IOException {

        try {
            preparedStatement = connection.prepareStatement("insert into employees(username, password, email,fullName, mobile, age)values(?,?,?,?,?,?)");
            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, password.getText());
            preparedStatement.setString(3, email.getText());
            preparedStatement.setString(4, name.getText());
            preparedStatement.setString(5, number.getText());
            preparedStatement.setInt(6, Integer.parseInt(age.getText()));
            if (Integer.parseInt(age.getText()) < 20)
                try {
                    throw new TooYoung();
                } catch (TooYoung e) {
                    label.setText(e.getMessage());
                }
            else {
                label.setText("Employee added Successfully");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Employee Home Page

    //Clients
    @FXML
    private void goClients() throws IOException {
        TicketReservation.showEmployeeClients();
    }

    //turn upcoming event into previous event if current date > event date
    private void checkEventsDate(Statement statement) {
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("select current_date");
            String date = resultSet.getString("current_date");
            String[] currentDate = date.split("-");
            ResultSet resultSet4 = statement.executeQuery("select * from upcomingEvents");
            String event;

            while (resultSet4.next()) {
                event = resultSet4.getString(1);
                ResultSet resultSet1 = statement.executeQuery("select eventName, day, month, year from events where eventName = '" + event + "'");

                while(resultSet1.next()) {
                    if (resultSet1.getInt(4) > Integer.parseInt(currentDate[0])) {
                        return;
                    }
                    else if (resultSet1.getInt(4) < Integer.parseInt(currentDate[0])){
                        moveToPreviousEvents(statement, event);
                    }
                    else if (resultSet1.getInt(4) == Integer.parseInt(currentDate[0])) {

                        if ((resultSet1.getInt(3) > Integer.parseInt(currentDate[1]))) {
                            return;
                        }
                        else if ((resultSet1.getInt(3) < Integer.parseInt(currentDate[1]))) {
                            moveToPreviousEvents(statement, event);
                        }
                        else if (resultSet1.getInt(3) == Integer.parseInt(currentDate[1])) {

                            if (resultSet1.getInt(2) < Integer.parseInt(currentDate[2])) {
                                moveToPreviousEvents(statement, event);
                            }
                            else
                                return;

                        } else {
                            moveToPreviousEvents(statement, event);
                        }
                    } else {
                        moveToPreviousEvents(statement, event);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private void moveToPreviousEvents(Statement statement, String event) throws SQLException {
        ResultSet resultSet3 = statement.executeQuery("select clientSerial from upcomingEvents where upcomingEvent = '" + event + "'");
        while (resultSet3.next()) {
            int clientId = resultSet3.getInt(1);
            preparedStatement = connection.prepareStatement("insert into previousEvents (previousEvent, clientSerial) values (?,?)");
            preparedStatement.setString(1, event);
            preparedStatement.setInt(2, clientId);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from upcomingEvents where upcomingEvent = '" + event + "' and clientSerial = '" + clientId + "'");
            preparedStatement.executeUpdate();
        }
    }

    @FXML
    private void addClient() {
        Statement statement;
            try {
                preparedStatement = connection.prepareStatement("insert into clients(SerialNumber, clientName, clientId, clientGender, clientMobile)values(?,?,?,?,?)");
                preparedStatement.setString(2, name.getText());
                preparedStatement.setString(3, name1.getText());
                preparedStatement.setString(4, name2.getText());
                preparedStatement.setString(5, name3.getText());
                preparedStatement.executeUpdate();

                /*
                // check if client exist or not
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT clientId FROM clients");
                try {
                    while (resultSet.next()) {
                        if (resultSet.getString(1).equals(name1.getText())) {
                            throw new AlreadyExist("Client already exist");
                        }
                    }
                } catch (AlreadyExist e){
                     label.setText(e.getMessage());
                }
                 */
                statement = connection.createStatement();
                ResultSet resultSet1 = statement.executeQuery("select serialNumber from clients where clientName = '" + name.getText() + "'");
                resultSet1.next();
                int clientSerialNumber = resultSet1.getInt(1);
                label.setText(String.valueOf(clientSerialNumber));
                System.out.println("\nClient's Serial Number: " + clientSerialNumber);
            }
            catch (SQLException e) {
                try{
                    statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select serialNumber from clients where clientName = '" + name.getText() + "'");
                    resultSet.next();
                    int clientSerialNumber = resultSet.getInt(1);
                    label.setText(String.valueOf(clientSerialNumber));
                    throw new AlreadyExist("Client already exist and his serial number is : " + clientSerialNumber);
                }
                catch (AlreadyExist b){
                    label.setText(b.getMessage());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
/*
        private boolean isClientAlreadyExist(String id) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT clientId FROM clients");

                while(resultSet.next()) {
                    if (resultSet.getString(1).equals(id)) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error");
                throw new RuntimeException(e);
            }
            return false;
        }
*/

    @FXML
    // remove client by his name or serial number
    private void removeClient() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select serialNumber from clients where serialNumber = '" + name4.getText() + "' or clientName = '" + name4.getText() + "'");
            resultSet.next();
            int clientSerialNumber = resultSet.getInt(1);
            preparedStatement = connection.prepareStatement("DELETE FROM clients where serialNumber = '" + clientSerialNumber + "'");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM upcomingEvents where clientSerial = '" + clientSerialNumber + "'");
            preparedStatement.executeUpdate();
            label1.setText("Client is removed successfully.");

        } catch (SQLException e) {
            try{
                throw new NotFound("Client");
            }
            catch (NotFound b){
                label1.setText(b.getMessage());
            }
        }
    }

    @FXML
    // retrieve client by serial number or name
    private void retrieveClient() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet, resultSet1;
            resultSet = statement.executeQuery("select serialNumber from clients where serialNumber = '" + name6.getText() + "' or clientName = '" + name6.getText() + "'");
            int clientSerialNumber = resultSet.getInt(1);
            resultSet1 = statement.executeQuery("SELECT * FROM clients where serialNumber = '" + clientSerialNumber + "'");
            resultSet1.next();
            String clientName = resultSet.getString(2);
            String clientId = resultSet.getString(3);
            String clientGender = resultSet.getString(4);
            String clientMobile = resultSet.getString(5);
            resultSet1 = statement.executeQuery("select previousEvent from previousEvents where clientSerial = '" + clientSerialNumber +"'");

            String previousEvents = "\n";
            int counter = 1;
            while (resultSet1.next()){
                previousEvents += counter + "- " + resultSet1.getString(1) + "\n";
                counter++;
            }
            if (counter == 1)
                previousEvents = "No Previous Events";

            resultSet1 = statement.executeQuery("select upcomingEvent from upcomingEvents where clientSerial = '" + clientSerialNumber +"'");
            String upcomingEvents = "\n";
            int counter1 = 1;
            while (resultSet1.next()){
                upcomingEvents += counter1 + "- " + resultSet1.getString(1) + "\n";
                counter1++;
            }
            if (counter1 == 1)
                upcomingEvents = "No Upcoming Events";

            label2.setText("Client Name: " + clientName +
                    "\nID: " + clientId +
                    "\nGender: " + clientGender +
                    "\nMobile: " + clientMobile+
                    "\nSerial Number: " + clientSerialNumber +
                    "\nPrevious Events: " + previousEvents +
                    "\nUpcoming Events: " + upcomingEvents);

        } catch (SQLException e) {
            try{
                throw new NotFound("Client");
            }
            catch (NotFound b){
                label2.setText(b.getMessage());
            }
        }
    }

    //Events
    @FXML
    private void goEvents() throws IOException {
        TicketReservation.showEmployeeEvents();
    }

    @FXML
    private void searchEvent()  {
        try {
            Statement statement = connection.createStatement();
            Event.searchEvent(name.getText(), label, statement);
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    @FXML
    private void viewEvent() {
        boolean flag = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categories WHERE categoryName = '" + name1.getText() + "'");
            while(resultSet.next()) {
                String categoryName = resultSet.getString(1);
                if (categoryName.equals(name1.getText())) {

                    Category.viewEvents(name2.getText(), label1, statement);
                    flag = true;
                }
            }
            if(!flag)
                throw new NotFound(name1.getText());
        }
        catch (NotFound e){
            label1.setText(e.getMessage());
        } catch (SQLException ex){
            throw new RuntimeException();
        }
    }

    @FXML
    private void retrieveEvent() {
        try {
            Statement statement = connection.createStatement();
            if (Category.isCategoryExist(name3.getText(), statement)) {
                Event.retrieveEvent(name3.getText(), label2, statement);
            } else {
                try {
                    throw new NotFound(name3.getText());
                } catch (NotFound e) {
                    label2.setText(e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    //Booking
    @FXML
    private void goBooking() throws IOException {
        TicketReservation.showEmployeeBooking();
    }

    @FXML
    private void bookEvent() {
        try {
            Statement statement = connection.createStatement();
        if (Client.isClientExist(name.getText(), statement)){
            if (Category.isCategoryExist(name1.getText(),statement)) {
                if (Event.isEventExist(name2.getText(), statement)) {
                    if (Event.isEventBooked(name2.getText(), name.getText(), statement) == false) {
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM clients WHERE clientName = '" + name.getText() + "'");
                        String serialNum = resultSet.getString(1);
                        resultSet = statement.executeQuery("SELECT * FROM events WHERE eventName = '" + name2.getText() + "'");
                        int availableTickets = resultSet.getInt(4);
                        try {
                            if (availableTickets > 0) {
                                availableTickets = availableTickets - 1;
                            } else
                                throw new NoAvailableTickets();
                        } catch (NoAvailableTickets e) {
                            label.setText(e.getMessage());
                        }
                        resultSet.next();
                        if (resultSet.getInt(4) > 0) {
                            preparedStatement = connection.prepareStatement("insert into upcomingEvents (upcomingEvent,clientSerial)values(?,?)");
                            preparedStatement.setString(1, name2.getText());
                            preparedStatement.setString(2, serialNum);
                            preparedStatement.executeUpdate();
                            label.setText("Event Booked Successfully");
                            preparedStatement = connection.prepareStatement("update events set availableTickets = '" + availableTickets + "' WHERE eventName = '" + name2.getText() + "'");
                            preparedStatement.executeUpdate();
                        }
                    } else
                        throw new AlreadyExist("Event is already booked");

                } else
                    throw new NotFound(name2.getText());

            } else
                throw new NotFound(name1.getText());

        }  else
            throw new NotFound(name.getText());

        }
        catch (AlreadyExist e){
            label.setText(e.getMessage());
        }
        catch (NotFound e1) {
            label.setText(e1.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Client Not Found");
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void unbookEvent() {
        Statement statement;
        try {
            statement = connection.createStatement();
            if (Client.isClientExist(name.getText(), statement)){
                if (Category.isCategoryExist(name1.getText(),statement)) {
                    if (Event.isEventExist(name2.getText(), statement)) {
                        if (Event.isEventBooked(name2.getText(), name.getText(), statement)) {
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM clients WHERE clientName = '" + name.getText() + "'");
                            resultSet.next();
                            String serialNum = resultSet.getString(1);
                            preparedStatement = connection.prepareStatement("DELETE FROM upcomingEvents where clientSerial = '" + serialNum + "' and upcomingEvent = '" + name2.getText() +"'");
                            preparedStatement.executeUpdate();
                            label.setText("Event is removed successfully.");
                            resultSet = statement.executeQuery("SELECT * FROM events WHERE eventName = '" + name2.getText() + "'");
                            int availableTickets = resultSet.getInt(4) + 1;
                            preparedStatement = connection.prepareStatement("update events set availableTickets = '" + availableTickets + "' WHERE eventName = '" + name2.getText() + "'");
                            preparedStatement.executeUpdate();
                        } else
                            label.setText("Event is not booked.");

                    } else
                        throw new NotFound(name2.getText());

                } else
                    throw new NotFound(name1.getText());

            }  else
                throw new NotFound(name.getText());
        }
        catch (NotFound e1) {
            label.setText(e1.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Client Not Found");
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void goBackEmployeeHome() throws IOException {
        TicketReservation.employeeHome();
    }

    // Admin Home Page

    //Categories
    @FXML
    private void goManageCategories() throws IOException {
        TicketReservation.manageCategory();
    }
    @FXML
    private void addCategory() {
        try{
            Statement statement = connection.createStatement();
            if (!Category.isCategoryExist(name.getText(), statement)) {
                preparedStatement = connection.prepareStatement("insert into categories(categoryName)values(?)");
                preparedStatement.setString(1, name.getText());
                preparedStatement.executeUpdate();
                label.setText("Category is added successfully.");
            } else
                throw new AlreadyExist(name.getText() + " Already Exists");

        }
        catch (AlreadyExist e){
            label.setText(e.getMessage());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void removeCategory() {
        try {
            Statement statement = connection.createStatement();
            if (Category.isCategoryExist(name.getText(), statement)) {
                preparedStatement = connection.prepareStatement("DELETE FROM categories where categoryName = '" + name.getText() + "'");
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement("DELETE FROM events WHERE category = '" + name.getText() + "'");
                preparedStatement.executeUpdate();
                label.setText("Category and its Events are removed successfully");
            } else {
                throw new NotFound(name.getText());
            }
        }
        catch (NotFound e){
            label.setText("Category Not Found");
        }
        catch (SQLException e) {
            System.out.println("Category not Found");
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void editCategory() {
        Statement statement;
        try {
            statement = connection.createStatement();
            // check if category exist
            if (Category.isCategoryExist(name1.getText(), statement)) {
                // check if new category name is already exist
                if (!Category.isCategoryExist(name2.getText(), statement)) {
                    preparedStatement = connection.prepareStatement("update categories set categoryName = '" + name2.getText() + "' WHERE categoryName = '" + name1.getText() + "'");
                    preparedStatement.executeUpdate();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM events");
                    while (resultSet.next()) {
                        preparedStatement = connection.prepareStatement("update events set category = '" + name2.getText() + "' WHERE category = '" + name1.getText() + "'");
                        preparedStatement.executeUpdate();
                    }
                    label1.setText("Name is changed successfully.");
                } else
                    throw new AlreadyExist("This Category Name is Already Exist\nPlease Enter Another Name");

            } else {
                throw new NotFound(name1.getText());
            }
        }
        catch (NotFound e){
            label1.setText("Category Not Found");
        }
        catch (AlreadyExist e) {
            label1.setText(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException();
        }
    }

    //Events
    @FXML
    private void goManageEvents() throws IOException {
        TicketReservation.manageEvents();
    }
    private Date localDate;
    private Date localDate1;
    @FXML
    private void getDate(ActionEvent event) {
        LocalDate mydate = date.getValue();
        Date date = Date.from(mydate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        localDate = date;
    }
    @FXML
    private void getDate1(ActionEvent event) {
        LocalDate mydate = date1.getValue();
        Date date = Date.from(mydate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        localDate1 = date;
    }
    @FXML
    private void addEvent() {
        try {
            Statement statement = connection.createStatement();
            if (Category.isCategoryExist(name.getText(), statement)) {
                if (!Event.isEventExist(name1.getText(), statement)) {
                    preparedStatement = connection.prepareStatement("insert into events(category,eventName,location,availableTickets,day,month,year,startTime,endTime)values(?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setString(1, name.getText());
                    preparedStatement.setString(2, name1.getText());
                    preparedStatement.setString(3, name2.getText());
                    preparedStatement.setInt(4, Integer.parseInt(number.getText()));
                    preparedStatement.setInt(5, localDate.getDate());
                    preparedStatement.setInt(6, localDate.getMonth() + 1);
                    preparedStatement.setInt(7, localDate.getYear() + 1900);
                    preparedStatement.setString(8, stime.getText());
                    preparedStatement.setString(9, etime.getText());
                    preparedStatement.executeUpdate();
                    label.setText("Event is added successfully.");
                } else {
                    throw new AlreadyExist("Event already exist");
                }

            } else {
                throw new NotFound(name.getText());
            }
        }
        catch (AlreadyExist e) {
            label.setText(e.getMessage());
        }
        catch (NotFound e) {
            label.setText(e.getMessage());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void removeEvent() {
        try {
            Statement statement = connection.createStatement();
            if (Category.isCategoryExist(name3.getText(), statement)) {
                if (Event.isEventExist(name4.getText(), statement)) {
                    PreparedStatement preparedStatement;
                        preparedStatement = connection.prepareStatement("DELETE FROM events where eventName = '" + name4.getText() + "' and category = '" + name3.getText() + "'");
                        preparedStatement.executeUpdate();
                        label1.setText("Event is removed successfully");
                    } else {
                    throw new NotFound(name4.getText());
                }
            }  else {
                throw new NotFound(name3.getText());
            }
        }
        catch (NotFound e){
            label1.setText(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Event not Found");
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void editEvent() {

        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            if (Category.isCategoryExist(name5.getText(), statement)) {
                if (Event.isEventExist(name6.getText(), statement)) {
                    preparedStatement = connection.prepareStatement("update events set eventName = '" + name7.getText() + "' WHERE eventName = '" + name6.getText() + "'");
                    preparedStatement.executeUpdate();

                    resultSet = statement.executeQuery("SELECT * FROM upcomingEvents");
                    while (resultSet.next()) {
                        preparedStatement = connection.prepareStatement("update upcomingEvents set upcomingEvent = '" + name7.getText() + "' WHERE upcomingEvent = '" + name6.getText() + "'");
                        preparedStatement.executeUpdate();
                    }
                    preparedStatement = connection.prepareStatement("update events set location = '" + name8.getText() + "' WHERE eventName = '" + name7.getText() + "'");
                    preparedStatement.executeUpdate();
                    int newAvailableTickets = Integer.parseInt(number1.getText());
                    if (newAvailableTickets >= 0) {
                        preparedStatement = connection.prepareStatement("update events set availableTickets = '" + newAvailableTickets + "' WHERE eventName = '" + name7.getText() + "'");
                        preparedStatement.executeUpdate();
                    } else {
                        throw new NotValidTicketsNumber("Available Tickets Not Valid");
                    }

                    preparedStatement = connection.prepareStatement("update events set day = '" + localDate1.getDate() + "' WHERE eventName = '" + name7.getText() + "'");
                    preparedStatement.executeUpdate();
                    preparedStatement = connection.prepareStatement("update events set month = '" + ((localDate1.getMonth()) + 1) + "' WHERE eventName = '" + name7.getText() + "'");
                    preparedStatement.executeUpdate();
                    preparedStatement = connection.prepareStatement("update events set year = '" + ((localDate1.getYear()) + 1900) + "' WHERE eventName = '" + name7.getText() + "'");
                    preparedStatement.executeUpdate();

                    preparedStatement = connection.prepareStatement("update events set startTime = '" + stime1.getText() + "' WHERE eventName = '" + name7.getText() + "'");
                    preparedStatement.executeUpdate();

                    preparedStatement = connection.prepareStatement("update events set endTime = '" + etime1.getText() + "' WHERE eventName = '" + name7.getText() + "'");
                    preparedStatement.executeUpdate();

                    label2.setText("Event is edited successfully");
                } else {
                    throw new NotFound(name6.getText());
                }
            } else {
                throw new NotFound(name5.getText());
            }
        }
        catch (NotValidTicketsNumber e){
            label2.setText(e.getMessage());
        }
        catch (NotFound e) {
            label2.setText(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Error!");
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void showOldData(){
        try {
            Statement statement = connection.createStatement();
            if (Category.isCategoryExist(name5.getText(), statement)) {
                if (Event.isEventExist(name6.getText(), statement)) {
                    String[] eventData = Event.getEventData(name6.getText(), statement);
                    if (eventData != null) {
                        name8.setText(eventData[0]);
                        number1.setText(eventData[1]);
                        label3.setText(eventData[4]);
                        stime1.setText(eventData[2]);
                        etime1.setText(eventData[3]);
                    } else
                        throw new NotFound(name6.getText());

                } else
                    throw new NotFound(name6.getText());

            } else
                    throw new NotFound(name5.getText());
        }
        catch (NotFound e){
           label2.setText(e.getMessage());
        }
        catch (SQLException e){
            throw new RuntimeException();
        }
    }
    @FXML
    private void goBackAdminHome() throws IOException {
        TicketReservation.adminHome();
    }

    //Logout
    @FXML
    private void logOut() throws IOException {
        TicketReservation.userType();
    }
}
