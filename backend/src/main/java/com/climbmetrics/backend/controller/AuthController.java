package com.climbmetrics.backend.controller;

import com.climbmetrics.backend.entity.User;
import com.climbmetrics.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }



    @PostMapping("/register")
    public String register(
            @RequestBody User user
    ){

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );


        userRepository.save(user);


        return "User created";

    }

    @PostMapping("/login")
    public String login(
            @RequestBody User loginRequest
    ){

        User user = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow();


        if(!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        )){
            throw new RuntimeException("Invalid password");
        }


        return "Login successful";

    }

}