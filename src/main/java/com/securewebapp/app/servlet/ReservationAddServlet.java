package com.securewebapp.app.servlet;

import com.securewebapp.app.api.Endpoint;
import com.securewebapp.app.api.Pages;
import com.securewebapp.app.repository.ReservationRepository;
import com.securewebapp.app.helper.InputValidator;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationAddServlet extends HttpServlet {
    // Logger for the ReservationAddServlet class
    private static final Logger logger = Logger.getLogger(ReservationAddServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Retrieve the requested session ID from the request
            String userSessionId = req.getRequestedSessionId();

            // Check if the session ID is not null
            if (userSessionId != null) {
                // Retrieve the current session without creating a new one
                HttpSession session = req.getSession(false);

                // Check if the session exists
                if (session != null) {
                    // Retrieve the CSRF token from the session
                    String csrfToken = (String) session.getAttribute("csrfToken");

                    // Set the CSRF token as a request attribute for the JSP page
                    req.setAttribute("csrfToken", csrfToken);
                    // Forward the request to the reservation addition page
                    req.getRequestDispatcher(Pages.reservationAdd).forward(req, res);
                } else {
                    // If session is invalid, redirect to the login page
                    res.sendRedirect(Endpoint.login);
                }
            } else {
                // If there is no session ID, redirect to the login page
                res.sendRedirect(Endpoint.login);
            }
        } catch (ServletException | IOException ex) {
            // Log any exceptions that occur during the process
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
            // Redirect to the root endpoint in case of errors
            res.sendRedirect(Endpoint.root);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
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

                    // Retrieve reservation details from the request parameters
                    String reservationDate = req.getParameter("date");
                    String reservationTime = req.getParameter("time");
                    String reservationLocation = req.getParameter("location");
                    String reservationVehicleNo = req.getParameter("vehicleno");
                    String reservationMileage = req.getParameter("mileage");
                    String reservationMessage = req.getParameter("message");

                    // Check if all required fields are filled
                    if (reservationDate != null && reservationTime != null &&
                        reservationLocation != null && reservationVehicleNo != null &&
                        reservationMileage != null && reservationMessage != null) {
                        
                        HashMap<String, String> postValidatedData = new HashMap<>();

                        // Validate the input data
                        if (InputValidator.isValidDate(reservationDate) &&
                            InputValidator.isNumeric(reservationTime) &&
                            InputValidator.isAlphanumeric(reservationLocation) &&
                            InputValidator.isAlphanumeric(reservationVehicleNo) &&
                            InputValidator.isNumeric(reservationMileage) &&
                            InputValidator.isAlphanumeric(reservationMessage)) {
                            
                            // Add validated data to the map
                            postValidatedData.put("reservationDate", reservationDate);
                            postValidatedData.put("reservationTime", reservationTime);
                            postValidatedData.put("reservationLocation", reservationLocation);
                            postValidatedData.put("reservationVehicleNo", reservationVehicleNo);
                            postValidatedData.put("reservationMileage", reservationMileage);
                            postValidatedData.put("reservationMessage", reservationMessage);
                            postValidatedData.put("userName", userId);

                            // Create a ReservationRepository instance to handle reservation data
                            ReservationRepository reservationRepository = new ReservationRepository();
                            // Attempt to add reservation details
                            if (reservationRepository.addReservationDetails(postValidatedData)) {
                                req.setAttribute("msg", "success"); // Set success message
                                req.getRequestDispatcher(Pages.reservationAction).forward(req, res); // Forward to action page
                            } else {
                                req.setAttribute("msg", "error"); // Set error message
                                req.getRequestDispatcher(Pages.reservationAction).forward(req, res); // Forward to action page
                            }
                        } else {
                            req.setAttribute("msg", "error"); // Set error message
                            req.getRequestDispatcher(Pages.reservationAction).forward(req, res); // Forward to action page
                        }
                    } else {
                        req.setAttribute("msg", "error"); // Set error message
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
