package com.doodle.minidoodle.scheduling;

import com.doodle.minidoodle.scheduling.annotations.DomainService;
import com.doodle.minidoodle.scheduling.api.CalendarService;
import com.doodle.minidoodle.scheduling.spi.SlotRepository;

import java.time.Duration;
import java.time.LocalDateTime;

@DomainService
public class CalendarServiceImpl implements CalendarService {

    private final SlotRepository slotRepository;

    public CalendarServiceImpl(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
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
    public Slot deleteSlotFromUsersCalendar(SlotId slotId, UserId userId) {
        if (slotId == null || userId == null) {
            throw new IllegalArgumentException("SlotId and UserId cannot be null");
        }

        Slot slot = this.slotRepository.get(slotId);
        validateSlotOwnership(slot, userId);

        return this.slotRepository.delete(slotId);
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

    private void validateSlotOwnership(Slot slot, UserId userId) {
        if (slot == null || !slot.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Slot not found or does not belong to the user");
        }
    }

    private Slot updateAvailability(SlotId slotId, UserId userId, Availability availability) {
        if (slotId == null || userId == null) {
            throw new IllegalArgumentException("SlotId and UserId cannot be null");
        }

        Slot slot = this.slotRepository.get(slotId);
        validateSlotOwnership(slot, userId);

        slot.setAvailability(availability);

        return this.slotRepository.update(slot);
    }

}
