package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanBrickBlock extends Block {
	
    public AtlanteanBrickBlock(Properties settings) {
        super(settings
                .strength(3.0F, 7.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE));
    }
}

