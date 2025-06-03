package com.doodle.db.repositories;

import com.doodle.db.entities.MeetingEntity;
import com.doodle.db.entities.SlotEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MeetingCrudRepository extends CrudRepository<MeetingEntity, UUID> {
    List<MeetingEntity> findByUserId(UUID id);
}
