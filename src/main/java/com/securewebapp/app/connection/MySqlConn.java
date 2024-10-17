package com.securewebapp.app.connection;

import com.securewebapp.app.auth.AuthConfig;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MySqlConn provides methods to establish a connection to a MySQL database.
 */
public class MySqlConn {
    private static final String CONFIG_FILE = "config.properties"; // Configuration file name
    private static Properties properties = new Properties(); // Properties object to hold database connection settings
    private static final Logger logger = Logger.getLogger(MySqlConn.class.getName()); // Logger for logging errors

    // Static block to load properties from the configuration file
    static {
        try (InputStream input = AuthConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input); // Load properties from the file
            } else {
                logger.log(Level.CONFIG, "An error occurred while reading configuration file");
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex); // Log any exceptions that occur
        }
    }

    // Private constructor to prevent instantiation
    private MySqlConn() {}

    /**
     * Establishes a connection to the MySQL database using the properties defined in the configuration file.
     *
     * @return A Connection object for the database, or null if the connection fails.
     */
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
            Connection connection = DriverManager.getConnection(
                    properties.getProperty("mysql.conn.url"), // Database URL
                    properties.getProperty("mysql.conn.user"), // Database username
                    properties.getProperty("mysql.conn.password")); // Database password
            return connection; // Return the established connection
        } catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex); // Log any exceptions that occur
        }

        return null; // Return null if the connection could not be established
    }
}
