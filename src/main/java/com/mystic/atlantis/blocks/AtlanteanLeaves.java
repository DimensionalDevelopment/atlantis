package com.mystic.atlantis.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.sound.BlockSoundGroup;

public class AtlanteanLeaves extends LeavesBlock implements Waterloggable {

    public AtlanteanLeaves(FabricBlockSettings properties) {
        super(properties
                .sounds(BlockSoundGroup.GRASS)
                .breakByTool(FabricToolTags.SHEARS, 1) //TODO: Update
                .strength(1.0F, 2.0F));
    }
}
