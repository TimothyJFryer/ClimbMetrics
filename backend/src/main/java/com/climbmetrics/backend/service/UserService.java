package com.climbmetrics.backend.service;

import com.climbmetrics.backend.dto.LoginRequest;
import com.climbmetrics.backend.dto.RegisterRequest;
import com.climbmetrics.backend.dto.UserProfileResponse;
import com.climbmetrics.backend.entity.User;
import com.climbmetrics.backend.exception.NoSuchUserException;
import com.climbmetrics.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileResponse getProfile(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(NoSuchUserException::new);

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getBoulderGrade(),
                user.getDescription(),
                user.getSportGrade(),
                user.getTotalClimbs(),
                user.getTotalSessions()
        );
    }
}
