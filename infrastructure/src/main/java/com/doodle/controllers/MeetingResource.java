package com.doodle.controllers;

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
                meeting.getMeetingId().id(),
                meeting.getTitle(),
                meeting.getDescription(),
                meeting.getStartTime(),
                meeting.getDuration(),
                meeting.getUserId().id(),
                meeting.getParticipants().stream().map(UserId::getId).toList()
        );
    }

}
