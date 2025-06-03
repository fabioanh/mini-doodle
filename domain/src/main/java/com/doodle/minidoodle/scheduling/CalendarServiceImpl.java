package com.doodle.minidoodle.scheduling;

import com.doodle.minidoodle.scheduling.annotations.DomainService;
import com.doodle.minidoodle.scheduling.api.CalendarService;
import com.doodle.minidoodle.scheduling.spi.SlotRepository;

import java.time.Duration;
import java.time.LocalDateTime;

@DomainService
public class CalendarServiceImpl implements CalendarService {

    private final SlotRepository slotRepository;

    public CalendarServiceImpl(SlotRepository slotRepository){
        this.slotRepository = slotRepository;
    }

    public Slot createSlotInUsersCalendar(LocalDateTime startDateTime, Duration duration, Availability availability, UserId userId){
        return this.slotRepository.save(new Slot(startDateTime, duration, availability, userId));
    }

}
