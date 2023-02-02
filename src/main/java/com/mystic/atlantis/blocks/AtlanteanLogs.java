package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AtlanteanLogs extends RotatedPillarBlock {

    public AtlanteanLogs(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.WOOD)
                .strength(3.0F, 5.0F));

    }
}
