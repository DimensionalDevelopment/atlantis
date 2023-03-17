package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;

public class SeaBedBlock extends Block {
    public SeaBedBlock(Properties properties) {
        super(properties.strength(1.0f, 6.0f).sound(SoundType.STONE).color(MaterialColor.STONE).requiresCorrectToolForDrops());
    }
}
