package com.doodle.controllers;

import com.doodle.minidoodle.scheduling.User;

import java.util.UUID;

public record UserResource (UUID userId) {
    public UserResource (User user){
        this(user.getUserId().getId());
    }
}
