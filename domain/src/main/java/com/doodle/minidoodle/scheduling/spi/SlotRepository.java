package com.doodle.minidoodle.scheduling.spi;


import com.doodle.minidoodle.scheduling.Slot;
import com.doodle.minidoodle.scheduling.SlotId;
import com.doodle.minidoodle.scheduling.UserId;

import java.util.List;

public interface SlotRepository {
    Slot save(Slot slot);

    Slot get(SlotId slotId);

    void delete(SlotId slotId);

    Slot update(Slot slot);

    List<Slot> getUserSlots(UserId userId);
}
