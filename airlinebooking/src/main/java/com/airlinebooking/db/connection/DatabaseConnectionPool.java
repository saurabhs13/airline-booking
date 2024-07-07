package com.airlinebooking.db.connection;

import java.sql.*;
import java.util.concurrent.*;
public class DatabaseConnectionPool{
    private static final int MAX_CONNECTIONS = 100;
    private static BlockingQueue<Connection> connectionPool;

    static {
        connectionPool = new LinkedBlockingQueue<>(MAX_CONNECTIONS);
        populatePool();
    }
    /*public DatabaseConnectionPool(int maxConnections){
        connectionPool = new LinkedBlockingQueue<>(maxConnections);
        populatePool(connectionPool,maxConnections);
    }*/
    private static void populatePool() {  
        for(int i=0;i<MAX_CONNECTIONS;i++){
               Connection connection =  DatabaseConnection.getConnection();
               if(null != connection){
                   try {
                       connectionPool.put(connection);
                   } catch (InterruptedException ex) {
                        ex.printStackTrace();
                   }
               }
         }
    }
    public static Connection getConnection(){
        try {
            return connectionPool.take();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void putConnection(Connection connection){
        try {
            connectionPool.put(connection);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}