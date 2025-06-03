package com.doodle.db.repositories;

import com.doodle.db.entities.MeetingEntity;
import com.doodle.minidoodle.scheduling.Meeting;
import com.doodle.minidoodle.scheduling.MeetingId;
import com.doodle.minidoodle.scheduling.UserId;
import com.doodle.minidoodle.scheduling.spi.MeetingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeetingPostgresRepository implements MeetingRepository {
    private final MeetingCrudRepository meetingCrudRepository;

    public MeetingPostgresRepository(MeetingCrudRepository meetingCrudRepository) {
        this.meetingCrudRepository = meetingCrudRepository;
    }

    @Override
    public Meeting save(Meeting meeting) {
        return meetingCrudRepository.save(new MeetingEntity(meeting)).toDomain();
    }

    @Override
    public Meeting update(Meeting meeting) {
        return meetingCrudRepository.save(new MeetingEntity(meeting)).toDomain();
    }

    @Override
    public Meeting get(MeetingId meetingId) {
        return meetingCrudRepository.findById(meetingId.id())
                .map(MeetingEntity::toDomain)
                .orElse(null);
    }

    @Override
    public List<Meeting> getUserMeetings(UserId userId) {
        return meetingCrudRepository.findByUserId(userId.id())
                .stream()
                .map(MeetingEntity::toDomain)
                .toList();
    }
}
