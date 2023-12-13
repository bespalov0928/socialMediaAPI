package com.example.socialmediaapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class InviteDto {
    private UserDto user;
    private UserDto friend;
    private Boolean appruvFriend;
}
