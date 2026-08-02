package com.climbmetrics.backend.service;

import com.climbmetrics.backend.dto.LoginRequest;
import com.climbmetrics.backend.dto.LoginResponse;
import com.climbmetrics.backend.dto.RegisterRequest;
import com.climbmetrics.backend.entity.User;
import com.climbmetrics.backend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }


    public LoginResponse login(LoginRequest request) {


        // Find user by email
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException("Invalid password");

        }


        // Generate JWT
        String token =
                jwtService.generateToken(
                        user.getEmail()
                );


        return new LoginResponse(token);

    }

    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setEmail(request.getEmail());

        // Never store plain-text passwords
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

}