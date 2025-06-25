package com.doodle.controllers.dtos;

import com.doodle.minidoodle.scheduling.User;

import java.util.UUID;

public record UserResource (UUID userId) {
    public UserResource (User user){
        this(user.userId().getId());
    }
}
