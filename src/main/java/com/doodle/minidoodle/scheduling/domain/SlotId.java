package com.doodle.minidoodle.scheduling.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record SlotId(UUID id) {
    public SlotId {
        Assert.notNull(id, "Slot id must not be null");
    }

    public SlotId() {
        this(UUID.randomUUID());
    }
}
