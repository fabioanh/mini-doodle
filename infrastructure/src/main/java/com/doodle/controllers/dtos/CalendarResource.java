package com.doodle.controllers.dtos;

import com.doodle.minidoodle.scheduling.Calendar;

import java.util.List;

public record CalendarResource(List<SlotResource> slots, List<MeetingResource> meetings) {
    public CalendarResource(Calendar calendar) {
        this(calendar.slots().stream().map(SlotResource::new).toList(),
                calendar.meetings().stream().map(MeetingResource::new).toList());
    }
}
