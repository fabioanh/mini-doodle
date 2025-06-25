package com.doodle.controllers.dtos;

import com.doodle.minidoodle.scheduling.Availability;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;

public record UpdateSlotRequest(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime startTime,
        Duration duration, // Duration in ISO-8601 format (e.g., PT1H for 1 hour)
        Availability availability
) {
}
