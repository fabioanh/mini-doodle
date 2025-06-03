package com.doodle.minidoodle.scheduling.spi;

import com.doodle.minidoodle.scheduling.Meeting;
import com.doodle.minidoodle.scheduling.MeetingId;
import com.doodle.minidoodle.scheduling.UserId;

import java.util.List;

public interface MeetingRepository {
    Meeting save(Meeting meeting);

    Meeting update(Meeting meeting);

    Meeting get(MeetingId meetingId);

    List<Meeting> getUserMeetings(UserId userId);
}
