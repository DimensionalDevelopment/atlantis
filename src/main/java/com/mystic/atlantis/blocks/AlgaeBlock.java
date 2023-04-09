package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AlgaeBlock extends Block {

    public AlgaeBlock(Properties properties) {
        super(properties
                .sound(SoundType.GRASS)
                .requiresCorrectToolForDrops()
                .strength(5.0F));
    }
}