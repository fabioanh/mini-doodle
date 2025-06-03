package com.doodle.controllers;

import com.doodle.minidoodle.scheduling.Calendar;

import java.util.List;

public record CalendarResource(List<SlotResource> slots, List<MeetingResource> meetings) {

//    public CalendarResource {
//        if (slots == null || meetings == null) {
//            throw new IllegalArgumentException("Slots and meetings must not be null");
//        }
//    }

    public CalendarResource(Calendar calendar) {
        this(calendar.getSlots().stream().map(SlotResource::new).toList(),
                calendar.getMeetings().stream().map(MeetingResource::new).toList());
    }


}
