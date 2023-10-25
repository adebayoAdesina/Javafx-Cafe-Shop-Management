package com.example.cafeshopmanagement.Controller;

import com.example.cafeshopmanagement.Model.ProductData;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
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
    public void setData(ProductData productData) {
        this.productData = productData;
        card_product_name.setText(productData.getProductName());
        card_price.setText("$" + String.valueOf(productData.getPrice()));
        String path = "File:" + productData.getImage();
        image = new Image(path, 200, 150, false, true);
        card_imageview.setImage(image);
    }

    public void addBtn() {
        quantity = card_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM Product";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        card_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
    }
}
