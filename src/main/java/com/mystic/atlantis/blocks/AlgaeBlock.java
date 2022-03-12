package com.mystic.atlantis.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class AlgaeBlock extends Block {

    public AlgaeBlock(AbstractBlock.Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.GRASS)
//                .breakByTool(FabricToolTags.AXES, 1) //TODO: Update
                .requiresTool()
                .strength(5.0F));
    }
}