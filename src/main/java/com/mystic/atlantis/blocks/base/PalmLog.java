package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PalmLog extends RotatedPillarBlock {

    public PalmLog(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(4.0F, 6.0F));
    }
}
