package com.finguard.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finguard.auth.dto.ApiResponse;
import com.finguard.auth.dto.LoginRequest;
import com.finguard.auth.dto.RegisterRequest;
import com.finguard.auth.dto.UserRegisteredEvent;
import com.finguard.auth.dto.UserValidationResponse;
import com.finguard.auth.entity.Role;
import com.finguard.auth.entity.User;
import com.finguard.auth.exception.InvalidCredentialsException;
import com.finguard.auth.kafka.UserEventProducer;
import com.finguard.auth.repository.UserRepository;
import com.finguard.auth.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserEventProducer userEventProducer;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            UserEventProducer userEventProducer) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userEventProducer = userEventProducer;
    }

    public ApiResponse<Void> register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email Already Exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user = userRepository.save(user);

        UserRegisteredEvent event = new UserRegisteredEvent();

        event.setUserId(user.getId());
        event.setEmail(user.getEmail());
        event.setRole(user.getRole().name());

        System.out.println(
                "SENDING EVENT -> "
                        + event.getEmail());

        userEventProducer.publishUserRegisteredEvent(event);
        System.out.println("EVENT PUBLISHED SUCCESSFULLY");
        return new ApiResponse<>(
                true,
                "User Registered Successfully",
                null);
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid Credentials"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException("Invalid Credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public UserValidationResponse validateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return new UserValidationResponse(
                    null,
                    null,
                    false);
        }

        return new UserValidationResponse(
                user.getId(),
                user.getEmail(),
                true);
    }
}