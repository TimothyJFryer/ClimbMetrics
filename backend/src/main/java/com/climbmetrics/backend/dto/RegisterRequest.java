package com.climbmetrics.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;

public record RegisterRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        String email,

        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(
                regexp = ".*[A-Z].*",
                message = "Password must contain an uppercase letter"
        )
        @Pattern(
                regexp = ".*[a-z].*",
                message = "Password must contain a lowercase letter"
        )
        @Pattern(
                regexp = ".*\\d.*",
                message = "Password must contain a number"
        )
        @Pattern(
                regexp = ".*[^a-zA-Z0-9].*",
                message = "Password must contain a special character"
        )
        String password
) {
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}