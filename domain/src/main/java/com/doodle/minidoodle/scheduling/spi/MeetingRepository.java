package com.doodle.minidoodle.scheduling.spi;

import com.doodle.minidoodle.scheduling.Meeting;

public interface MeetingRepository {
    Meeting save(Meeting meeting);
}
