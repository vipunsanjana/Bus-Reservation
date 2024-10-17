package com.securewebapp.app.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.security.enterprise.credential.Credential;

/**
 * JwtCredential represents a JWT token credential.
 * It contains the decoded JWT and provides access to its principal.
 */
public class JwtCredential implements Credential {
    private final JwtPrincipal jwtPrincipal; // The principal extracted from the JWT

    // Constructor that decodes the token and initializes the principal
    public JwtCredential(String token) {
        DecodedJWT decodedJWT = JWT.decode(token); // Decode the JWT token
        this.jwtPrincipal = new JwtPrincipal(decodedJWT); // Initialize the JwtPrincipal
    }

    // Getter for the JwtPrincipal
    public JwtPrincipal getAuth0JwtPrincipal() {
        return jwtPrincipal; // Return the principal
    }
}
