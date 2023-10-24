package com.example.cafeshopmanagement.Model;


public class UserDetail {
    private static String username;
    private static String path;
    private static String date;
    private static Integer id;

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

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        UserDetail.date = date;
    }

    public static void setId(Integer id) {
        UserDetail.id = id;
    }

    public static Integer getId() {
        return id;
    }
}
