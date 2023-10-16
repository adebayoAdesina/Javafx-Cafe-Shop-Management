package com.example.cafeshopmanagement.Controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public AnchorPane login_section;
    public TextField login_username;
    public PasswordField login_password;
    public Button login_button;
    public Hyperlink login_forget_password;
    public AnchorPane register_account_section;
    public TextField register_account_username;
    public PasswordField register_account_password;
    public Button create_account_button;
    public ComboBox<String> register_account_question;
    public TextField register_account_answer;
    public AnchorPane side_form;
    public Button side_create_account_button;
    public Button side_already_have_an_account;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private final String[] questionList = {
            "What is your favorite Color?",
            "What is your favorite food?",
            "What is your date of birth?",
    };

    ObservableList<String> observableList = FXCollections.observableArrayList(questionList);

    private Alert alert;
    public void registrationButton() {
        if (register_account_username.getText().isEmpty() || register_account_password.getText().isEmpty()
            || register_account_question.getSelectionModel().getSelectedItem() == null || register_account_answer.getText().isEmpty()
        ) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else  {
            String regData = "INSERT INTO Employee (username, password, question, answer)";
        }
    }
    public void switchForm(ActionEvent event) {
        TranslateTransition translateTransition = new TranslateTransition();


        if (event.getSource() == side_create_account_button) {
            translateTransition.setNode(side_form);
            translateTransition.setToX(300);
            translateTransition.setDuration(Duration.millis(1000));
            translateTransition.play();
            translateTransition.setOnFinished(e -> {
                side_already_have_an_account.setVisible(true);
                side_create_account_button.setVisible(false);
            });

        } else if (event.getSource() == side_already_have_an_account) {
            translateTransition.setNode(side_form);
            translateTransition.setToX(0);
            translateTransition.setDuration(Duration.millis(1000));
            translateTransition.play();
            translateTransition.setOnFinished(e -> {
                side_already_have_an_account.setVisible(false);
                side_create_account_button.setVisible(true);
            });
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_account_question.setItems(observableList);
        create_account_button.setOnAction(event -> registrationButton());
    }
}
