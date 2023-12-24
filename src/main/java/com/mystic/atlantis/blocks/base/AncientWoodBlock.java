package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AncientWoodBlock extends Block {

    public AncientWoodBlock(Properties properties) {
        super(properties
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(2.0F));
    }
}
