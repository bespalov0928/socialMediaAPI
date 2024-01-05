package com.example.socialmediaapi.jwt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");

    @Getter
    private final String permission;
}
