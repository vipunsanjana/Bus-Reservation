package com.securewebapp.app.api;

/**
 * ApplicationURL provides a utility for constructing the base URL
 * of the application based on predefined scheme, domain, and port.
 */
public class ApplicationURL {
    private static final String scheme = "http"; // URL scheme (http or https)
    private static final String domainName = "localhost"; // Domain name or IP address
    private static final int port = 8080; // Port number the application listens on

    // Private constructor to prevent instantiation
    private ApplicationURL(){}

    /**
     * Constructs and returns the complete application URL.
     *
     * @return A string representing the application URL in the format scheme://domainName:port
     */
    public static String getURL(){
        return scheme + "://" + domainName + ":" + port; // Construct and return the URL
    }
}
