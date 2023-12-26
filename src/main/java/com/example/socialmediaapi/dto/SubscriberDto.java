package com.example.socialmediaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class SubscriberDto {

    @NotBlank(message = "User must be not empty")
    private String emailUser;

    @NotBlank(message = "Subscriber must be not empty")
    private String emailSubscriber;

    @NotNull(message = "Active must be not empty")
    private Boolean active;
}
