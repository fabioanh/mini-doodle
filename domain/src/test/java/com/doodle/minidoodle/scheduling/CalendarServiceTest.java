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
    void deleteSlotFromUsersCalendar_regularFlow_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Slot retrievedSlot = new Slot(startDate, duration, Availability.AVAILABLE, user.getUserId());
        SlotId slotId = retrievedSlot.getSlotId();
        Mockito.when(slotRepository.get(slotId)).thenReturn(retrievedSlot);
        Mockito.when(slotRepository.delete(slotId)).thenReturn(retrievedSlot);
        // when
        Slot deletedSlot = calendarService.deleteSlotFromUsersCalendar(slotId, user.getUserId());
        // then
        Assertions.assertNotNull(deletedSlot);
        Mockito.verify(slotRepository).get(Mockito.eq(slotId));
        Mockito.verify(slotRepository).delete(Mockito.eq(slotId));
    }

    @Test
    void updateSlotInUsersCalendar_basicUpdate_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository);
        LocalDateTime oldStartDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration oldDuration = Duration.ofHours(1);
        LocalDateTime newStartDate = LocalDateTime.of(2025, 6, 5, 11, 0);
        Duration newDuration = Duration.ofHours(2);
        User user = new User();
        SlotId slotId = new SlotId();
        Slot originalSlot = new Slot(slotId, oldStartDate, oldDuration, Availability.AVAILABLE, user.getUserId());
        Slot updatedSlot = new Slot(slotId, newStartDate, newDuration, Availability.UNAVAILABLE, user.getUserId());
        Mockito.when(slotRepository.get(slotId)).thenReturn(originalSlot);
        Mockito.when(slotRepository.update(Mockito.any(Slot.class))).thenReturn(updatedSlot);
        // when
        Slot responseSlot = calendarService.updateSlotInUsersCalendar(slotId, newStartDate, newDuration, Availability.UNAVAILABLE, user.getUserId());
        // then
        Assertions.assertNotNull(responseSlot);
        Assertions.assertEquals(newStartDate, responseSlot.getStartTime());
        Assertions.assertEquals(newDuration, responseSlot.getDuration());
        Assertions.assertEquals(Availability.UNAVAILABLE, responseSlot.getAvailability());
        Mockito.verify(slotRepository).get(Mockito.eq(slotId));
        Mockito.verify(slotRepository).update(Mockito.any(Slot.class));
    }
}