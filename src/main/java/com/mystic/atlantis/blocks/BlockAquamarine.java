package com.mystic.atlantis.blocks;


import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;

public class BlockAquamarine extends Block
{
    public BlockAquamarine(FabricBlockSettings properties) {
        super(properties
                .sounds(BlockSoundGroup.METAL)
                .breakByTool(FabricToolTags.PICKAXES, 2) //TODO: Update
                .requiresTool()
                .strength(3.0F, 15.0F));
    }
}
