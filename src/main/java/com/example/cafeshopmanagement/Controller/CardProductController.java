package com.example.cafeshopmanagement.Controller;

import com.example.cafeshopmanagement.Database.Database;
import com.example.cafeshopmanagement.Model.ProductData;
import com.example.cafeshopmanagement.Model.UserDetail;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class CardProductController implements Initializable {
    public Label card_product_name;
    public Label card_price;
    public ImageView card_imageview;
    public Spinner<Integer> card_spinner;
    public Button card_add_btn;
    private ProductData productData;
    private Image image;
    private int quantity;
    private String productID;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Alert alert;


    private String type;
    private String prod_date;
    private String prod_image;

    public void setData(ProductData productData) {
        this.productData = productData;
        type = productData.getType();
        prod_image = productData.getImage();
        prod_date = productData.getDate();
        productID = productData.getProductId();
        card_product_name.setText(productData.getProductName());
        card_price.setText("$" + String.valueOf(productData.getPrice()));
        String path = "File:" + productData.getImage();
        image = new Image(path, 200, 150, false, true);
        card_imageview.setImage(image);
        pr = productData.getPrice();
    }

    private double total;
    private double pr;

    public void addBtn() {
        MainController mainController = new MainController();
        mainController.getCustomerID();
        quantity = card_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM Product WHERE product_id = ?";
        connection = Database.connectionDB();
        try {
            preparedStatement = connection.prepareStatement(checkAvailable);
            preparedStatement.setString(1, productID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                check = resultSet.getString("status");
            }
            if (!check.equals("Available") || quantity == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong");
                alert.showAndWait();
            } else {
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                int checkStck = 0;
                String checkStock = "SELECT stock FROM Product WHERE product_name = ?";

                preparedStatement = connection.prepareStatement(checkStock);
                preparedStatement.setString(1, card_product_name.getText());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    checkStck = resultSet.getInt("stock");

                }
                if (checkStck < card_spinner.getValue()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid. This product is out of stock");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO Customer (customer_id, product_name, quantity, price, date, em_username) VALUES(?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertData);
                    preparedStatement.setString(1, String.valueOf(UserDetail.getCustomerID()));
                    preparedStatement.setString(2, card_product_name.getText());
                    preparedStatement.setString(3, card_spinner.getValue().toString());
                    total = (quantity * pr);
                    preparedStatement.setString(4, String.valueOf(total));
                    preparedStatement.setString(5, String.valueOf(sqlDate));
                    preparedStatement.setString(6, UserDetail.getUsername());

                    preparedStatement.executeUpdate();

                    int upStock = checkStck - quantity;

                    prod_image = prod_image.replace("\\", "\\\\");

                    String updateStock = "UPDATE Product SET product_name =? , type = ? , stock=?, price = ?, status = ?, image = ?, date =? WHERE product_id = ?";
                    preparedStatement = connection.prepareStatement(updateStock);
                    preparedStatement.setString(1, card_product_name.getText());
                    preparedStatement.setString(2, type);
                    preparedStatement.setString(3, String.valueOf(upStock));
                    preparedStatement.setString(4, String.valueOf(pr));
                    preparedStatement.setString(5, check);
                    preparedStatement.setString(6, prod_image);
                    preparedStatement.setString(7, String.valueOf(sqlDate));
                    preparedStatement.setString(8, productID);
                    preparedStatement.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        card_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
    }
}
