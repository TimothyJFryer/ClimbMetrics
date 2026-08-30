package com.climbmetrics.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;

public record EditRequest(


        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        String email,

        String description
) {
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }
}
