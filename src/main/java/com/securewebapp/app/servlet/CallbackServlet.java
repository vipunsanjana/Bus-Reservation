package com.securewebapp.app.servlet;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.SessionUtils;
import com.auth0.Tokens;
import com.securewebapp.app.auth.*;
import com.securewebapp.app.api.Endpoint;
import com.securewebapp.app.helper.CSRFTokenGenerator;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CallbackServlet extends HttpServlet {
    // Logger for this servlet class
    private static final Logger logger = Logger.getLogger(AuthConfig.class.getName());
    private AuthenticationController authenticationController;

    @Override
    public void init() {
        // Initialize AuthConfig and AuthenticationProvider to configure authentication
        AuthConfig configs = new AuthConfig();
        AuthenticationProvider authenticationProvider = new AuthenticationProvider();
        authenticationController = authenticationProvider.authenticationController(configs);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Handle GET requests by delegating to the handle method
        handle(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Handle POST requests by delegating to the handle method
        handle(req, res);
    }

    private void handle(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Process the request and obtain tokens from the authentication controller
            Tokens tokens = authenticationController.handle(req, res);
            
            // Store the access and ID tokens in the session
            SessionUtils.set(req, "accessToken", tokens.getAccessToken());
            SessionUtils.set(req, "idToken", tokens.getIdToken());

            // Create a JwtCredential object from the ID token to extract user information
            JwtCredential jwtCredential = new JwtCredential(tokens.getIdToken());
            JwtPrincipal jwtPrincipal = jwtCredential.getAuth0JwtPrincipal();
            
            // Store the user ID in the session
            SessionUtils.set(req, "userId", jwtPrincipal.getName());

            // Generate a CSRF token and store it in the session for security
            CSRFTokenGenerator csrfTokenGenerator = new CSRFTokenGenerator();
            SessionUtils.set(req, "csrfToken", csrfTokenGenerator.generate());

            // Redirect the user to the reservation page upon successful authentication
            res.sendRedirect(Endpoint.reservation);
        } catch (IdentityVerificationException | IOException ex) {
            // Log any exceptions that occur during authentication handling
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
            // Redirect to the login page in case of errors
            res.sendRedirect(Endpoint.login);
        }
    }
}
