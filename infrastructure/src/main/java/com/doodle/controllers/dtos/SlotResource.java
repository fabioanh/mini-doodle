package com.doodle.controllers.dtos;

import com.doodle.minidoodle.scheduling.Slot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record SlotResource(
        UUID id,
        LocalDateTime start,
        Duration duration,
        String availability
) {
    public SlotResource(Slot slot) {
        this(slot.slotId().id(), slot.startTime(), slot.duration(), slot.availability().name());
    }
}
