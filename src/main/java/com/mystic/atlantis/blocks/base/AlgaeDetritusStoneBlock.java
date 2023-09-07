package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AlgaeDetritusStoneBlock extends Block {

    public AlgaeDetritusStoneBlock(Properties settings) {
        super(settings
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 9.0F));
    }
}
