package com.climbmetrics.backend.controller;


import com.climbmetrics.backend.dto.LoginRequest;
import com.climbmetrics.backend.dto.LoginResponse;
import com.climbmetrics.backend.dto.RegisterRequest;
import com.climbmetrics.backend.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;


    public AuthController(AuthService authService) {

        this.authService = authService;

    }



    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        System.out.println("LOGIN ENDPOINT HIT");
        return authService.login(request);

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

}