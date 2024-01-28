package com.example.socialmediaapi.jwt.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "First name must be not empty")
    private String firstname;
    @NotBlank(message = "Email must be not empty")
    private String email;
    @NotBlank(message = "Password must be not empty")
    String password;
}
