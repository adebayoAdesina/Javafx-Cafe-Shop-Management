package com.example.cafeshopmanagement.Controller;

import com.example.cafeshopmanagement.Model.UserDetail;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Label username;
    public Button dashboard_button;
    public Button inventory_button;
    public Button menu_button;
    public Button customers_button;
    public FontAwesomeIconView log_out_button;
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
    public ComboBox<?> type_combobox;
    public TextField stock_textfield;
    public TextField price_textfield;
    public ImageView display_selected_image;
    public Button choose_image_button;
    public Button add_button;
    public Button update_button;
    public Button delete_button;
    public Button clear_button;
    public ComboBox<?> status_combobox;


    public void getUsername() {
        String user = UserDetail.getUsername();
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUsername();

    }


}
