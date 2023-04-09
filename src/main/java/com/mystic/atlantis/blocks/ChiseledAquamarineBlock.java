package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class ChiseledAquamarineBlock extends Block {

    public ChiseledAquamarineBlock(Properties settings) {
        super(settings
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops()
                .strength(6.0F));
    }
}