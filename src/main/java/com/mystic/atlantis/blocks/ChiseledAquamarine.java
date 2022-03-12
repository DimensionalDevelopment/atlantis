package com.mystic.atlantis.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class ChiseledAquamarine extends Block {

    public ChiseledAquamarine(AbstractBlock.Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.STONE)
//                .breakByTool(FabricToolTags.PICKAXES, 2) //TODO: Update
                .requiresTool()
                .strength(6.0F));
    }
}