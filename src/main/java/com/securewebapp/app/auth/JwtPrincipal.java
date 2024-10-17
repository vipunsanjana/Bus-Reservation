package com.securewebapp.app.auth;

import com.auth0.jwt.interfaces.DecodedJWT;

import javax.security.enterprise.CallerPrincipal;

/**
 * JwtPrincipal represents the principal extracted from a decoded JWT token.
 */
public class JwtPrincipal extends CallerPrincipal {
    private final DecodedJWT idToken; // The decoded JWT token

    // Constructor initializes the principal with the name claim
    public JwtPrincipal(DecodedJWT idToken) {
        super(idToken.getClaim("name").asString()); // Set the principal's name from the JWT claim
        this.idToken = idToken; // Store the decoded token
    }

    // Getter for the decoded JWT token
    public DecodedJWT getIdToken() {
        return this.idToken; // Return the JWT token
    }
}
