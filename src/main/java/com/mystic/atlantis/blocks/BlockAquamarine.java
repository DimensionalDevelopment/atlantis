package com.mystic.atlantis.blocks;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class BlockAquamarine extends Block
{
    public BlockAquamarine(AbstractBlock.Settings properties) {
        super(properties
                .sounds(BlockSoundGroup.METAL)
//                .breakByTool(FabricToolTags.PICKAXES, 2) //TODO: Update
                .requiresTool()
                .strength(3.0F, 15.0F));
    }
}
