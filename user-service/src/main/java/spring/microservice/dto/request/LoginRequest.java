package spring.microservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull
    private String credentials;

    @NotNull
    private String password;
}
