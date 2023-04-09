package com.mystic.atlantis.blocks.power.atlanteanstone;

import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanPowerLampBlock extends RedstoneLampBlock {

    public AtlanteanPowerLampBlock(Properties settings) {
        super(settings
        		.sound(SoundType.GLASS)
        		.strength(0.3f)
        		.lightLevel(value -> value.getValue(LIT) ? 15 : 0)
        		.isValidSpawn((state, world, pos, type) -> true));
    }

}
