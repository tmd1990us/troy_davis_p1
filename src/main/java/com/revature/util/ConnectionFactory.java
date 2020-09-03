package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A Class to get a connection from the connection factory
 */
public class ConnectionFactory {
    private Properties props = new Properties();
    private static ConnectionFactory connFactory = new ConnectionFactory();

    private ConnectionFactory(){
        try{
            props.load(new FileReader("D:/Revature/troy_davis_p1/src/main/resources/application.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the current instance of the Connection to the DB
     * @return returns and instance of thte connection
     */
    public static ConnectionFactory getInstance(){
        return connFactory;
    }

    /**
     * A method to get a connection from the connection factory
     * @return returns a connection to the DB
     */
    public Connection getConnection(){
        Connection conn = null;
        try {
            // Force the JVM to load the PostGreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            throw new RuntimeException("Failed to establish connection.");
        }
        return conn;
    }

}
