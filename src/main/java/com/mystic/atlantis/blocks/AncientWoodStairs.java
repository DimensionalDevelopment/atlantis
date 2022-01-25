package com.mystic.atlantis.blocks;


import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.sound.BlockSoundGroup;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;

public class AncientWoodStairs extends StairsBlock {

    public AncientWoodStairs(BlockState baseBlockState, FabricBlockSettings settings) {
        super(baseBlockState, settings
                .sounds(BlockSoundGroup.WOOD)
                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresTool()
                .strength(3.0F, 6.0F));
    }
}