package com.securewebapp.app.filter;

import com.securewebapp.app.api.Endpoint;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthFilter is a servlet filter that checks user authentication
 * before allowing access to protected resources.
 */
public class AuthFilter implements Filter {

    // Initialization method for the filter
    public void init(FilterConfig filterConfig) {}

    /**
     * Filters requests to check if the user is authenticated.
     *
     * @param req The servlet request.
     * @param res The servlet response.
     * @param chain The filter chain to pass the request and response to the next filter or resource.
     * @throws IOException If an input or output error occurs.
     * @throws ServletException If the request could not be handled.
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) req; // Cast request to HttpServletRequest
            HttpServletResponse response = (HttpServletResponse) res; // Cast response to HttpServletResponse

            // Check if the user is authenticated
            if (isAuthenticated(request)) {
                chain.doFilter(request, response); // User is authenticated, continue processing the request
                return;
            }

            // User is not authenticated, redirect to the login page
            response.sendRedirect(Endpoint.login);
        } catch (ServletException | IOException ex) {
            // On exception, redirect to the root endpoint
            ((HttpServletResponse) res).sendRedirect(Endpoint.root);
        }
    }

    // Cleanup method for the filter
    public void destroy() {}

    /**
     * Checks if the user is authenticated based on session attributes.
     *
     * @param request The HttpServletRequest to check.
     * @return true if the user is authenticated, false otherwise.
     */
    private boolean isAuthenticated(HttpServletRequest request) {
        // Check if the session exists and contains a valid user ID
        return request.getSession(false) != null
                && request.getSession().getAttribute("userId") != null;
    }
}
