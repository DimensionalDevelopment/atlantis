package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class CalciteBlock extends Block {
    public CalciteBlock(Properties settings) {
        super(settings.sound(SoundType.CALCITE)
        .requiresCorrectToolForDrops()
        .strength(5.0F, 30.0F));
    }
}
