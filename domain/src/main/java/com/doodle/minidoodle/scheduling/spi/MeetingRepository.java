package com.doodle.minidoodle.scheduling.spi;

import com.doodle.minidoodle.scheduling.Meeting;
import com.doodle.minidoodle.scheduling.MeetingId;

public interface MeetingRepository {
    Meeting save(Meeting meeting);

    Meeting update(Meeting meeting);

    Meeting get(MeetingId meetingId);
}
