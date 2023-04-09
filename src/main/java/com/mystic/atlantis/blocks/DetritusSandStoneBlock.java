package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class DetritusSandStoneBlock extends Block {

    public DetritusSandStoneBlock(Properties settings) {
        super(settings
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops()
                .strength(5.0F, 9.0F));

    }
}
