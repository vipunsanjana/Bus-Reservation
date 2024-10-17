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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationViewServlet extends HttpServlet {
    // Logger for this servlet class
    private static final Logger logger = Logger.getLogger(ReservationViewServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Retrieve the session ID requested by the client
            String userSessionId = req.getRequestedSessionId();

            // Check if the requested session ID is not null
            if (userSessionId != null) {
                // Get the current session without creating a new one
                HttpSession session = req.getSession(false);

                // Validate the session ID matches the requested session ID
                if (session != null && session.getId().equals(userSessionId)) {
                    // Retrieve user ID and CSRF token from the session
                    String userId = (String) session.getAttribute("userId");
                    String csrfToken = (String) session.getAttribute("csrfToken");
                    String requestedCsrfToken = req.getParameter("token");

                    // Verify CSRF token to prevent CSRF attacks
                    if (!csrfToken.equals(requestedCsrfToken)) {
                        req.setAttribute("msg", "error");
                        req.getRequestDispatcher(Pages.reservationAction).forward(req, res);
                        return; // Exit method if CSRF token validation fails
                    }

                    // Get the booking ID from the request parameters
                    String bookingId = req.getParameter("bid");

                    // Validate that the booking ID is numeric
                    if (bookingId != null && InputValidator.isNumeric(bookingId)) {
                        HashMap<String, Object> reservationDetails; // To hold reservation details

                        // Instantiate the repository to fetch reservation details
                        ReservationRepository reservationRepository = new ReservationRepository();
                        reservationDetails = reservationRepository.getReservationDetails(userId, bookingId);

                        // Check if reservation details are not null
                        if (reservationDetails != null) {
                            // If reservation details are found, forward to the reservation view page
                            if (!reservationDetails.isEmpty()) {
                                req.setAttribute("reservationDetails", reservationDetails); // Set reservation details as request attribute
                                req.setAttribute("csrfToken", csrfToken); // Set CSRF token as request attribute
                                req.getRequestDispatcher(Pages.reservationView).forward(req, res);
                            } else {
                                // If no reservation details are found, set message and forward to the action page
                                req.setAttribute("msg", "empty");
                                req.getRequestDispatcher(Pages.reservationAction).forward(req, res);
                            }
                        } else {
                            // If there was an error fetching reservation details, set error message and forward
                            req.setAttribute("msg", "error");
                            req.getRequestDispatcher(Pages.reservationAction).forward(req, res);
                        }
                    } else {
                        // If booking ID is invalid, set error message and forward to the action page
                        req.setAttribute("msg", "error");
                        req.getRequestDispatcher(Pages.reservationAction).forward(req, res);
                    }
                } else {
                    // If session is invalid, redirect to the login page
                    res.sendRedirect(Endpoint.login);
                }
            } else {
                // If requested session ID is null, redirect to the login page
                res.sendRedirect(Endpoint.login);
            }
        } catch (ServletException | IOException | NullPointerException ex) {
            // Log any exceptions that occur during request processing
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
            // Redirect to the root endpoint in case of errors
            res.sendRedirect(Endpoint.root);
        }
    }
}
