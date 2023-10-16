module com.example.cafeshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;

    opens com.example.cafeshopmanagement to javafx.fxml;
    exports com.example.cafeshopmanagement;
    exports com.example.cafeshopmanagement.Controller;
    exports com.example.cafeshopmanagement.Database;
}