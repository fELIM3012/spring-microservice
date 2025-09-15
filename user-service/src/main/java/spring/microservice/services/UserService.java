package spring.microservice.services;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.microservice.dto.request.CreateUserRequest;
import spring.microservice.dto.response.BaseResponse;
import spring.microservice.dto.response.GetUserResponse;
import spring.microservice.entities.User;
import spring.microservice.mapper.UserMapper;
import spring.microservice.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public BaseResponse<User> createUser(CreateUserRequest createUserRequest){

        String username = createUserRequest.getUsername();
        String email = createUserRequest.getEmail();
        boolean existingUser = userRepository.existsByUsernameOrEmail(username, email);

        if(existingUser){
            throw new ValidationException("User already exists");
        }

        User payload = UserMapper.INSTANCE.map(createUserRequest);

        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        payload.setPassword(encodedPassword);

        User savedUser = userRepository.save(payload);

        return BaseResponse.<User>builder()
                .message("Successfully created user")
                .payload(savedUser)
                .build();
    }

    public BaseResponse<GetUserResponse> getProfileDetails(UUID userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new ValidationException("User is not exists");
        }

        GetUserResponse getUserResponse = UserMapper.INSTANCE.map(user.get());
        return BaseResponse
                .<GetUserResponse>builder()
                .message("successfully find user")
                .payload(getUserResponse)
                .build();
    }


}
