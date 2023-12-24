package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanWoodBlock extends Block {

    public AtlanteanWoodBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(2.0F));

    }
}
