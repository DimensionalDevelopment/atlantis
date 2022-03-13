package com.mystic.atlantis.items.musicdisc;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

import java.util.function.Supplier;

public class AtlantisMusicDisc extends RecordItem {
    public AtlantisMusicDisc(int comparatorOutput, Supplier<SoundEvent> sound, Item.Properties settings){
        super(comparatorOutput, sound, settings);
    }
}
