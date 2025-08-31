package spring.microservice.dto.response;

import lombok.Data;

@Data
public class GetUserResponse {

    private String username;
    private String email;

}
