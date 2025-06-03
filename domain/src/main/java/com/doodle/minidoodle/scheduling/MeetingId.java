package com.doodle.minidoodle.scheduling;

import java.util.UUID;

public record MeetingId(UUID id) {
    public MeetingId {
        if (id == null) {
            throw new IllegalArgumentException("Meeting id must not be null");
        }
    }

    public MeetingId() {
        this(UUID.randomUUID());
    }
}
