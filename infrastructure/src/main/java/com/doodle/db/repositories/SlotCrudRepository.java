package com.doodle.db.repositories;

import com.doodle.db.entities.SlotEntity;
import com.doodle.minidoodle.scheduling.Slot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SlotCrudRepository extends CrudRepository<SlotEntity, UUID> {
    List<SlotEntity> findByUserId(UUID id);
}
