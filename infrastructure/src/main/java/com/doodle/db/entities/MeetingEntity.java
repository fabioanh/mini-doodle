package com.doodle.db.entities;

import com.doodle.minidoodle.scheduling.Meeting;
import com.doodle.minidoodle.scheduling.MeetingId;
import com.doodle.minidoodle.scheduling.Slot;
import com.doodle.minidoodle.scheduling.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class MeetingEntity {
    @Id
    private UUID id;
    private LocalDateTime startTime;
    private Duration duration;
    private String title;
    private String description;
    private UUID userId;
    private List<UUID> participants;

    public MeetingEntity(Meeting meeting) {
        this.id = meeting.getUserId().id();
        this.startTime = meeting.getStartTime();
        this.duration = meeting.getDuration();
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.userId = meeting.getUserId().id();
        this.participants = meeting.getParticipants().stream()
                .map(UserId::getId)
                .toList();
    }

    public MeetingEntity() {
        // Default constructor for JPA
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<UUID> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UUID> participants) {
        this.participants = participants;
    }

    public Meeting toDomain() {
        return new Meeting(
                new MeetingId(id),
                startTime,
                duration,
                title,
                description,
                new UserId(userId),
                participants.stream().map(UserId::new).toList()
        );
    }
}
