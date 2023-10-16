package com.example.cafeshopmanagement.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
        private static final String URL = "jdbc:mysql://localhost:3306/Cafe_Shop_Management";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static Connection connectionDB(){
        try{
            Class.forName("com.mysql.jdbc.Drive");

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
