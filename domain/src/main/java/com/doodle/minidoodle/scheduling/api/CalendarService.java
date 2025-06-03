package com.doodle.minidoodle.scheduling.api;

import com.doodle.minidoodle.scheduling.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface CalendarService {
    Slot createSlotInUsersCalendar(
            LocalDateTime startDateTime,
            Duration duration,
            Availability availability,
            UserId userId
    );

    void deleteSlotFromUsersCalendar(SlotId slotId, UserId userId);

    Slot updateSlotInUsersCalendar(
            SlotId slotId,
            LocalDateTime startDateTime,
            Duration duration,
            Availability availability,
            UserId userId
    );

    Slot makeSlotAvailable(SlotId slotId, UserId userId);

    Slot makeSlotUnavailable(SlotId slotId, UserId userId);

    Meeting transformSlotToMeeting(SlotId slotId, UserId userId);

    Meeting updateMeetingDetails(
            MeetingId meetingId,
            String title,
            String description,
            List<UserId> participants,
            UserId userId
    );

    Calendar getCalendarForUser(UserId userId);
}
