package com.doodle.minidoodle.scheduling;

public class User {
    private UserId userId;
    private Calendar calendar;

    public User() {
        this.userId = new UserId();
        this.calendar = new Calendar();
    }

    public UserId getUserId() {
        return userId;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
