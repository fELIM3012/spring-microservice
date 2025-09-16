package spring.microservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;
import spring.microservice.dto.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseGlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Mono<BaseResponse<String>> handleGeneralException(Exception ex) {
        return Mono.just(
                BaseResponse.<String>builder()
                        .message(ex.getMessage())
                        .payload("Internal server error")
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<BaseResponse<Map<String , List<String>>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String , List<String>> messages = new HashMap<>();

        List<String> messagesLists = new ArrayList<String>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            messagesLists.add(error.getDefaultMessage());
        });

        messages.put("messages" , messagesLists);

        return Mono.just(
                BaseResponse.<Map<String, List<String>>>builder()
                        .message(ex.getMessage())
                        .payload(messages)
                        .build()
        );
    }
}
