package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AncientWoodFence extends FenceBlock {
    public AncientWoodFence(BlockBehaviour.Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}
