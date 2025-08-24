package spring.microservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import spring.microservice.config.BaseSecurityConfig;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig extends BaseSecurityConfig {

    @Bean
    @Override
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return super.securityWebFilterChain(http);
    }

    @Override
    protected void configureAuthorizeExchange(AuthorizeExchangeSpec exchanges) {
        exchanges.pathMatchers("/api/auth/**", "/health").permitAll()
                .anyExchange().authenticated();
    }

    @Bean
    @Override
    public ReactiveJwtDecoder jwtDecoder() {
        return super.jwtDecoder();
    }
}
