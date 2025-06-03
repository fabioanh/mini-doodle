package com.doodle.db.repositories;

import com.doodle.db.entities.SlotEntity;
import com.doodle.minidoodle.scheduling.Slot;
import com.doodle.minidoodle.scheduling.SlotId;
import com.doodle.minidoodle.scheduling.UserId;
import com.doodle.minidoodle.scheduling.spi.SlotRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SlotPostgresRepository implements SlotRepository {
    private final SlotCrudRepository slotCrudRepository;

    public SlotPostgresRepository(SlotCrudRepository slotCrudRepository) {
        this.slotCrudRepository = slotCrudRepository;
    }

    @Override
    public Slot save(Slot slot) {
        return this.slotCrudRepository.save(new SlotEntity(slot)).toDomain();
    }

    @Override
    public Slot get(SlotId slotId) {
        return this.slotCrudRepository.findById(slotId.id())
                .map(SlotEntity::toDomain)
                .orElse(null);
    }

    @Override
    public void delete(SlotId slotId) {
        this.slotCrudRepository.deleteById(slotId.id());
    }

    @Override
    public Slot update(Slot slot) {
        return this.slotCrudRepository.save(new SlotEntity(slot)).toDomain();
    }

    @Override
    public List<Slot> getUserSlots(UserId userId) {
        return this.slotCrudRepository.findByUserId(userId.id())
                .stream()
                .map(SlotEntity::toDomain)
                .toList();
    }
}
