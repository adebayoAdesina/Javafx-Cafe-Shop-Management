package com.example.cafeshopmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Cafe Shop Management");
            stage.setMinHeight(430);
            stage.setMinWidth(610);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}