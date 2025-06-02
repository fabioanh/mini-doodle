package com.doodle.minidoodle.scheduling.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record MeetingId(UUID id) {
    public MeetingId {
        Assert.notNull(id, "Meeting id must not be null");
    }

    public MeetingId() {
        this(UUID.randomUUID());
    }
}
