package spring.microservice.dto.response;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class BaseResponse<T> {

    private String message;
    private T payload;

    @Builder.Default
    private Instant timestamp = Instant.now();
}
