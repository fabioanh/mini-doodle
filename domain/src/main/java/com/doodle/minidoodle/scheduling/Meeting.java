package com.doodle.minidoodle.scheduling;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record Meeting(
        MeetingId meetingId,
        LocalDateTime startTime,
        Duration duration,
        String title,
        String description,
        UserId userId,
        List<UserId> participants
) {

    public Meeting {
        if (meetingId == null || startTime == null || duration == null || userId == null || participants == null) {
            throw new IllegalArgumentException("MeetingId, StartTime, Duration, UserId, and Participants cannot be null");
        }
    }

    public Meeting(LocalDateTime startTime, Duration duration, String title, String description, UserId userId, List<UserId> participants) {
        this(new MeetingId(), startTime, duration, title, description, userId, participants);
    }

    public static Meeting fromSlot(Slot slot) {
        if (slot == null) {
            throw new IllegalArgumentException("Slot cannot be null");
        }
        return new Meeting(new MeetingId(), slot.startTime(), slot.duration(), "", "", slot.userId(), Collections.emptyList());
    }

    public Meeting withTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        return new Meeting(meetingId, startTime, duration, title, description, userId, participants);
    }

    public Meeting withDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        return new Meeting(meetingId, startTime, duration, title, description, userId, participants);
    }

    public Meeting withParticipants(List<UserId> participants) {
        if (participants == null) {
            throw new IllegalArgumentException("Participants cannot be null");
        }
        return new Meeting(meetingId, startTime, duration, title, description, userId, participants);
    }
}
