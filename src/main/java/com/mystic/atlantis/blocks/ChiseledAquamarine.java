package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;

public class ChiseledAquamarine extends Block {

    public ChiseledAquamarine(FabricBlockSettings properties) {
        super(properties
                .sounds(BlockSoundGroup.STONE)
                .breakByTool(FabricToolTags.PICKAXES, 2) //TODO: Update
                .requiresTool()
                .strength(6.0F));
    }
}