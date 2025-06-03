package com.doodle.minidoodle.scheduling;

import java.time.Duration;
import java.time.LocalDateTime;

public class Slot {
    private SlotId slotId;
    private LocalDateTime startTime;
    private Duration duration;
    private Availability availability;
    private UserId userId;

    public Slot(LocalDateTime startDate, Duration duration, Availability availability, UserId userId) {
        this.slotId = new SlotId();
        this.startTime = startDate;
        this.duration = duration;
        this.availability = availability;
        this.userId = userId;
    }

    public Slot(SlotId slotId, LocalDateTime startDate, Duration duration, Availability availability, UserId userId) {
        this.slotId = slotId;
        this.startTime = startDate;
        this.duration = duration;
        this.availability = availability;
        this.userId = userId;
    }

    public SlotId getSlotId() {
        return slotId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public Availability getAvailability() {
        return availability;
    }

    public UserId getUserId() {
        return userId;
    }
}
