package com.securewebapp.app.filter;

import com.securewebapp.app.api.ApplicationURL;
import com.securewebapp.app.api.Endpoint;
import com.securewebapp.app.auth.AuthConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RefererFilter is a servlet filter that checks the Referer header
 * of incoming requests to enhance security by ensuring that requests
 * are coming from valid sources.
 */
public class RefererFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthConfig.class.getName());

    // Initialization method for the filter
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * Filters incoming requests to validate the Referer header.
     *
     * @param req The servlet request.
     * @param res The servlet response.
     * @param chain The filter chain to pass the request and response to the next filter or resource.
     * @throws IOException If an input or output error occurs.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException {
        try {
            HttpServletRequest request = (HttpServletRequest) req; // Cast request to HttpServletRequest
            String referer = request.getHeader("Referer"); // Get the Referer header from the request

            // Validate the Referer header
            if (isValidReferer(request, referer)) {
                chain.doFilter(req, res); // If valid, continue processing the request
            } else {
                // Log the error and redirect to the root endpoint if the Referer is invalid
                logger.log(Level.SEVERE, "An error occurred while validating referer header");
                ((HttpServletResponse) res).sendRedirect(Endpoint.root);
            }
        } catch (ServletException | IOException ex) {
            // Log any exceptions and redirect to the root endpoint
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex);
            ((HttpServletResponse) res).sendRedirect(Endpoint.root);
        }
    }

    /**
     * Validates the Referer header against the application's URL.
     *
     * @param request The HttpServletRequest to check.
     * @param referer The value of the Referer header.
     * @return true if the Referer is valid, false otherwise.
     */
    private boolean isValidReferer(HttpServletRequest request, String referer) {
        // Check if the Referer is valid and starts with the application's URL
        if (referer != null && referer.startsWith(ApplicationURL.getURL())) {
            return true; // Valid referer
        } else {
            // Check if the request URL matches the application's URL
            return request.getRequestURL().toString().startsWith(ApplicationURL.getURL());
        }
    }

    // Cleanup method for the filter
    @Override
    public void destroy() {
    }
}
