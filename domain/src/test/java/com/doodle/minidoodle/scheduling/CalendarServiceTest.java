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
        Mockito.when(slotRepository.save(Mockito.any())).thenReturn(new Slot(startDate, duration, Availability.AVAILABLE, user.userId()));
        // when
        Slot createdSlot = calendarService.createSlotInUsersCalendar(startDate, duration, Availability.AVAILABLE, user.userId());
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
        Slot retrievedSlot = new Slot(startDate, duration, Availability.AVAILABLE, user.userId());
        SlotId slotId = retrievedSlot.slotId();
        Mockito.when(slotRepository.get(slotId)).thenReturn(retrievedSlot);
        // when
        calendarService.deleteSlotFromUsersCalendar(slotId, user.userId());
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
        Slot originalSlot = new Slot(slotId, oldStartDate, oldDuration, Availability.AVAILABLE, user.userId());
        Slot updatedSlot = new Slot(slotId, newStartDate, newDuration, Availability.UNAVAILABLE, user.userId());
        Mockito.when(slotRepository.get(slotId)).thenReturn(originalSlot);
        Mockito.when(slotRepository.update(Mockito.any(Slot.class))).thenReturn(updatedSlot);
        // when
        Slot responseSlot = calendarService.updateSlotInUsersCalendar(slotId, newStartDate, newDuration, Availability.UNAVAILABLE, user.userId());
        // then
        Assertions.assertNotNull(responseSlot);
        Assertions.assertEquals(newStartDate, responseSlot.startTime());
        Assertions.assertEquals(newDuration, responseSlot.duration());
        Assertions.assertEquals(Availability.UNAVAILABLE, responseSlot.availability());
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
        Slot slot = new Slot(startDate, duration, Availability.AVAILABLE, user.userId());
        Slot availableSlot = new Slot(slot.slotId(), startDate, duration, Availability.AVAILABLE, user.userId());
        SlotId slotId = slot.slotId();
        Mockito.when(slotRepository.get(slotId)).thenReturn(slot);
        Mockito.when(slotRepository.update(Mockito.any())).thenReturn(availableSlot);
        // when
        Slot response = calendarService.makeSlotAvailable(slotId, user.userId());
        // then
        Assertions.assertEquals(Availability.AVAILABLE, response.availability());
        Mockito.verify(slotRepository).update(Mockito.any(Slot.class));
    }

    @Test
    void makeSlotUnavailable_basicScenario_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Slot slot = new Slot(startDate, duration, Availability.AVAILABLE, user.userId());
        SlotId slotId = slot.slotId();
        Slot unavailableSlot = new Slot(slot.slotId(), startDate, duration, Availability.UNAVAILABLE, user.userId());
        Mockito.when(slotRepository.get(slotId)).thenReturn(slot);
        Mockito.when(slotRepository.update(Mockito.any())).thenReturn(unavailableSlot);
        // when
        Slot response = calendarService.makeSlotUnavailable(slotId, user.userId());
        // then
        Assertions.assertEquals(Availability.UNAVAILABLE, response.availability());
        Mockito.verify(slotRepository).update(Mockito.any(Slot.class));
    }

    @Test
    void transformSlotToMeeting_basicScenario_successful() {
        // given
        CalendarService calendarService = new CalendarServiceImpl(slotRepository, meetingRepository);
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 5, 10, 0);
        Duration duration = Duration.ofHours(1);
        User user = new User();
        Slot slot = new Slot(startDate, duration, Availability.AVAILABLE, user.userId());
        SlotId slotId = slot.slotId();

        Meeting transformedMeeting = Meeting.fromSlot(slot);
        Mockito.when(slotRepository.get(slotId)).thenReturn(slot);
        Mockito.when(meetingRepository.save(Mockito.any(Meeting.class))).thenReturn(transformedMeeting);
        // when
        Meeting meeting = calendarService.transformSlotToMeeting(slotId, user.userId());
        // then
        Assertions.assertNotNull(meeting);
        Assertions.assertEquals(slot.startTime(), meeting.startTime());
        Assertions.assertEquals(slot.duration(), meeting.duration());
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
                user.userId(),
                List.of(user.userId())
        );

        MeetingId meetingId = originalMeeting.meetingId();

        Meeting updatedMeeting = new Meeting(
                meetingId,
                startDate,
                duration,
                "Team Sync",
                "Weekly team sync meeting",
                user.userId(),
                List.of(user.userId(), new UserId())
        );

        Mockito.when(meetingRepository.get(Mockito.eq(meetingId))).thenReturn(originalMeeting);
        Mockito.when(meetingRepository.update(Mockito.any(Meeting.class))).thenReturn(updatedMeeting);
        // when
        Meeting resultMeeting = calendarService.updateMeetingDetails(meetingId, "Team Sync", "Weekly team sync meeting", List.of(user.userId(), new UserId()), user.userId());
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
        Slot slot = new Slot(startDate, duration, Availability.AVAILABLE, user.userId());
        Meeting meeting = new Meeting(meetingStartDate, Duration.ofHours(2), "Meeting Title", "Meeting Description", user.userId(), List.of(user.userId()));

        Calendar calendar = new Calendar(List.of(slot), List.of(meeting));
        Mockito.when(slotRepository.getUserSlots(user.userId())).thenReturn(List.of(slot));
        Mockito.when(meetingRepository.getUserMeetings(user.userId())).thenReturn(List.of(meeting));
        // when
        Calendar resultCalendar = calendarService.getCalendarForUser(user.userId());
        // then
        Assertions.assertNotNull(resultCalendar);
        Assertions.assertEquals(List.of(slot), resultCalendar.slots());
        Assertions.assertEquals(List.of(meeting), resultCalendar.meetings());
        Mockito.verify(slotRepository).getUserSlots(Mockito.eq(user.userId()));
        Mockito.verify(meetingRepository).getUserMeetings(Mockito.eq(user.userId()));
    }
}