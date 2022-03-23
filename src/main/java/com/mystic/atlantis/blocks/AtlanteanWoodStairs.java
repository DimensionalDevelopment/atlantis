package com.mystic.atlantis.blocks;


import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AtlanteanWoodStairs extends StairBlock {

    public AtlanteanWoodStairs(BlockState baseBlockState, Properties settings) {
        super(baseBlockState, settings
                .sound(SoundType.WOOD)
//                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}