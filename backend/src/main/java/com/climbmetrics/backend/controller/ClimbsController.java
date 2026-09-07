package com.climbmetrics.backend.controller;


import com.climbmetrics.backend.dto.ClimbsResponse;
import com.climbmetrics.backend.dto.LogClimbRequest;
import com.climbmetrics.backend.dto.StatsResponse;
import com.climbmetrics.backend.service.ClimbService;
import com.climbmetrics.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/climbs")
public class ClimbsController {

    private final ClimbService climbService;
    private final UserService userService;


    public ClimbsController(ClimbService climbService, UserService userService) {
        this.climbService = climbService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ClimbsResponse>> getClimbs(Authentication authentication) {
        String email = authentication.getName();

        Long userId = userService.getUserIdByEmail(email);

        return ResponseEntity.ok(
                climbService.getClimbs(userId)
        );
    }

    @PostMapping("/log")
    public ResponseEntity<String> logClimb(Authentication authentication, @RequestBody @Valid LogClimbRequest logRequest) {

        String email = authentication.getName();

        Long userId = userService.getUserIdByEmail(email);


        climbService.logClimb(userId, logRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Climb logged successfully");
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(Authentication authentication) {
        String email = authentication.getName();
        Long userId = userService.getUserIdByEmail(email);


        return ResponseEntity.ok(
                climbService.getStatistics(userId)
        );
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editClimb(Authentication authentication, @RequestBody @Valid LogClimbRequest logRequest) {
        String email = authentication.getName();
        Long userId = userService.getUserIdByEmail(email);

        climbService.editClimb(userId, logRequest);

        return ResponseEntity.ok("Climb updated successfully");

    }

    @DeleteMapping("/{climbId}")
    public ResponseEntity<String> deleteClimb(
            Authentication authentication,
            @PathVariable Long climbId) {

        String email = authentication.getName();
        Long userId = userService.getUserIdByEmail(email);

        climbService.deleteClimb(userId, climbId);

        return ResponseEntity.ok("Climb deleted successfully");
    }
}
