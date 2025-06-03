package com.doodle.minidoodle.scheduling;

import java.util.UUID;

public record UserId(UUID id) {
    public UserId {
        if (id == null) {
            throw new IllegalArgumentException("User id must not be null");
        }
    }

    public UserId() {
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }
}