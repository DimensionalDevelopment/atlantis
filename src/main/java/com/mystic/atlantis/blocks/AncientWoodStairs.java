package com.mystic.atlantis.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.sound.BlockSoundGroup;

public class AncientWoodStairs extends StairsBlock {

    public AncientWoodStairs(BlockState baseBlockState, Block.Settings settings) {
        super(baseBlockState, settings
                .sounds(BlockSoundGroup.WOOD)
//                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresTool()
                .strength(3.0F, 6.0F));
    }
}