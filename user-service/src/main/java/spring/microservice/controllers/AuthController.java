package spring.microservice.controllers;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import spring.microservice.dto.request.CreateUserRequest;
import spring.microservice.dto.request.LoginRequest;
import spring.microservice.dto.response.BaseResponse;
import spring.microservice.dto.response.LoginResponse;
import spring.microservice.entities.User;
import spring.microservice.services.AuthService;
import spring.microservice.services.UserService;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())
                .map(token -> ResponseEntity.ok(
                        LoginResponse.builder()
                                .message("Successfully login")
                                .token(token)
                                .build()
                ))
                .switchIfEmpty(Mono.error(
                        new AuthenticationException("Failed to find user with this id and password")
                ));
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<BaseResponse<User>>> register(
            @RequestBody @Valid CreateUserRequest createUserRequest) throws AuthenticationException {

        if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            throw new AuthenticationException("Password and confirm password must match");
        }

        return Mono.fromSupplier(() -> {
            BaseResponse<User> response = userService.createUser(createUserRequest);
            return ResponseEntity.ok(response);
        });
    }


}
