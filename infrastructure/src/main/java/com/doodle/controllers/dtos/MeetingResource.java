package com.doodle.controllers.dtos;

import com.doodle.minidoodle.scheduling.Meeting;
import com.doodle.minidoodle.scheduling.UserId;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MeetingResource(
        UUID id,
        String title,
        String description,
        LocalDateTime start,
        Duration duration,
        UUID organizer,
        List<UUID> participants) {

    public MeetingResource(Meeting meeting) {

        this(
                meeting.meetingId().id(),
                meeting.title(),
                meeting.description(),
                meeting.startTime(),
                meeting.duration(),
                meeting.userId().id(),
                meeting.participants().stream().map(UserId::getId).toList()
        );
    }

}
