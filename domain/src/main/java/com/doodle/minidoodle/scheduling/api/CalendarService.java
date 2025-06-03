package com.doodle.minidoodle.scheduling.api;

import com.doodle.minidoodle.scheduling.Availability;
import com.doodle.minidoodle.scheduling.Slot;
import com.doodle.minidoodle.scheduling.SlotId;
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

    Slot deleteSlotFromUsersCalendar(SlotId slotId, UserId userId);

    Slot updateSlotInUsersCalendar(
            SlotId slotId,
            LocalDateTime startDateTime,
            Duration duration,
            Availability availability,
            UserId userId
    );

    Slot makeSlotAvailable(SlotId slotId, UserId userId);

    Slot makeSlotUnavailable(SlotId slotId, UserId userId);
}
