package com.mystic.atlantis.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;

public class AtlanteanLogs extends PillarBlock {

    public AtlanteanLogs(FabricBlockSettings properties) {
        super(properties
                .sounds(BlockSoundGroup.WOOD)
                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresTool()
                .strength(4.0F, 7.0F));

    }
}
