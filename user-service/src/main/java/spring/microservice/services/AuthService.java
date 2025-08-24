package spring.microservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import spring.microservice.dto.request.CustomUserDetails;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private ReactiveUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.expiration:295000}")
    private Long jwtExpirationSeconds;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public Mono<String> authenticate(String username, String password) {
        return userDetailsService.findByUsername(username)
                .filter(userDetails -> passwordEncoder.matches(password, userDetails.getPassword()))
                .map(userDetails -> {
                    CustomUserDetails customUser = (CustomUserDetails) userDetails;
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("id", customUser.getId());
                    claims.put("email", customUser.getEmail());
                    return generateJwtToken(customUser.getUsername(), claims);
                })
                .switchIfEmpty(Mono.error(new Exception("Invalid credentials")));
    }

    private String generateToken(String subject, Map<String, Object> claims, Long expirationSeconds) {
        Instant now = Instant.now();
        Instant expiration = now.plus(expirationSeconds, ChronoUnit.SECONDS);

        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSigningKey())
                .compact();
    }

    private String generateJwtToken(String username, Map<String, Object> claims) {
        return generateToken(username, claims, jwtExpirationSeconds);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

