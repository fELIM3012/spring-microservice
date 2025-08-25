package spring.microservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import spring.microservice.dto.request.CustomUserDetails;
import spring.microservice.repositories.UserRepository;

import java.util.ArrayList;

@Service
public class UserDetailService implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() -> userRepository.findByUsernameOrEmail(username))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .map(this::mapUserToUserDetails)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + username)));
    }

    private UserDetails mapUserToUserDetails(spring.microservice.entities.User user) {

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

}