package com.climbmetrics.backend.controller;


import com.climbmetrics.backend.dto.UserProfileResponse;
import com.climbmetrics.backend.entity.User;
import com.climbmetrics.backend.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;


    public ProfileController(UserService userService) {

        this.userService = userService;

    }

    @GetMapping
    public UserProfileResponse viewProfile(
            @RequestParam String email) {

        return userService.getProfile(email);
    }

}
