package com.securewebapp.app.servlet;

import com.securewebapp.app.api.Endpoint;
import com.securewebapp.app.auth.AuthConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogoutServlet extends HttpServlet {
    // Logger for the LogoutServlet class
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        try {
            // Clear the user's session to log them out
            clearSession(req);
            // Redirect to the logout URL after clearing the session
            res.sendRedirect(getLogoutUrl(req));
        } catch (IOException ex) {
            // Log any IOException that occurs during the process
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
            // Redirect to the root endpoint in case of errors
            res.sendRedirect(Endpoint.root);
        }
    }

    // Method to invalidate the user's session
    private void clearSession(HttpServletRequest request) {
        // Check if the session exists and invalidate it
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

    // Method to construct the logout URL
    private String getLogoutUrl(HttpServletRequest request) {
        // Create the base return URL based on the request's scheme and server name
        String returnUrl = String.format("%s://%s", request.getScheme(), request.getServerName());
        int port = request.getServerPort(); // Get the server port
        String scheme = request.getScheme(); // Get the request scheme (http/https)

        // Append the port number if it's not the default for the scheme
        if (("http".equals(scheme) && port != 80) ||
                ("https".equals(scheme) && port != 443)) {
            returnUrl += ":" + port; // Append the port if it's not default
        }

        returnUrl += "/"; // Append a trailing slash

        // Create an AuthConfig object to retrieve authentication configurations
        AuthConfig config = new AuthConfig();

        // Construct the logout URL using the Auth0 domain, client ID, and return URL
        return String.format(
                "https://%s/v2/logout?client_id=%s&returnTo=%s",
                config.getDomain(),
                config.getClientId(),
                returnUrl
        );
    }
}
