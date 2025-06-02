package com.doodle.minidoodle.scheduling.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class Slot {
    private SlotId slotId;
    private LocalDateTime startTime;
    private Duration duration;
    private Availability availability;
}
