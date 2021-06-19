package com.mystic.atlantis.blocks.power;

import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.sound.BlockSoundGroup;

public class AtlanteanPowerLamp extends RedstoneLampBlock {

    public AtlanteanPowerLamp(Settings settings) {
        super(settings.sounds(BlockSoundGroup.GLASS).luminance(value -> value.get(LIT) ? 15 : 0));
    }

}
