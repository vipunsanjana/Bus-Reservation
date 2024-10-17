package com.securewebapp.app.servlet;

import com.auth0.AuthenticationController;
import com.securewebapp.app.api.Endpoint;
import com.securewebapp.app.auth.AuthConfig;
import com.securewebapp.app.auth.AuthenticationProvider;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {
    // Logger for the LoginServlet class
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse res) throws IOException {
        try {
            // Construct the callback URL where Auth0 will redirect after authentication
            String callbackUrl = String.format(
                    "%s://%s:%s/callback",
                    "http", // Protocol
                    "localhost", // Hostname
                    "8080" // Port
            );

            // Create an AuthConfig object to retrieve authentication configurations
            AuthConfig config = new AuthConfig();
            // Initialize the AuthenticationProvider to manage authentication
            AuthenticationProvider authenticationProvider = new AuthenticationProvider();
            // Get the AuthenticationController instance for handling authentication
            AuthenticationController authenticationController = authenticationProvider.authenticationController(config);
            
            // Build the authorization URL using the AuthenticationController
            String authURL = authenticationController.buildAuthorizeUrl(request, res, callbackUrl)
                    .withScope(config.getScope()) // Set the requested scopes
                    .build(); // Build the URL

            // Redirect the user to the authorization URL to initiate the login process
            res.sendRedirect(authURL);
        } catch (IOException ex) {
            // Log any IOException that occurs during the process
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
            // Redirect to the root endpoint in case of errors
            res.sendRedirect(Endpoint.root);
        }
    }
}
