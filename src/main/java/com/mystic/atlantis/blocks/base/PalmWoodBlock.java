package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class PalmWoodBlock extends Block {

    public PalmWoodBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));

    }
}
