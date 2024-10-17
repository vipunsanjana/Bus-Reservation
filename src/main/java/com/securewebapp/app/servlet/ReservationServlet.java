package com.securewebapp.app.servlet;

import com.securewebapp.app.api.Endpoint;
import com.securewebapp.app.api.Pages;
import com.securewebapp.app.repository.ReservationRepository;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationServlet extends HttpServlet {
    // Logger for this servlet class
    private static final Logger logger = Logger.getLogger(ReservationServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // List to hold reservation details retrieved from the repository
            List<HashMap<String, Object>> reservationsDetails;

            // Retrieve the requested session ID from the request
            String userSessionId = req.getRequestedSessionId();

            // Check if the requested session ID is not null
            if (userSessionId != null) {
                // Get the current session (if it exists)
                HttpSession session = req.getSession(false);

                // Validate the session ID matches the requested session ID
                if (session != null && session.getId().equals(userSessionId)) {
                    // Retrieve user ID and CSRF token from the session
                    String userId = (String) session.getAttribute("userId");
                    String csrfToken = (String) session.getAttribute("csrfToken");

                    // Instantiate the repository to fetch reservation details
                    ReservationRepository reservationRepository = new ReservationRepository();
                    reservationsDetails = reservationRepository.getReservationsDetails(userId);

                    // Check if reservation details are not null
                    if (reservationsDetails != null) {
                        // If reservation details are not empty, forward to the reservation page
                        if (!reservationsDetails.isEmpty()) {
                            req.setAttribute("csrfToken", csrfToken); // Set CSRF token as a request attribute
                            req.setAttribute("reservationsDetails", reservationsDetails); // Set reservation details as a request attribute
                            req.getRequestDispatcher(Pages.reservation).forward(req, res); // Forward request to reservation page
                        } else {
                            // If no reservations, set message and forward to the action page
                            req.setAttribute("msg", "empty");
                            req.getRequestDispatcher(Pages.reservationAction).forward(req, res);
                        }
                    } else {
                        // If there was an error fetching reservations, set error message and forward
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
