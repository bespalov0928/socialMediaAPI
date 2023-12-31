package com.example.socialmediaapi.dto;

import com.example.socialmediaapi.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class MessageDto {
    @NotBlank(message = "Message must be not empty")
    private String mess;

    @NotBlank(message = "User must be not empty")
    private UserDto user;
}
