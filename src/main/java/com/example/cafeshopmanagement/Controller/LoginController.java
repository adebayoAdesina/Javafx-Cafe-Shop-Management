package com.example.cafeshopmanagement.Controller;

import com.example.cafeshopmanagement.App;
import com.example.cafeshopmanagement.Database.Database;
import com.example.cafeshopmanagement.Model.UserDetail;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
    public TextField user_username;
    public Button user_proceed;
    public ComboBox<String> user_question;
    public Button back_to_login;
    public TextField user_answer;
    public Button back_to;
    public Button change_password;
    public PasswordField confirm_password;
    public PasswordField new_password;
    public AnchorPane forget_password_section;
    public AnchorPane forget_password_proceed_section;

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet;
    private final String[] questionList = {
            "What is your favorite Color?",
            "What is your favorite food?",
            "What is your date of birth?",
    };

    ObservableList<String> observableList = FXCollections.observableArrayList(questionList);

    private Alert alert;

    public  void proceedAction() {
        if (user_username.getText().isEmpty() || user_question.getSelectionModel().getSelectedItem() == null || user_answer.getText().isEmpty() ) {
            fillAllFieldError();
        }
        else {
            String checkUsernameAndQuestion = "SELECT username, question, answer FROM Employee WHERE username = ? AND question = ? AND answer = ?";
            connection = Database.connectionDB();
            try{
                preparedStatement = connection.prepareStatement(checkUsernameAndQuestion);
                preparedStatement.setString(1, user_username.getText());
                preparedStatement.setString(2, user_question.getSelectionModel().getSelectedItem());
                preparedStatement.setString(3, user_answer.getText());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    side_already_have_an_account.setVisible(false);
                    side_create_account_button.setVisible(false);
                    forget_password_section.setVisible(true);
                    forget_password_proceed_section.setVisible(false);
                    side_create_account_button.setVisible(true);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username, Question or Answer");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loginAction() {

        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            fillAllFieldError();
        } else if (login_password.getText().length() < 8) {
            invalidPassword();
        } else {
            String confirmIfTrue = "SELECT username, password FROM Employee WHERE username = ? AND password = ? ";
            connection = Database.connectionDB();
            try {
                preparedStatement = connection.prepareStatement(confirmIfTrue);
                preparedStatement.setString(1, login_username.getText());
                preparedStatement.setString(2, login_password.getText());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    UserDetail.setUsername(login_username.getText());

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("InformationMessage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXML/Main.fxml"));
                    Stage stage =  new Stage();
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.setTitle("Cafe Shop Management");
                    stage.setMinHeight(800);
                    stage.setMinWidth(1280);
                    stage.show();
                    login_button.getScene().getWindow().hide();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void registrationButton() throws SQLException {
        if (register_account_username.getText().isEmpty() || register_account_password.getText().isEmpty()
                || register_account_question.getSelectionModel().getSelectedItem() == null || register_account_answer.getText().isEmpty()
        ) {
            fillAllFieldError();
        } else {
//            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM");
//            String date = sdf.format(new Date());
            Date date = new Date();
            java.sql.Date _date = new java.sql.Date(date.getTime());
            String regData = "INSERT INTO Employee (username, password, question, answer, date) VALUES (?, ?, ?, ?, ?)";
            connection = Database.connectionDB();
            System.out.println(isDBConnected());
            try {
                String checkUsername = "SELECT username FROM Employee WHERE username == '" + register_account_username.getText() + "'";
                preparedStatement = connection.prepareStatement(checkUsername);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(register_account_username.getText() + " is already registered\nPlease Log in or click forgot password");
                    alert.showAndWait();

                    transitionLeft();
                } else if (register_account_password.getText().length() < 8) {
                    invalidPassword();
                } else {
                    preparedStatement = connection.prepareStatement(regData);
                    preparedStatement.setString(1, register_account_username.getText());
                    preparedStatement.setString(2, register_account_password.getText());
                    preparedStatement.setString(3, register_account_question.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(4, register_account_answer.getText());
                    preparedStatement.setString(5, String.valueOf(_date));
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Operation was successful!\nPlease Log in");

                    ButtonType closeButton = new ButtonType("Close", ButtonType.OK.getButtonData());
                    alert.getButtonTypes().setAll(closeButton);

                    alert.showAndWait();
                    register_account_username.setText("");
                    register_account_answer.setText("");
                    register_account_password.setText("");
                    register_account_question.getSelectionModel().clearSelection();

                    transitionLeft();
//                if (resultSet.next()) {
//                    System.out.println("Sent");
//                } else {
//                    System.out.println("Error");
//                }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                preparedStatement.close();
                if (connection != null) {
                    try {
                        connection.close(); // <-- This is important
                    } catch (SQLException e) {
                        /* handle exception */
                    }
                }
//                resultSet.close();
            }
        }
    }

    public boolean isDBConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    TranslateTransition translateTransition = new TranslateTransition();

    public void switchForm(ActionEvent event) {
        if (event.getSource() == side_create_account_button) {
            transitionRight();

        } else if (event.getSource() == side_already_have_an_account) {
            transitionLeft();
        }

    }

    public void invalidPassword() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Password must be more than 8 character.");
        alert.showAndWait();
    }

    public void transitionLeft() {
        translateTransition.setNode(side_form);
        translateTransition.setToX(0);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.play();
        translateTransition.setOnFinished(e -> {
            side_already_have_an_account.setVisible(false);
            side_create_account_button.setVisible(true);
            forget_password_section.setVisible(false);
            forget_password_proceed_section.setVisible(false);
        });
    }

    public void transitionRight() {
        translateTransition.setNode(side_form);
        translateTransition.setToX(300);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.play();
        translateTransition.setOnFinished(e -> {
            side_already_have_an_account.setVisible(true);
            side_create_account_button.setVisible(false);
            forget_password_section.setVisible(false);
            forget_password_proceed_section.setVisible(false);
        });
    }

    public void fillAllFieldError() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    }

    public void  forgetPasswordAction() {
        side_already_have_an_account.setVisible(false);
        side_create_account_button.setVisible(false);
        forget_password_section.setVisible(false);
        forget_password_proceed_section.setVisible(true);
        side_create_account_button.setVisible(true);
    }

    public void backToLogin() {
        side_already_have_an_account.setVisible(false);
        side_create_account_button.setVisible(true);
        forget_password_section.setVisible(false);
        forget_password_proceed_section.setVisible(false);

    }

    public void changePasswordAction()  {
        if (!new_password.getText().equals(confirm_password.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("New password and confirm password are not the same.");
            alert.showAndWait();
        } else if(new_password.getText().isEmpty() || confirm_password.getText().isEmpty()) {
            fillAllFieldError();
        }
        else if(new_password.getText().length() < 8) {
            invalidPassword();
        } else {
            String changePassword = "UPDATE Employee SET password = ? WHERE username = ?";

            try {
                preparedStatement = connection.prepareStatement(changePassword);
                preparedStatement.setString(1, new_password.getText());
                preparedStatement.setString(2, user_username.getText());
                boolean rowsAffected = preparedStatement.execute();
                System.out.println(rowsAffected);
                if (rowsAffected) {
                    System.out.println("Internal Error");

                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password Successfully Changed");
                    alert.showAndWait();

                    new_password.setText("");
                    confirm_password.setText("");
                    user_username.setText("");
                    user_answer.setText("");
                    user_question.getSelectionModel().clearSelection();
                }


            } catch (SQLException e) {
                e.printStackTrace();
//                throw new RuntimeException(e);
            }
//            finally {
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                if (connection != null) {
//                    try {
//                        connection.close(); // <-- This is important
//                    } catch (SQLException e) {
//                        /* handle exception */
//                    }
//                }
//            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_account_question.setItems(observableList);
        user_question.setItems(observableList);
        create_account_button.setOnAction(event -> {
            try {
                registrationButton();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        login_button.setOnAction(event -> loginAction());
        login_forget_password.setOnAction(event -> forgetPasswordAction());
        back_to_login.setOnAction(event -> backToLogin());
        back_to.setOnAction(event -> backToLogin());
        user_proceed.setOnAction(event -> proceedAction());
        change_password.setOnAction(event -> {

                changePasswordAction();

        });
    }

}
