package com.doodle.minidoodle.scheduling;

import com.doodle.minidoodle.scheduling.api.CalendarService;
import com.doodle.minidoodle.scheduling.spi.MeetingRepository;
import com.doodle.minidoodle.scheduling.spi.SlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @Mock
    private SlotRepository slotRepository;
    @Mock
    private MeetingRepository meetingRepository;


    @Test
    void createSlotInUsersCalendar_basicDuration_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
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
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Slot retrievedSlot = new Slot(startDate, duration, Availability.AVAILABLE, user.getUserId());
        SlotId slotId = retrievedSlot.getSlotId();
        Mockito.when(slotRepository.get(slotId)).thenReturn(retrievedSlot);
        // when
        calendarService.deleteSlotFromUsersCalendar(slotId, user.getUserId());
        // then
        Mockito.verify(slotRepository).get(Mockito.eq(slotId));
        Mockito.verify(slotRepository).delete(Mockito.eq(slotId));
    }

    @Test
    void updateSlotInUsersCalendar_basicUpdate_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
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

    @Test
    void makeSlotAvailable_basicScenario_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Slot slot = new Slot(startDate, duration, Availability.AVAILABLE, user.getUserId());
        SlotId slotId = slot.getSlotId();
        Mockito.when(slotRepository.get(slotId)).thenReturn(slot);
        Mockito.when(slotRepository.update(Mockito.any())).thenReturn(slot);
        // when
        Slot availableSlot = calendarService.makeSlotAvailable(slotId, user.getUserId());
        // then
        Assertions.assertEquals(Availability.AVAILABLE, availableSlot.getAvailability());
        Mockito.verify(slotRepository).update(Mockito.any(Slot.class));
    }

    @Test
    void makeSlotUnavailable_basicScenario_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Slot slot = new Slot(startDate, duration, Availability.AVAILABLE, user.getUserId());
        SlotId slotId = slot.getSlotId();
        Mockito.when(slotRepository.get(slotId)).thenReturn(slot);
        Mockito.when(slotRepository.update(Mockito.any())).thenReturn(slot);
        // when
        Slot availableSlot = calendarService.makeSlotUnavailable(slotId, user.getUserId());
        // then
        Assertions.assertEquals(Availability.UNAVAILABLE, availableSlot.getAvailability());
        Mockito.verify(slotRepository).update(Mockito.any(Slot.class));
    }

    @Test
    void transformSlotToMeeting_basicScenario_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Slot slot = new Slot(startDate, duration, Availability.AVAILABLE, user.getUserId());
        SlotId slotId = slot.getSlotId();

        Meeting transformedMeeting = new Meeting(slot);
        Mockito.when(slotRepository.get(slotId)).thenReturn(slot);
        Mockito.when(meetingRepository.save(Mockito.any(Meeting.class))).thenReturn(transformedMeeting);
        // when
        Meeting meeting = calendarService.transformSlotToMeeting(slotId, user.getUserId());
        // then
        Assertions.assertNotNull(meeting);
        Assertions.assertEquals(slot.getStartTime(), meeting.getStartTime());
        Assertions.assertEquals(slot.getDuration(), meeting.getDuration());
        Mockito.verify(slotRepository).get(Mockito.eq(slotId));
        Mockito.verify(slotRepository).delete(Mockito.eq(slotId));
    }


    @Test
    void updateMeetingDetails_basicUpdate_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();

        Meeting originalMeeting = new Meeting(
                startDate,
                duration,
                null,
                null,
                user.getUserId(),
                List.of(user.getUserId())
        );

        MeetingId meetingId = originalMeeting.getMeetingId();

        Meeting updatedMeeting = new Meeting(
                meetingId,
                startDate,
                duration,
                "Team Sync",
                "Weekly team sync meeting",
                user.getUserId(),
                List.of(user.getUserId(), new UserId())
        );

        Mockito.when(meetingRepository.get(Mockito.eq(meetingId))).thenReturn(originalMeeting);
        Mockito.when(meetingRepository.update(Mockito.any(Meeting.class))).thenReturn(updatedMeeting);
        // when
        Meeting resultMeeting = calendarService.updateMeetingDetails(meetingId, "Team Sync", "Weekly team sync meeting", List.of(user.getUserId(), new UserId()), user.getUserId());
        // then
        Assertions.assertNotNull(resultMeeting);
        Mockito.verify(meetingRepository).update(Mockito.any(Meeting.class));
    }

    @Test
    void getCalendarForUser_basicScenario_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
        User user = new User();
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        LocalDateTime meetingStartDate = LocalDateTime.of(2025, 6, 5, 12, 0);
        Duration duration = Duration.ofHours(1);
        Slot slot = new Slot(startDate, duration, Availability.AVAILABLE, user.getUserId());
        Meeting meeting = new Meeting(meetingStartDate, Duration.ofHours(2), "Meeting Title", "Meeting Description", user.getUserId(), List.of(user.getUserId()));

        Calendar calendar = new Calendar(List.of(slot), List.of(meeting));
        Mockito.when(slotRepository.getUserSlots(user.getUserId())).thenReturn(List.of(slot));
        Mockito.when(meetingRepository.getUserMeetings(user.getUserId())).thenReturn(List.of(meeting));
        // when
        Calendar resultCalendar = calendarService.getCalendarForUser(user.getUserId());
        // then
        Assertions.assertNotNull(resultCalendar);
        Assertions.assertEquals(List.of(slot), resultCalendar.getSlots());
        Assertions.assertEquals(List.of(meeting), resultCalendar.getMeetings());
        Mockito.verify(slotRepository).getUserSlots(Mockito.eq(user.getUserId()));
        Mockito.verify(meetingRepository).getUserMeetings(Mockito.eq(user.getUserId()));
    }
}