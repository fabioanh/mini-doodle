package com.doodle.controllers;

import com.doodle.minidoodle.scheduling.Availability;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;

public class UpdateSlotRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime startTime;
    public Duration duration; // Duration in ISO-8601 format (e.g., PT1H for 1 hour)
    public Availability availability;
}
