package com.securewebapp.app.filter;

import com.securewebapp.app.api.Endpoint;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CSPFilter is a servlet filter that sets the Content Security Policy
 * (CSP) header to mitigate cross-site scripting (XSS) and other attacks.
 */
public class CSPFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        // Initialization method for the filter
    }

    /**
     * Sets the Content Security Policy header for the response
     * to enhance security against XSS attacks.
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
            HttpServletResponse response = (HttpServletResponse) res; // Cast response to HttpServletResponse

            // Define the Content Security Policy
            String POLICY = "default-src 'self';" +
                    "script-src 'self' 'unsafe-inline';" +
                    "style-src 'self' 'unsafe-inline';" +
                    "img-src 'self' https://*.gravatar.com/ https://*.auth0.com/ " +
                    "https://*.wp.com/ https://*.googleusercontent.com/;" +
                    "font-src 'self';" +
                    "object-src 'none';" +
                    "frame-ancestors 'none';" +
                    "base-uri 'self';" +
                    "form-action 'self';" +
                    "block-all-mixed-content;";

            response.setHeader("Content-Security-Policy", POLICY); // Set CSP header
            chain.doFilter(request, response); // Continue processing the request
        } catch (ServletException | IOException ex) {
            // On exception, redirect to the root endpoint
            ((HttpServletResponse) res).sendRedirect(Endpoint.root);
        }
    }

    // Cleanup method for the filter
    @Override
    public void destroy() {}
}
