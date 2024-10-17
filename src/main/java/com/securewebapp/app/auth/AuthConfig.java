package com.securewebapp.app.auth;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AuthConfig loads and provides access to authentication configuration properties
 * from a properties file.
 */
@ApplicationScoped
public class AuthConfig {
    private static final String CONFIG_FILE = "config.properties"; // Name of the configuration file
    private static final Properties properties = new Properties(); // Properties object to hold configurations
    private static final Logger logger = Logger.getLogger(AuthConfig.class.getName()); // Logger for error handling

    // Static block to load properties from the configuration file
    static {
        try (InputStream input = AuthConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input); // Load properties from file
            } else {
                logger.log(Level.CONFIG, "An error occurred while reading configuration file"); // Log if file is not found
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex); // Log any exceptions
        }
    }

    // Getters for authentication configuration properties
    public String getDomain() {
        return properties.getProperty("okta.client.domain");
    }
    
    public String getClientId() {
        return properties.getProperty("okta.client.id");
    }
    
    public String getClientSecret() {
        return properties.getProperty("okta.client.secret");
    }

    public String getScope() {
        return properties.getProperty("okta.client.scope");
    }
}
