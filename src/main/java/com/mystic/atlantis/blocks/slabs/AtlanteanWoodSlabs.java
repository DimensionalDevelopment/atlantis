package com.mystic.atlantis.blocks.slabs;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanWoodSlabs extends SlabBlock {
    public AtlanteanWoodSlabs(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
//                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}
