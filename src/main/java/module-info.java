module com.example.cafeshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cafeshopmanagement to javafx.fxml;
    exports com.example.cafeshopmanagement;
    exports com.example.cafeshopmanagement.Controller;
}