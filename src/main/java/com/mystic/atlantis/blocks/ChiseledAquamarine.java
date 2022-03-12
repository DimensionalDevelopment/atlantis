package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ChiseledAquamarine extends Block {

    public ChiseledAquamarine(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.STONE)
//                .breakByTool(FabricToolTags.PICKAXES, 2) //TODO: Update
                .requiresCorrectToolForDrops()
                .strength(6.0F));
    }
}