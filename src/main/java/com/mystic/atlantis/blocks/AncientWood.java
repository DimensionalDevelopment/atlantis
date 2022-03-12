package com.mystic.atlantis.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class AncientWood extends Block {

    public AncientWood(AbstractBlock.Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.WOOD)
//                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresTool()
                .strength(3.0F, 6.0F));

    }
}
