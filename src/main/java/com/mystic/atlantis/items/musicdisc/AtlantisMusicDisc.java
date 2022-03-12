package com.mystic.atlantis.items.musicdisc;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

public class AtlantisMusicDisc extends RecordItem {
    public AtlantisMusicDisc(int comparatorOutput, SoundEvent sound, Item.Properties settings){
        super(comparatorOutput, sound, settings);
    }
}
