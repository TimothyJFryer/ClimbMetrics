package com.climbmetrics.backend.controller;

import com.climbmetrics.backend.service.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    private final JwtService jwtService;


    public TestController(JwtService jwtService){
        this.jwtService = jwtService;
    }


    @GetMapping("/token")
    public String token(){

        return jwtService.generateToken(
                "test@email.com"
        );
    }

}
