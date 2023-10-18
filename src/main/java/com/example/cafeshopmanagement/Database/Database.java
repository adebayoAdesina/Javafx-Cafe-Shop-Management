package com.example.cafeshopmanagement.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
        private static final String URL = "jdbc:sqlite:cafeShopManagement.db";
        private static final String USER = "root";
        private static final String PASSWORD = "";
        public static Connection connectionDB(){
            try{
               Class.forName("org.sqlite.JDBC");

                return DriverManager.getConnection(URL);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }
}
