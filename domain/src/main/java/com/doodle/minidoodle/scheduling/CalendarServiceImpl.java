package com.doodle.minidoodle.scheduling;

import com.doodle.minidoodle.scheduling.annotations.DomainService;
import com.doodle.minidoodle.scheduling.api.CalendarService;
import com.doodle.minidoodle.scheduling.spi.MeetingRepository;
import com.doodle.minidoodle.scheduling.spi.SlotRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@DomainService
public class CalendarServiceImpl implements CalendarService {

    private final SlotRepository slotRepository;
    private final MeetingRepository meetingRepository;

    public CalendarServiceImpl(SlotRepository slotRepository, MeetingRepository meetingRepository) {
        this.slotRepository = slotRepository;
        this.meetingRepository = meetingRepository;
    }

    public Slot createSlotInUsersCalendar(
            LocalDateTime startDateTime,
            Duration duration,
            Availability availability,
            UserId userId) {
        if (startDateTime == null || duration == null || availability == null || userId == null) {
            throw new IllegalArgumentException("StartDateTime, Duration, Availability and UserId cannot be null");
        }
        return this.slotRepository.save(new Slot(startDateTime, duration, availability, userId));
    }

    @Override
    public void deleteSlotFromUsersCalendar(SlotId slotId, UserId userId) {
        if (slotId == null || userId == null) {
            throw new IllegalArgumentException("SlotId and UserId cannot be null");
        }

        Slot slot = this.slotRepository.get(slotId);
        validateSlotOwnership(slot, userId);

        this.slotRepository.delete(slotId);
    }

    @Override
    public Slot updateSlotInUsersCalendar(
            SlotId slotId,
            LocalDateTime startDateTime,
            Duration duration,
            Availability availability,
            UserId userId) {

        if (slotId == null || startDateTime == null || duration == null || availability == null || userId == null) {
            throw new IllegalArgumentException("SlotId, StartDateTime, Duration, Availability, and UserId cannot be null");
        }

        Slot originalSlot = this.slotRepository.get(slotId);
        validateSlotOwnership(originalSlot, userId);


        return this.slotRepository.update(
                new Slot(slotId, startDateTime, duration, availability, userId)
        );
    }

    @Override
    public Slot makeSlotAvailable(SlotId slotId, UserId userId) {
        return updateAvailability(slotId, userId, Availability.AVAILABLE);
    }

    @Override
    public Slot makeSlotUnavailable(SlotId slotId, UserId userId) {
        return updateAvailability(slotId, userId, Availability.UNAVAILABLE);
    }

    @Override
    public Meeting transformSlotToMeeting(SlotId slotId, UserId userId) {
        if (slotId == null || userId == null) {
            throw new IllegalArgumentException("SlotId and UserId cannot be null");
        }
        Slot slot = this.slotRepository.get(slotId);
        validateSlotOwnership(slot, userId);

        this.slotRepository.delete(slotId);

        return this.meetingRepository.save(Meeting.fromSlot(slot));
    }

    @Override
    public Meeting updateMeetingDetails(MeetingId meetingId, String title, String description, List<UserId> participants, UserId userId) {
        if (meetingId == null || userId == null) {
            throw new IllegalArgumentException("MeetingId and UserId cannot be null");
        }

        Meeting meeting = this.meetingRepository.get(meetingId);
        if (meeting == null || !meeting.userId().equals(userId)) {
            throw new IllegalArgumentException("Meeting not found or does not belong to the user");
        }

        return this.meetingRepository.update(meeting.withTitle(title).withDescription(description).withParticipants(participants));
    }

    @Override
    public Calendar getCalendarForUser(UserId userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        List<Slot> slots = this.slotRepository.getUserSlots(userId);
        List<Meeting> meetings = this.meetingRepository.getUserMeetings(userId);
        return new Calendar(slots, meetings);
    }

    private void validateSlotOwnership(Slot slot, UserId userId) {
        if (slot == null || !slot.userId().equals(userId)) {
            throw new IllegalArgumentException("Slot not found or does not belong to the user");
        }
    }

    private Slot updateAvailability(SlotId slotId, UserId userId, Availability availability) {
        if (slotId == null || userId == null) {
            throw new IllegalArgumentException("SlotId and UserId cannot be null");
        }

        Slot slot = this.slotRepository.get(slotId);
        validateSlotOwnership(slot, userId);

        return this.slotRepository.update(slot.withAvailability(availability));
    }

}
