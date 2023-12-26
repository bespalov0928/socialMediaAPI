package com.example.socialmediaapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InviteDto {
    @NotBlank(message = "User must be not empty")
    private String emailUser;

    @NotBlank(message = "Friend must be not empty")
    private String emailFriend;

    @NotNull(message = "Appruv must be not empty")
    private Boolean appruvFriend;
}
