package com.doodle.minidoodle.scheduling.spi;


import com.doodle.minidoodle.scheduling.Slot;
import com.doodle.minidoodle.scheduling.SlotId;

public interface SlotRepository {
    Slot save(Slot slot);

    Slot get(SlotId slotId);

    Slot delete(SlotId slotId);
}
