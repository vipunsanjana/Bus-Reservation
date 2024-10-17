package com.securewebapp.app.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 * JwtIdentityStore is an implementation of IdentityStore for validating JWT credentials.
 */
@ApplicationScoped
public class JwtIdentityStore implements IdentityStore {
    
    /**
     * Validates the provided credential and returns a validation result.
     *
     * @param credential The credential to validate.
     * @return The CredentialValidationResult indicating the outcome of the validation.
     */
    @Override
    public CredentialValidationResult validate(final Credential credential) {
        CredentialValidationResult result = CredentialValidationResult.NOT_VALIDATED_RESULT; // Default result
        if (credential instanceof JwtCredential) {
            JwtCredential jwtCredential = (JwtCredential) credential; // Cast to JwtCredential
            result = new CredentialValidationResult(jwtCredential.getAuth0JwtPrincipal()); // Create validation result
        }
        return result; // Return the result
    }
}
