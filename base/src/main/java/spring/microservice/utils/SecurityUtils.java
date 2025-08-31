package spring.microservice.utils;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public class SecurityUtils {

    private SecurityUtils() {}

    public static UUID extractUserId(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            String userId = jwtAuth.getToken().getClaimAsString("id");
            if (userId != null) {
                return UUID.fromString(userId);
            }
            throw new BadCredentialsException("User ID claim not found in token");
        }
        throw new BadCredentialsException("Invalid authentication type");
    }
}
