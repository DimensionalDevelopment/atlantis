package com.mystic.atlantis.items.musicdisc;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.RecordItem;

public class AtlantisMusicDisc extends RecordItem {
    public AtlantisMusicDisc(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties builder, int lengthInTicks) {
        super(comparatorValue, soundSupplier, builder, lengthInTicks);
    }
}
