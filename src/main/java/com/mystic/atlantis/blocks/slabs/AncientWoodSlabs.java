package com.mystic.atlantis.blocks.slabs;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.SlabBlock;
import net.minecraft.sound.BlockSoundGroup;

public class AncientWoodSlabs extends SlabBlock {
    public AncientWoodSlabs(FabricBlockSettings settings) {
        super(settings
                .sounds(BlockSoundGroup.WOOD)
                .breakByTool(FabricToolTags.AXES, 2)
                .requiresTool()
                .strength(3.0F, 6.0F));
    }
}
