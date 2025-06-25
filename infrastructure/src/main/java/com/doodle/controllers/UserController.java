package com.doodle.controllers;

import com.doodle.controllers.dtos.*;
import com.doodle.minidoodle.scheduling.*;
import com.doodle.minidoodle.scheduling.api.CalendarService;
import com.doodle.minidoodle.scheduling.api.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/")
public class UserController {

    private final UserService userService;
    private final CalendarService calendarService;

    public UserController(UserService userService, CalendarService calendarService) {
        // Constructor logic if needed
        this.userService = userService;
        this.calendarService = calendarService;
    }

    @GetMapping("/{userIdParam}/calendar")
    public ResponseEntity<CalendarResource> getCalendar(@PathVariable UUID userIdParam) {
        UserId userId = new UserId(userIdParam);
        Calendar calendar = calendarService.getCalendarForUser(userId);
        return ResponseEntity.ok(new CalendarResource(calendar));
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser() {
        User user = userService.createUser();
        return ResponseEntity.ok(new UserResource(user));
    }

    @PostMapping("/{userIdParam}/slots")
    public ResponseEntity<SlotResource> createSlot(@PathVariable UUID userIdParam, @RequestBody CreateSlotRequest createSlotRequest) {
        UserId userId = new UserId(userIdParam);
        Slot createdSlot = calendarService.createSlotInUsersCalendar(createSlotRequest.startTime(), createSlotRequest.duration(), createSlotRequest.availability(), userId);
        return ResponseEntity.ok(new SlotResource(createdSlot));
    }

    @PostMapping("/{userIdParam}/slots/{slotId}/toMeeting")
//    @Transactional
    public ResponseEntity<MeetingResource> createMeeting(@PathVariable UUID userIdParam, @PathVariable UUID slotId) {
        UserId userId = new UserId(userIdParam);
        Meeting createdMeeting = calendarService.transformSlotToMeeting(new SlotId(slotId), new UserId(userIdParam));
        return ResponseEntity.ok(new MeetingResource(createdMeeting));
    }

    @PostMapping("/{userIdParam}/slots/{slotId}/makeAvailable")
    public ResponseEntity<SlotResource> makeAvailable(@PathVariable UUID userIdParam, @PathVariable UUID slotId) {
        UserId userId = new UserId(userIdParam);
        Slot changedSlot = calendarService.makeSlotAvailable(new SlotId(slotId), new UserId(userIdParam));
        return ResponseEntity.ok(new SlotResource(changedSlot));
    }

    @PostMapping("/{userIdParam}/slots/{slotId}/makeUnavailable")
    public ResponseEntity<SlotResource> makeUnavailable(@PathVariable UUID userIdParam, @PathVariable UUID slotId) {
        UserId userId = new UserId(userIdParam);
        Slot changedSlot = calendarService.makeSlotUnavailable(new SlotId(slotId), new UserId(userIdParam));
        return ResponseEntity.ok(new SlotResource(changedSlot));
    }

    @DeleteMapping("/{userIdParam}/slots/{slotId}")
    public ResponseEntity deleteSlot(@PathVariable UUID userIdParam, @PathVariable UUID slotId) {
        UserId userId = new UserId(userIdParam);
        calendarService.deleteSlotFromUsersCalendar(new SlotId(slotId), userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userIdParam}/slots/{slotId}")
    public ResponseEntity updateSlot(@PathVariable UUID userIdParam, @PathVariable UUID slotId, @RequestBody UpdateSlotRequest updateSlotRequest) {
        UserId userId = new UserId(userIdParam);
        Slot updatedSlot = calendarService.updateSlotInUsersCalendar(new SlotId(slotId), updateSlotRequest.startTime(), updateSlotRequest.duration(), updateSlotRequest.availability(), userId);
        return ResponseEntity.ok(new SlotResource(updatedSlot));
    }

    @PutMapping("/{userIdParam}/meetings/{meetingId}")
    public ResponseEntity updateMeeting(@PathVariable UUID userIdParam, @PathVariable UUID meetingId, @RequestBody UpdateMeetingRequest updateMeetingRequest) {
        UserId userId = new UserId(userIdParam);
        Meeting updatedMeeting = calendarService.updateMeetingDetails(
                new MeetingId(meetingId),
                updateMeetingRequest.title(),
                updateMeetingRequest.description(),
                updateMeetingRequest.participants().stream().map(UUID::fromString).map(UserId::new).toList(),
                userId
        );
        return ResponseEntity.ok(new MeetingResource(updatedMeeting));
    }
}
