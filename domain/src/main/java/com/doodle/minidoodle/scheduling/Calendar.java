package com.doodle.minidoodle.scheduling;


import java.util.Collections;
import java.util.List;

public record Calendar(
        List<Slot> slots,
        List<Meeting> meetings
) {
    public Calendar() {
        this(Collections.emptyList(), Collections.emptyList());
    }
}
