package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.sound.BlockSoundGroup;

public class AncientWoodTrapdoor extends TrapdoorBlock {

    public AncientWoodTrapdoor(Block.Settings settings) {
        super(settings
                .sounds(BlockSoundGroup.WOOD)
//                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresTool()
                .strength(3.0F, 6.0F));
    }
}
