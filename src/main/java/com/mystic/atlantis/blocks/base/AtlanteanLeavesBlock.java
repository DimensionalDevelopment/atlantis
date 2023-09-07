package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanLeavesBlock extends LeavesBlock implements SimpleWaterloggedBlock {

    public AtlanteanLeavesBlock(Properties settings) {
        super(settings
                .sound(SoundType.GRASS)
                .requiresCorrectToolForDrops()
                .strength(1.0F, 2.0F));
    }
}
