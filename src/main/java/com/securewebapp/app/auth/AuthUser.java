package com.securewebapp.app.auth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AuthUser represents a user authenticated via access token.
 * It provides a method to retrieve user information from the authorization server.
 */
public class AuthUser {
    private final String accessToken; // Access token for authentication
    private final String domain; // Domain for the user info endpoint
    private static final Logger logger = Logger.getLogger(AuthUser.class.getName()); // Logger for error handling

    // Constructor for AuthUser, initializes domain and access token
    public AuthUser(String domain, String accessToken){
        this.domain = domain;
        this.accessToken = accessToken;
    }

    /**
     * Retrieves user information from the userinfo endpoint.
     *
     * @return A JSONObject containing user information, or null if an error occurs.
     * @throws IOException If an I/O error occurs during the request.
     */
    public JSONObject getInfo() throws IOException {
        try {
            String userInfoUrl = "https://" + domain + "/userinfo"; // User info endpoint URL

            HttpClient httpClient = HttpClient.newHttpClient(); // Create an HTTP client
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(userInfoUrl)) // Set the request URI
                    .header("Authorization", "Bearer " + accessToken) // Add authorization header
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body()); // Parse response body to JSONObject

            // Check the response status code
            if (response.statusCode() == 200) {
                return jsonObject; // Return user info if successful
            } else {
                logger.log(Level.WARNING, "An error occurred while retrieving user profile details"); // Log warning if failed
            }
        } catch (InterruptedException | JSONException ex){
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex); // Log any exceptions
        }

        return null; // Return null if user info retrieval fails
    }
}
