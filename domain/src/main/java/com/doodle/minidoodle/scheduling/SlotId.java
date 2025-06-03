package com.doodle.minidoodle.scheduling;


import java.util.UUID;

public record SlotId(UUID id) {
    public SlotId {
        if (id == null) {
            throw new IllegalArgumentException("Slot id must not be null");
        }
    }

    public SlotId() {
        this(UUID.randomUUID());
    }
}
