package com.doodle.minidoodle.scheduling;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Meeting {
    private MeetingId meetingId;
    private LocalDateTime startTime;
    private Duration duration;
    private String title;
    private String description;
    private UserId userId;
    private List<UserId> participants;

    public Meeting(LocalDateTime startTime, Duration duration, String title, String description, UserId userId, List<UserId> participants) {
        if (startTime == null || duration == null || userId == null || participants == null) {
            throw new IllegalArgumentException("StartTime, Duration, UserId, and Participants cannot be null");
        }
        this.meetingId = new MeetingId();
        this.startTime = startTime;
        this.duration = duration;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.participants = participants;
    }

    public Meeting(MeetingId meetingId, LocalDateTime startTime, Duration duration, String title, String description, UserId userId, List<UserId> participants) {
        if (meetingId == null || startTime == null || duration == null || userId == null || participants == null) {
            throw new IllegalArgumentException("MeetingId, StartTime, Duration, UserId, and Participants cannot be null");
        }
        this.meetingId = meetingId;
        this.startTime = startTime;
        this.duration = duration;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.participants = participants;
    }

    public Meeting(Slot slot) {
        if (slot == null) {
            throw new IllegalArgumentException("Slot cannot be null");
        }
        this.meetingId = new MeetingId();
        this.startTime = slot.getStartTime();
        this.duration = slot.getDuration();
        this.title = null;
        this.description = null;
        this.userId = slot.getUserId();
        this.participants = null;
    }

    public MeetingId getMeetingId() {
        return meetingId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UserId getUserId() {
        return userId;
    }

    public List<UserId> getParticipants() {
        return participants;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParticipants(List<UserId> participants) {
        if (participants == null) {
            throw new IllegalArgumentException("Participants cannot be null");
        }
        this.participants = participants;
    }
}
