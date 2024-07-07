package com.airlinebooking.data.prepare;

import com.airlinebooking.db.connection.DatabaseConnection;
import com.github.javafaker.Faker;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class CustomerDBDataPrepUtil {

    public static void initializeCustomerDB(){
        populateCustomers();
        populateTrips();
        populateSeats();
    }

    private static void populateSeats(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO customer_schema.seats (id, name,trip_id) VALUES (?, ?,?)";
            if(null== connection){
                throw new RuntimeException("Could not get a DB connection");
            }
            preparedStatement = connection.prepareStatement(sql);
            int id = 1;
            int row = 1;
            char seat = 'A';
            int trip_id = 1;
            for (int i = 0; i < 120; i++) {
                if (seat > 'F') {
                    seat = 'A';
                    row++;
                }
                String name = row + "-" + seat;
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, trip_id);
                preparedStatement.executeUpdate();
                id++;
                seat++;
            }

            System.out.println("Inserted 120 records into the seats table.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private static void populateTrips(){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int trip_id = 1;
        String trip_name = "6E-101";

        try {
                connection = DatabaseConnection.getConnection();
            if(null == connection){
                System.out.println("Could not establish a connection: "+Thread.currentThread().getName());
                return;
            }
            // SQL query to insert data
            String sql = "INSERT INTO customer_schema.trips (id, name) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, trip_id); // id
            preparedStatement.setString(2, trip_name); // trip name
                // Execute update
                int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private static void populateCustomers(){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Random random = new Random();
        int randomAge;
        int minAge = 18;
        int maxAge = 100;

        try {
            connection = DatabaseConnection.getConnection();
            if(null == connection){
                System.out.println("Could not establish a connection: "+Thread.currentThread().getName());
                return;
            }
            // SQL query to insert data
            String sql = "INSERT INTO customer_schema.customer (id, first_name, last_name,age_in_years) VALUES (?, ?, ?,?)";

            preparedStatement = connection.prepareStatement(sql);
            int rowsAffected = 0;

            for(int i=1;i<=120;i++){
                randomAge = random.nextInt(minAge,maxAge);
                Faker faker = new Faker();
                preparedStatement.setInt(1, i); // id
                preparedStatement.setString(2, faker.name().firstName()); // first_name
                preparedStatement.setString(3, faker.name().lastName()); // last_name
                preparedStatement.setInt(4,randomAge); // age
                // Execute update
                rowsAffected = rowsAffected + preparedStatement.executeUpdate();
            }

            System.out.println("Rows affected: " + rowsAffected);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
