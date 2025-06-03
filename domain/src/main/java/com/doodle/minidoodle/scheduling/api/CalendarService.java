package com.doodle.minidoodle.scheduling.api;

import com.doodle.minidoodle.scheduling.Availability;
import com.doodle.minidoodle.scheduling.Slot;
import com.doodle.minidoodle.scheduling.UserId;

import java.time.Duration;
import java.time.LocalDateTime;

public interface CalendarService {
    Slot createSlotInUsersCalendar(
            LocalDateTime startDateTime,
            Duration duration,
            Availability availability,
            UserId userId
    );
}
