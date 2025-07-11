package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;

public class NymphFenceBlock extends FenceBlock {
	
    public NymphFenceBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}
