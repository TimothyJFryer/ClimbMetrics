package com.climbmetrics.backend.controller;


import com.climbmetrics.backend.dto.LoginRequest;
import com.climbmetrics.backend.dto.LoginResponse;
import com.climbmetrics.backend.dto.RegisterRequest;
import com.climbmetrics.backend.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;


    public AuthController(AuthService authService) {

        this.authService = authService;

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.noContent().build();
    }



    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {

        LoginResponse loginResponse =  authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("accessToken", loginResponse.getToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

}