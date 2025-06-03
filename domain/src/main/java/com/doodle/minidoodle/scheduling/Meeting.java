package com.doodle.minidoodle.scheduling;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Meeting {
    private MeetingId meetingId;
    private LocalDateTime startTime;
    private Duration duration ;
    private String title;
    private String description;
    private List<UserId> participants;


}
