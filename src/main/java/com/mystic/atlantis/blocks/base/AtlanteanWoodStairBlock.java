package com.mystic.atlantis.blocks.base;


import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AtlanteanWoodStairBlock extends StairBlock {

    public AtlanteanWoodStairBlock(BlockState baseBlockState, Properties settings) {
        super(() -> baseBlockState, settings
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}