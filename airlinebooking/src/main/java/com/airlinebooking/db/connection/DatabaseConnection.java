package com.airlinebooking.db.connection;

import java.sql.*;
public class DatabaseConnection{

    public static Connection getConnection(){
        try {
            String url = "jdbc:postgresql://localhost/customerdb?user=postgres&password=password&ssl=false";

            return DriverManager.getConnection(url);
        } catch (Exception e) {
          //  e.printStackTrace();
        }
        return null;
    }
}