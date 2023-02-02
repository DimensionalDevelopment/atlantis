package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AtlanteanWoodTrapdoor extends TrapDoorBlock {

    public AtlanteanWoodTrapdoor(BlockBehaviour.Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .noOcclusion()
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}
