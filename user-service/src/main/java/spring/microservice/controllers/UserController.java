package spring.microservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.microservice.dto.response.BaseResponse;
import spring.microservice.dto.response.GetUserResponse;
import spring.microservice.services.UserService;
import spring.microservice.utils.SecurityUtils;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/get-profile")
    public BaseResponse<GetUserResponse> getUser(Authentication authentication){
        UUID userId = SecurityUtils.extractUserId(authentication);

        return userService.getProfileDetails(userId);
    }
}

