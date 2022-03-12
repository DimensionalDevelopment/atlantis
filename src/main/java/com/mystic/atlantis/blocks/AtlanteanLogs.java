package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;

public class AtlanteanLogs extends PillarBlock {

    public AtlanteanLogs(Block.Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.WOOD)
//                .breakByTool(FabricToolTags.AXES, 1) //TODO: Update
                .strength(3.0F, 5.0F));

    }
}
