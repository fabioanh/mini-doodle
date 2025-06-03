package com.doodle.minidoodle.scheduling;

public class User {
    private UserId userId;
    private Calendar calendar;

    public User() {
        this.userId = new UserId();
        this.calendar = new Calendar();
    }

    public User(UserId userId, Calendar calendar) {
        if (userId == null || calendar == null) {
            throw new IllegalArgumentException("UserId and Calendar must not be null");
        }
        this.userId = userId;
        this.calendar = calendar;
    }

    public UserId getUserId() {
        return userId;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
