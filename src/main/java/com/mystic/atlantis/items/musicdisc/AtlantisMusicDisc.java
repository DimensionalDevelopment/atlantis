package com.mystic.atlantis.items.musicdisc;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

public class AtlantisMusicDisc extends MusicDiscItem {
    public AtlantisMusicDisc(int comparatorOutput, SoundEvent sound, Item.Settings settings){
        super(comparatorOutput, sound, settings);
    }
}
