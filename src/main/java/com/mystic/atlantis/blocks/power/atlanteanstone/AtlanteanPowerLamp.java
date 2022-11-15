package com.mystic.atlantis.blocks.power.atlanteanstone;

import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanPowerLamp extends RedstoneLampBlock {

    public AtlanteanPowerLamp(Properties settings) {
        super(settings.sound(SoundType.GLASS).strength(0.3f).lightLevel(value -> value.getValue(LIT) ? 15 : 0).isValidSpawn((state, world, pos, type) -> true));
    }

}
