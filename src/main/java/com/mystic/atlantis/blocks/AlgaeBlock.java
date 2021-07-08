package com.mystic.atlantis.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class AlgaeBlock extends Block {

    public AlgaeBlock(FabricBlockSettings properties) {
        super(properties
                .sounds(BlockSoundGroup.GRASS)
                .breakByTool(FabricToolTags.AXES, 1)
                .requiresTool()
                .strength(5.0F));
    }
}