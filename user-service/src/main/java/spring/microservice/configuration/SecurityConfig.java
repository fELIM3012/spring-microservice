package spring.microservice.configuration;

import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import spring.microservice.config.BaseSecurityConfig;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig extends BaseSecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    @Override
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return super.securityWebFilterChain(http);
    }

    @Override
    protected void configureAuthorizeExchange(AuthorizeExchangeSpec exchanges) {
        exchanges.pathMatchers("/api/auth/**", "/health", "/actuator/**").permitAll()
                .anyExchange().authenticated();
    }

    @Bean
    @Override
    public ReactiveJwtDecoder jwtDecoder() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
