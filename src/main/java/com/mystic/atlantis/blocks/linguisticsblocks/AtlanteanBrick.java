package com.mystic.atlantis.blocks.linguisticsblocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanBrick extends Block {
    public AtlanteanBrick(Properties properties) {
        super(properties
                .strength(3.0F, 7.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE));
    }
}

