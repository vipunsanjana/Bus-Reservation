package com.securewebapp.app.servlet;

import com.securewebapp.app.api.Endpoint;
import com.securewebapp.app.api.Pages;
import com.securewebapp.app.helper.InputValidator;
import com.securewebapp.app.repository.ReservationRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationDeleteServlet extends HttpServlet {
    // Logger for the ReservationDeleteServlet class
    private static final Logger logger = Logger.getLogger(ReservationDeleteServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        try {
            // Retrieve the requested session ID from the request
            String userSessionId = req.getRequestedSessionId();

            // Check if the session ID is not null
            if (userSessionId != null) {
                // Retrieve the current session without creating a new one
                HttpSession session = req.getSession(false);

                // Validate that the session exists and matches the requested session ID
                if (session != null && session.getId().equals(userSessionId)) {
                    // Retrieve user ID and CSRF token from the session
                    String userId = (String) session.getAttribute("userId");
                    String csrfToken = (String) session.getAttribute("csrfToken");
                    // Retrieve the CSRF token from the request parameter
                    String requestedCsrfToken = req.getParameter("token");

                    // Check if the CSRF tokens match
                    if (!csrfToken.equals(requestedCsrfToken)) {
                        req.setAttribute("msg", "error"); // Set error message
                        req.getRequestDispatcher(Pages.reservationAction).forward(req, res); // Forward to action page
                        return;
                    }

                    // Retrieve the booking ID from the request parameters
                    String bookingId = req.getParameter("bid");

                    // Validate that the booking ID is not null and is numeric
                    if (bookingId != null && InputValidator.isNumeric(bookingId)) {
                        // Create a ReservationRepository instance to handle reservation data
                        ReservationRepository reservationRepository = new ReservationRepository();
                        // Attempt to delete reservation details by booking ID and user ID
                        boolean result = reservationRepository.deleteReservationDetailsById(bookingId, userId);

                        // Check if the deletion was successful
                        if (!result) {
                            req.setAttribute("msg", "error"); // Set error message for unsuccessful deletion
                            req.getRequestDispatcher(Pages.reservationAction).forward(req, res); // Forward to action page
                        }

                        // Redirect to the reservations page after successful deletion
                        res.sendRedirect(Endpoint.reservation);
                    } else {
                        req.setAttribute("msg", "error"); // Set error message for invalid booking ID
                        req.getRequestDispatcher(Pages.reservationAction).forward(req, res); // Forward to action page
                    }
                } else {
                    // If session is invalid, redirect to the login page
                    res.sendRedirect(Endpoint.login);
                }
            } else {
                // If there is no session ID, redirect to the login page
                res.sendRedirect(Endpoint.login);
            }
        } catch (ServletException | IOException | NullPointerException ex) {
            // Log any exceptions that occur during the process
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
            // Redirect to the root endpoint in case of errors
            res.sendRedirect(Endpoint.root);
        }
    }
}
