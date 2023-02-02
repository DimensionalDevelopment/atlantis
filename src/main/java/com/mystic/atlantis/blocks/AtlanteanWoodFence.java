package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanWoodFence extends FenceBlock {
    public AtlanteanWoodFence(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}
