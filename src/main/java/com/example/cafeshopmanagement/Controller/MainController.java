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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
    public TableView<ProductData> inventory_tableview;
    public TableColumn<ProductData, String> inventory_product_id;
    public TableColumn<ProductData, String> inventory_product_name;
    public TableColumn<ProductData, String> inventory_product_type;
    public TableColumn<ProductData, String> inventory_product_stock;
    public TableColumn<ProductData, String> inventory_product_price;
    public TableColumn<ProductData, String> inventory_product_status;
    public TableColumn<ProductData, String> inventory_product_date;
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
    public AnchorPane main_form;

    private Alert alert;

    private Image image;
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

    LoginController loginController = new LoginController();

    public void inventoryAddBtn() {
        if (product_id_textfield.getText().isEmpty()
                || product_name_textfield.getText().isEmpty()
                || stock_textfield.getText().isEmpty()
                || price_textfield.getText().isEmpty()
                || type_combobox.getSelectionModel().getSelectedItem() == null
                 || status_combobox.getSelectionModel().getSelectedItem() == null
                || UserDetail.getPath() == null
        ) {

            loginController.fillAllFieldError();
        } else {
            String checkProductID = "SELECT product_id from Product WHERE product_id = ?";
            connection = Database.connectionDB();
            try {
                preparedStatement = connection.prepareStatement(checkProductID);
                preparedStatement.setString(1, product_id_textfield.getText());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(product_id_textfield.getText() + "already exist");
                }
                else {
                    String insertData = "INSERT INTO Product (product_id, product_name, type, stock, price, status, image, date) VALUES(?,?,?,?,?,?,?,?)";
                    Date date = new Date();
                    java.sql.Date _date = new java.sql.Date(date.getTime());
                    String path = UserDetail.getPath();
                    path = path.replace("\\", "\\\\");

                    preparedStatement = connection.prepareStatement(insertData);
                    preparedStatement.setString(1, product_id_textfield.getText());
                    preparedStatement.setString(2, product_name_textfield.getText());
                    preparedStatement.setString(3, type_combobox.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(4, stock_textfield.getText());
                    preparedStatement.setString(5, price_textfield.getText());
                    preparedStatement.setString(6, status_combobox.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(7, path);
                    preparedStatement.setString(8, String.valueOf(_date));
                    preparedStatement.executeUpdate();

                    inventoryShowData();
                    getSuccessAlert();
                    inventoryClearBtn();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //import images
    public void inventoryImportBtn() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Open Image File", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {
            UserDetail.setPath(file.getAbsolutePath());
            image = new Image(file.toURI().toString());
            display_selected_image.setImage(image);
        }
    }
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
                        resultSet.getString("type"),
                        resultSet.getInt("stock"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status"),
                        resultSet.getString("image"),
                        resultSet.getString("date")
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
        inventory_product_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_product_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_product_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_product_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableview.setItems(inventoryListData);

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

    public void getSuccessAlert() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Added!");
        alert.showAndWait();
    }

    public  void inventoryClearBtn(){
        product_id_textfield.setText("");
        product_name_textfield.setText("");
        type_combobox.getSelectionModel().clearSelection();
        stock_textfield.setText("");
        price_textfield.setText("");
        status_combobox.getSelectionModel().clearSelection();
        UserDetail.setPath("");
    }

    public void inventorySelectedData(){

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUsername();
        log_out_button.setOnAction(event -> logout());
        type_combobox.setItems(typeList);
        status_combobox.setItems(observableStatus);
        inventoryShowData();
        choose_image_button.setOnAction(event -> inventoryImportBtn());
        add_button.setOnAction(event -> inventoryAddBtn());
    }


}
