package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AtlanteanLeaves extends LeavesBlock implements SimpleWaterloggedBlock {

    public AtlanteanLeaves(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.GRASS)
                .requiresCorrectToolForDrops()
                .strength(1.0F, 2.0F));
    }
}
