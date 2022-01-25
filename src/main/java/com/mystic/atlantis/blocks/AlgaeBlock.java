package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;

public class AlgaeBlock extends Block {

    public AlgaeBlock(FabricBlockSettings properties) {
        super(properties
                .sounds(BlockSoundGroup.GRASS)
                .breakByTool(FabricToolTags.AXES, 1) //TODO: Update
                .requiresTool()
                .strength(5.0F));
    }
}