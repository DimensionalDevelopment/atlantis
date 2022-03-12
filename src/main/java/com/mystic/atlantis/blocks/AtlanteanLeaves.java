package com.mystic.atlantis.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.sound.BlockSoundGroup;

public class AtlanteanLeaves extends LeavesBlock implements Waterloggable {

    public AtlanteanLeaves(AbstractBlock.Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.GRASS)
//                .breakByTool(FabricToolTags.SHEARS, 1) //TODO: Update
                .strength(1.0F, 2.0F));
    }
}
