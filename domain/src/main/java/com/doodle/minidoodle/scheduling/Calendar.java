package com.doodle.minidoodle.scheduling;


import java.util.Collections;
import java.util.List;

public class Calendar {
    private List<Slot> slots;
    private List<Meeting> meetings;

    public Calendar(){
        this.slots = Collections.emptyList();
        this.meetings = Collections.emptyList();
    }

    public Calendar(List<Slot> slots, List<Meeting> meetings) {
        this.slots = slots;
        this.meetings = meetings;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }
}
