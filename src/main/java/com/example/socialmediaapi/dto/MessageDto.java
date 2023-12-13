package com.example.socialmediaapi.dto;

import com.example.socialmediaapi.model.User;
import lombok.Data;
import lombok.Getter;

@Data
public class MessageDto {
    private String mess;
    private UserDto user;
}
