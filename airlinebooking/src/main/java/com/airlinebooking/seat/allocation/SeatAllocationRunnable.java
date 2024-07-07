package com.airlinebooking.seat.allocation;

import com.airlinebooking.db.connection.DatabaseConnection;
import com.airlinebooking.db.connection.DatabaseConnectionPool;

import java.sql.*;

public class SeatAllocationRunnable implements Runnable{
    private boolean poolActive = false;
    private int customerId = 0;

    public SeatAllocationRunnable(boolean poolActive,int customerId){
        this.poolActive = poolActive;
        this.customerId = customerId;
    }
    @Override
    public void run(){
        PreparedStatement updateStatement = null;
        Connection connection = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        try {

             if(this.poolActive){
                 connection = DatabaseConnectionPool.getConnection();
             }else{
                 connection = DatabaseConnection.getConnection();
             }
             if(null == connection){
                System.out.println("Could not establish a connection: "+Thread.currentThread().getName());
                return;
             }else{
                System.out.println("Connected to database successfully: "+Thread.currentThread().getName());
             }
                connection.setAutoCommit(false);
            /**
             * Approach1: Selects an empty seat for customer without aquiring any lock.
             * Approach2: Selects an empty seat for customer by aquiring an exclusive lock on the row for update until the transaction is committed or rolled back.
             * In case of a multi-threaded environment in this approach multiple threads may get the same row in select query but they will wait for the thread that aquires the lock to complete.
             * Approach3: Selects an empty seat for customer by aquiring an exclusive lock on the row for update until the transaction is committed or rolled back.
             * Also allowing other threads to proceed by adding "SKIP LOCKED" so this will lead to better performance than approach2.
             */
            String selectSQLApproach1 = "SELECT id FROM customer_schema.seats where trip_id=1 AND customer_id IS NULL ORDER BY id LIMIT 1";
                String selectSQLApproach2 = "SELECT id FROM customer_schema.seats where trip_id=1 AND customer_id IS NULL ORDER BY id LIMIT 1 FOR UPDATE";
                String selectSQLApproach3 = "SELECT id FROM customer_schema.seats where trip_id=1 AND customer_id IS NULL ORDER BY id LIMIT 1 FOR UPDATE SKIP LOCKED";
                // Prepare select statement
                selectStatement = connection.prepareStatement(selectSQLApproach1);
                resultSet = selectStatement.executeQuery();
                 int seatId = -1;
            if (resultSet.next()) {
                // Query returned a result
                seatId  = resultSet.getInt("id");
                System.out.println("Query returned a result. ID: " + seatId);
            }
            if(seatId == -1){
                System.out.println("Could not find a seat for customer" + this.customerId);
                return;
            }
            String updateSQL = "UPDATE customer_schema.seats SET customer_id = ? WHERE id = ?";

            // Prepare update statement
            updateStatement = connection.prepareStatement(updateSQL);

            updateStatement.setInt(1, this.customerId);
            updateStatement.setInt(2, seatId);

            // Execute update
            updateStatement.executeUpdate();
            connection.commit();
        }catch(Exception e){
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally{
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (updateStatement != null) updateStatement.close();
                if (connection != null) {
                    if(poolActive){
                        DatabaseConnectionPool.putConnection(connection);
                    }else{
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}