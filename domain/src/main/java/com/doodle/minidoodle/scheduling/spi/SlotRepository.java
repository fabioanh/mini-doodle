package com.doodle.minidoodle.scheduling.spi;


import com.doodle.minidoodle.scheduling.Slot;

public interface SlotRepository {
    Slot save(Slot slot);
}
