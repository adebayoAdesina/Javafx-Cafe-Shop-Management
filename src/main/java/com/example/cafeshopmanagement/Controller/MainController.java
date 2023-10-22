package com.example.cafeshopmanagement.Controller;

import com.example.cafeshopmanagement.App;
import com.example.cafeshopmanagement.Database.Database;
import com.example.cafeshopmanagement.Model.ProductData;
import com.example.cafeshopmanagement.Model.UserDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Label username;
    public Button dashboard_button;
    public Button inventory_button;
    public Button menu_button;
    public Button customers_button;
    public Button log_out_button;
    public AnchorPane inventory_form;
    public TableView<?> inventory_tableview;
    public TableColumn<?, ?> inventory_product_id;
    public TableColumn<?, ?> inventory_product_name;
    public TableColumn<?, ?> inventory_product_type;
    public TableColumn<?, ?> inventory_product_stock;
    public TableColumn<?, ?> inventory_product_price;
    public TableColumn<?, ?> inventory_product_status;
    public TableColumn<?, ?> inventory_product_date;
    public TextField product_id_textfield;
    public TextField product_name_textfield;
    public ComboBox<String> type_combobox;
    public TextField stock_textfield;
    public TextField price_textfield;
    public ImageView display_selected_image;
    public Button choose_image_button;
    public Button add_button;
    public Button update_button;
    public Button delete_button;
    public Button clear_button;
    public ComboBox<String> status_combobox;

    private Alert alert;

    private String[] list = {
            "Meal",
            "Drinks",
    };
    ObservableList<String> typeList = FXCollections.observableArrayList(list);

    private String[] status = {
            "Available",
            "Unavailable"
    };

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    ObservableList<String> observableStatus = FXCollections.observableArrayList(status);

    public ObservableList<ProductData> inventoryDataList() {
        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Product";
        connection = Database.connectionDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ProductData productData;
            while (resultSet.next()) {
                productData = new ProductData(
                        resultSet.getInt("id"),
                        resultSet.getString("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getInt("stock"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status"),
                        resultSet.getString("image"),
                        resultSet.getDate("date")
                );
                listData.add(productData);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;
    };

    private ObservableList<ProductData> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();
        inventory_product_id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_product_type.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_product_id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_product_id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_product_id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_product_id.setCellValueFactory(new PropertyValueFactory<>("productId"));


    }
    public void logout() {

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get().equals(ButtonType.OK)) {

            log_out_button.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
            Stage stage = new Stage();
            try {
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Cafe Shop Management");
                stage.setMinHeight(430);
                stage.setMinWidth(610);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void getUsername() {
        String user = UserDetail.getUsername();
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUsername();
        log_out_button.setOnAction(event -> logout());
        type_combobox.setItems(typeList);
        status_combobox.setItems(observableStatus);
    }


}
