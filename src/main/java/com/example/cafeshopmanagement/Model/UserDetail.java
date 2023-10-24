package com.example.cafeshopmanagement.Model;


public class UserDetail {
    private static String username;
    private static String path;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserDetail.username = username;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        UserDetail.path = path;
    }
}
