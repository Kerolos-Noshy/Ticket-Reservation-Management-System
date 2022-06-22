# Ticket Reservation Management System
## Overview
- Offline ticket reservation management system using Java, JavaFX for GUI, and SQLite.
- It's an application for managing ticket reservation of clients for any event.

- This system will be used by the call center employees who work inside the agency. So, the
client will call the call center employee and ask him to book a ticket for a specific event in a
specific time. The employee will search the system if the event is existing. If he found an
available ticket for the event, he will book it for the client.

- Example of events: matches, concerts, theaters, Festivals, opening events of any place, conferences, 
and any kind of events, etc.

- Each event has a name, place, time, and description for the event, number of available
tickets.

- :point_right: **_Upcoming events_ turn into _previous events_ automatically When their date becomes past.** :point_left:

## Features
1. Admin
   - Sign in and sign out of the system
   - Add/ edit/ delete category of event.
   - Add / edit / delete event inside a specific category.
2. Employee
   - Resister, Sign in and sign out of the system.
   - Add a new registered client in the system.[^1]
   - Remove a client from the system.
   - Retrieve all the events under a specific category.
   - Retrieve data about specific clients.[^2]
   - View the event details.
   - Search event by name.[^3]
   - Book event for a client if there is an available ticket.
   - Unbook any event of the upcoming events of any client.

## Built With
1. IntelliJ IDEA Ulimate 2021.3
2. SDK: Oracle OpenJDK (version 18.0.1)

## Prerequisites
1. Java SDK (Oracle JDK version 18 preferred)
2. IDE - as per your choice (IntelliJ preferred)
3. SQLite JDBC driver [Download Link](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/raw/main/sqlite%20jar%20file/sqlite-jdbc-3.38.1.jar)

## How to use this repository
1. Fork it.
2. Click on Code button in top right corner (the green button).
3. Download zip.
4. Extract.
5. Open IntelliJ and Import Project
6. Open Project Structure from Menu or Click (ctrl + alt + shift + s)
7. Select Labraries at the left panel Then Select + Icon at top
8. Select Java and add the [Jar](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/raw/main/sqlite%20jar%20file/sqlite-jdbc-3.38.1.jar) file
9. Run TicketReservation Class

## Date Stored in Datebase
1. Admin
   - Username: admin
   - Password: admin
   
2. Employee[^4]
   - username: employee
   - Password: 1111
   
3. Categories
   - Match
   - Movie

4. Events
   - Match Category
     - CAF CL Final
     - Cup Final
      
   - Movie Category
     - Avengers: End Game Final
     - Batman

[^1]: Each client has a unique serial number and data (client's name, national ID, gender, mobile, his previous attended events, his upcoming events). 
[^2]: searching for him using his name or by using his serial number.
[^3]: Search all events that contains this key word
[^4]: You can regester as employee with your data
