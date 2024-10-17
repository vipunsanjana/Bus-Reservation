package com.securewebapp.app.servlet;

import com.securewebapp.app.auth.AuthConfig;
import com.securewebapp.app.auth.AuthUser;
import com.securewebapp.app.api.Endpoint;
import com.securewebapp.app.api.Pages;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileServlet extends HttpServlet {
    // Logger for the ProfileServlet class
    private static final Logger logger = Logger.getLogger(ProfileServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Retrieve the requested session ID from the request
            String userSessionId = req.getRequestedSessionId();

            // Check if the session ID is not null
            if (userSessionId != null) {
                // Retrieve the current session without creating a new one
                HttpSession session = req.getSession(false);

                // Validate that the session exists and matches the requested session ID
                if (session != null && session.getId().equals(userSessionId)) {
                    // Get the access token from the session
                    String accessToken = (String) session.getAttribute("accessToken");

                    // Create an AuthConfig object to retrieve authentication configurations
                    AuthConfig authConfig = new AuthConfig();
                    // Create an AuthUser object using the domain and access token
                    AuthUser authUser = new AuthUser(authConfig.getDomain(), accessToken);
                    // Retrieve user information as a JSON object
                    JSONObject userInfoObject = authUser.getInfo();

                    // Prepare a HashMap to store user information
                    HashMap<String, Object> userInfo = new HashMap<>();
                    userInfo.put("email", userInfoObject.get("email")); // User's email
                    userInfo.put("emailVerification", userInfoObject.get("email_verified")); // Email verification status
                    userInfo.put("picture", userInfoObject.get("picture")); // User's profile picture
                    userInfo.put("fullName", userInfoObject.get("nickname")); // User's full name
                    userInfo.put("name", userInfoObject.get("nickname")); // User's nickname

                    // Set the user info in the request attribute for the JSP page
                    req.setAttribute("userInfo", userInfo);
                    // Forward the request to the user profile page
                    req.getRequestDispatcher(Pages.userProfile).forward(req, res);
                } else {
                    // If session is invalid, redirect to the login page
                    res.sendRedirect(Endpoint.login);
                }
            } else {
                // If there is no session ID, redirect to the login page
                res.sendRedirect(Endpoint.login);
            }
        } catch (ServletException | IOException | JSONException | NullPointerException ex) {
            // Log any exceptions that occur during the process
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
            // Redirect to the root endpoint in case of errors
            res.sendRedirect(Endpoint.root);
        }
    }
}
