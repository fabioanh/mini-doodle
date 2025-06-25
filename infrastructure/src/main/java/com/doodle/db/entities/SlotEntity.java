package com.doodle.db.entities;

import com.doodle.minidoodle.scheduling.Availability;
import com.doodle.minidoodle.scheduling.Slot;
import com.doodle.minidoodle.scheduling.SlotId;
import com.doodle.minidoodle.scheduling.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class SlotEntity {
    @Id
    private UUID id;
    private LocalDateTime startTime;
    private Duration duration;
    private String availability;
    private UUID userId;

    public SlotEntity(Slot slot) {
        this.id = slot.slotId().id();
        this.startTime = slot.startTime();
        this.duration = slot.duration();
        this.availability = slot.availability().name();
        this.userId = slot.userId().getId();
    }

    public SlotEntity() {
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

    public Duration duration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String availability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public UUID userId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Slot toDomain() {
        return new Slot(
                new SlotId(id),
                startTime,
                duration,
                Availability.valueOf(availability),
                new UserId(userId)
        );
    }
}
