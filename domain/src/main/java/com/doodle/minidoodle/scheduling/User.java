package com.doodle.minidoodle.scheduling;

public record User(
        UserId userId,
        Calendar calendar
) {

    public User {
        if (userId == null || calendar == null) {
            throw new IllegalArgumentException("UserId and Calendar must not be null");
        }
    }

    public User() {
        this(new UserId(), new Calendar());
    }

    public User(UserId userId) {
        this(userId, new Calendar());
    }

}
