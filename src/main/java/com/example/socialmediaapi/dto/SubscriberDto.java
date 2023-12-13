package com.example.socialmediaapi.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class SubscriberDto {
    private UserDto user;
    private UserDto subscriber;
    private Boolean active;
}
