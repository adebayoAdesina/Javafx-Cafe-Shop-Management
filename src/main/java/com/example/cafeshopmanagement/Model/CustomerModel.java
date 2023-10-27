package com.example.cafeshopmanagement.Model;

public class CustomerModel {
    private Integer id;
    private String customer_id;
    private String product_id;
    private String product_name;
    private String quantity;
    private String price;
    private String date;
    private String em_username;

    public CustomerModel(Integer id, String customerId, String productId, String productName, String quantity, String price, String date, String emUsername) {
        this.id = id;
        customer_id = customerId;
        product_id = productId;
        product_name = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        em_username = emUsername;
    }

    public Integer getId() {
        return id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getEm_username() {
        return em_username;
    }
}
