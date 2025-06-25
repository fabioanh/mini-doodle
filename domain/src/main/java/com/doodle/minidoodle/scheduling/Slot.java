package com.doodle.minidoodle.scheduling;

import java.time.Duration;
import java.time.LocalDateTime;

public record Slot(
        SlotId slotId,
        LocalDateTime startTime,
        Duration duration,
        Availability availability,
        UserId userId
) {

    public Slot{
        if (startTime == null || duration == null || availability == null || userId == null) {
            throw new IllegalArgumentException("StartDate, Duration, Availability and UserId cannot be null");
        }
    }

    public Slot(LocalDateTime startDate, Duration duration, Availability availability, UserId userId) {
        this(new SlotId(), startDate, duration, availability, userId);
    }

    public Slot withAvailability(Availability availability){
        return new Slot(slotId, startTime, duration, availability, userId);
    }

}
