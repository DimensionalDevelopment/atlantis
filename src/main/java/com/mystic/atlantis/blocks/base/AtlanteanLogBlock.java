package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanLogBlock extends RotatedPillarBlock {

    public AtlanteanLogBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .strength(3.0F, 5.0F));

    }
}
