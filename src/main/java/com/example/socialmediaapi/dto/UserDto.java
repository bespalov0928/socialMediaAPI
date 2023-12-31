package com.example.socialmediaapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "Email must be not empty")
    private String email;
    private String password;
}