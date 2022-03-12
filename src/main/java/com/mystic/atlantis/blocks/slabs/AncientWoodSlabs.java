package com.mystic.atlantis.blocks.slabs;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.sound.BlockSoundGroup;

public class AncientWoodSlabs extends SlabBlock {
    public AncientWoodSlabs(AbstractBlock.Settings settings) {
        super(settings
                .sounds(BlockSoundGroup.WOOD)
//                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresTool()
                .strength(3.0F, 6.0F));
    }
}
