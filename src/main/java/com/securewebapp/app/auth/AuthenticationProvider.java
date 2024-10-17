package com.securewebapp.app.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.AuthenticationController;

/**
 * AuthenticationProvider is responsible for producing an instance of 
 * AuthenticationController used for handling authentication processes.
 */
@ApplicationScoped
public class AuthenticationProvider {
    
    /**
     * Produces an AuthenticationController instance configured with
     * domain, client ID, and client secret from AuthConfig.
     *
     * @param config The AuthConfig object to retrieve authentication configurations.
     * @return A configured AuthenticationController instance.
     */
    @Produces
    public AuthenticationController authenticationController(AuthConfig config) {
        JwkProvider jwkProvider = new JwkProviderBuilder(config.getDomain()).build(); // Build JWK provider
        return AuthenticationController.newBuilder(
                        config.getDomain(),
                        config.getClientId(),
                        config.getClientSecret())
                .withJwkProvider(jwkProvider) // Attach JWK provider
                .build(); // Build and return AuthenticationController
    }
}
