package com.mystic.atlantis.blocks.slabs;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;

public class AncientWoodSlabBlock extends SlabBlock {
	
    public AncientWoodSlabBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}
