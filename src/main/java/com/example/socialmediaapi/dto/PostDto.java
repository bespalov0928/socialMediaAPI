package com.example.socialmediaapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
public class PostDto {
    private int id;
    @NotBlank(message = "Message must be not empty")
    private String mess;

    @NotBlank(message = "User must be not empty")
    private int user_id;
}
