package com.securewebapp.app.helper;

import org.owasp.encoder.Encode;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * InputValidator provides methods to validate and sanitize input data.
 */
public class InputValidator {
    // Regular expression pattern for alphanumeric strings
    private static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]*$";
    
    // Regular expression pattern for numeric strings
    private static final String NUMERIC_PATTERN = "^[0-9]*$";

    // Private constructor to prevent instantiation
    private InputValidator(){}

    /**
     * Checks if the input string is alphanumeric.
     *
     * @param input The input string to validate.
     * @return true if the input is alphanumeric; false otherwise.
     */
    public static boolean isAlphanumeric(String input) {
        Pattern pattern = Pattern.compile(ALPHANUMERIC_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Return true if input matches the pattern
    }

    /**
     * Checks if the input string is numeric.
     *
     * @param input The input string to validate.
     * @return true if the input is numeric; false otherwise.
     */
    public static boolean isNumeric(String input){
        Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Return true if input matches the pattern
    }

    /**
     * Validates if the input string is in a valid date format (YYYY-MM-DD).
     *
     * @param input The input string to validate.
     * @return true if the input is a valid date; false otherwise.
     */
    public static boolean isValidDate(String input){
        // Regular expression pattern for validating date format
        Pattern pattern = Pattern.compile("^((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])*$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Return true if input matches the date format
    }

    /**
     * Sanitizes the input string for HTML to prevent XSS attacks.
     *
     * @param input The input string to sanitize.
     * @return A sanitized string safe for HTML output.
     */
    public static String sanitizeHtml(String input) {
        input = Encode.forHtml(input); // Encode input to escape HTML characters
        return input; // Return the sanitized input
    }
}
