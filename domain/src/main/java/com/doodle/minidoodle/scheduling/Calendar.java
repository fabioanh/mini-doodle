package com.doodle.minidoodle.scheduling;


import java.util.List;

public class Calendar {
    private List<SlotId> slots;
    private List<MeetingId> meetings;

    public void addSlot(SlotId slotId) {
        if (slotId == null) {
            throw new IllegalArgumentException("SlotId must not be null");
        }
        slots.add(slotId);
    }
}
