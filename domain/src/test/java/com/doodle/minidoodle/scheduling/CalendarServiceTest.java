package com.doodle.minidoodle.scheduling;

import com.doodle.minidoodle.scheduling.api.CalendarService;
import com.doodle.minidoodle.scheduling.spi.SlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @Mock
    private SlotRepository slotRepository;


    @Test
    void createSlotInUsersCalendar_basicDuration_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Mockito.when(slotRepository.save(Mockito.any())).thenReturn(new Slot(startDate, duration, Availability.AVAILABLE, user.getUserId()));
        // when
        Slot createdSlot = calendarService.createSlotInUsersCalendar(startDate, duration, Availability.AVAILABLE, user.getUserId());
        // then
        Assertions.assertNotNull(createdSlot);
        Mockito.verify(slotRepository).save(Mockito.any(Slot.class));
    }

    @Test
    void deleteSlotInUsersCalendar_regularFlow_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Slot retrievedSlot = new Slot(startDate, duration, Availability.AVAILABLE, user.getUserId());
        Mockito.when(slotRepository.get(retrievedSlot.getSlotId())).thenReturn(retrievedSlot);
        Mockito.when(slotRepository.delete(retrievedSlot.getSlotId())).thenReturn(retrievedSlot);
        // when
        Slot deletedSlot = calendarService.deleteSlotFromUsersCalendar(retrievedSlot.getSlotId(), user.getUserId());
        // then
        Assertions.assertNotNull(deletedSlot);
        Mockito.verify(slotRepository).get(Mockito.eq(retrievedSlot.getSlotId()));
        Mockito.verify(slotRepository).delete(Mockito.eq(retrievedSlot.getSlotId()));
    }
}