package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;

public class NymphLeavesBlock extends LeavesBlock implements SimpleWaterloggedBlock {

    public NymphLeavesBlock(Properties settings) {
        super(settings
                .sound(SoundType.GRASS)
                .requiresCorrectToolForDrops()
                .strength(0.5F)
                .randomTicks()
                .noOcclusion());
    }
}
