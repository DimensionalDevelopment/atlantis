package com.mystic.atlantis.blocks;


import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AncientWoodStairs extends StairBlock {

    public AncientWoodStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        super(baseBlockState, settings
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}