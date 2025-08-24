package spring.microservice.filters;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

@Component
public class AuthorizationHeaderFilter {

    @Bean
    public GlobalFilter authorizationHeaderForwardingFilter() {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader != null) {
                exchange = exchange.mutate()
                        .request(r -> r.headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, authHeader)))
                        .build();
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {}));
        };
    }
}
