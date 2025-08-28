package spring.microservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import spring.microservice.dto.response.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<BaseResponse<String>> handleAuthenticationException(BadCredentialsException ex) {
        return Mono.just(
                BaseResponse.<String>builder()
                        .message(ex.getMessage())
                        .payload("Unauthorized")
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<BaseResponse<String>> handleGeneralException(Exception ex) {
        return super.handleGeneralException(ex);
    }
}
