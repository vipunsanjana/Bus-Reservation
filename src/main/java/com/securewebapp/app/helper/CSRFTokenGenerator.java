package com.securewebapp.app.helper;

import com.securewebapp.app.servlet.CallbackServlet;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CSRFTokenGenerator is responsible for generating secure CSRF tokens.
 */
public class CSRFTokenGenerator {
    private static final Logger logger = Logger.getLogger(CallbackServlet.class.getName());

    /**
     * Generates a secure CSRF token.
     *
     * @return A Base64 encoded CSRF token as a string, or null if an error occurs.
     */
    public String generate() {
        try {
            // Create a SecureRandom instance using SHA1PRNG algorithm
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] data = new byte[16]; // Array to hold random bytes
            secureRandom.nextBytes(data); // Fill the array with secure random bytes

            // Encode the byte array to a Base64 string and return it
            return Base64.getEncoder().encodeToString(data);
        } catch (NoSuchAlgorithmException ex){
            // Log the exception if the algorithm is not found
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
        }

        return null; // Return null if token generation fails
    }
}
