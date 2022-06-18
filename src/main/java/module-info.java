module com.example.ticketreservation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.willena.sqlitejdbc;


    opens com.example.ticketreservation to javafx.fxml;
    exports com.example.ticketreservation;
}