package com.example.Exceptions;

public class NoAvailableTickets extends Exception {
    public NoAvailableTickets() {
        super("No Available Tickets");
    }
}
