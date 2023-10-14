package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;

public class PalmLeavesBlock extends LeavesBlock implements SimpleWaterloggedBlock {

    public PalmLeavesBlock(Properties settings) {
        super(settings
                .sound(SoundType.GRASS)
                .requiresCorrectToolForDrops()
                .strength(1.0F, 2.0F)
                .randomTicks()
                .noOcclusion());
    }
}
