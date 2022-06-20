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

## Screenshots
![Home Page](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/home%20page.png)
### Admin
![Admin Login Screen](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/Admin/login.png)

![Admin Home Screen](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/Admin/admin%20home%20page.png) 

![Add/Remove Category](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/Admin/add%20category.png)

![Edit Category](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/Admin/edit%20category.png)

![Add event](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/Admin/add%20event.png)

![Edit Event](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/Admin/edit%20event.png)

![Remove Event](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/Admin/remove%20event.png)

## 
### Employee
![Employee login](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/employee%20login.png)

![Employee Regester](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/employee%20register.png)

![Employeee Home Page](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/employee%20home%20page.png)

![Add client](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/add%20client.png)

![Remove Client](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/remove%20client.png)

![Search Client](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/search%20client.png)

![Book/ Unbook Event](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/book%20event.png)

![Retrieve events under specific Category](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/retrieve%20events.png)

![view event details](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/view%20event.png)

![Search Events](https://github.com/Kerolos-Noshy/Ticket-Reservation-Management-System/blob/main/screen%20shots/screen%20shots/employee/search%20event.png)

[^1]: Each client has a unique serial number and data (client's name, national ID, gender, mobile, his previous attended events, his upcoming events). 
[^2]: searching for him using his name or by using his serial number.
[^3]: Search all events that contains this key word
