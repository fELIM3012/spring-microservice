package spring.microservice.exception;


import reactor.core.publisher.Mono;
import spring.microservice.dto.response.BaseResponse;

public abstract class BaseGlobalExceptionHandler {

    protected Mono<BaseResponse<String>> handleGeneralException(Exception ex) {
        return Mono.just(
                BaseResponse.<String>builder()
                        .message(ex.getMessage())
                        .payload("Internal server error")
                        .build()
        );
    }
}
