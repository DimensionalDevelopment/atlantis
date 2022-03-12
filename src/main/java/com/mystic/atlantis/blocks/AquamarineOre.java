package com.mystic.atlantis.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class AquamarineOre extends Block
{
    public AquamarineOre(AbstractBlock.Settings properties)
    {
        super(properties
            .sounds(BlockSoundGroup.STONE)
//            .breakByTool(FabricToolTags.PICKAXES, 2) //TODO: Update
            .requiresTool()
            .strength(3.0F, 15.0F)
            .luminance((state) -> 2));
    }
}
