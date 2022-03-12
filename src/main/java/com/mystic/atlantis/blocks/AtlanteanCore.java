package com.mystic.atlantis.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class AtlanteanCore extends Block {
    public AtlanteanCore(AbstractBlock.Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.METAL)
//                .breakByTool(FabricToolTags.PICKAXES, 2) //TODO: Update
                .requiresTool()
                .luminance((state) -> 7)
                .strength(4.0F, 25.0F));
    }
}
