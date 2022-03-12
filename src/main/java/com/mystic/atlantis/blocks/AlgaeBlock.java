package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AlgaeBlock extends Block {

    public AlgaeBlock(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.GRASS)
//                .breakByTool(FabricToolTags.AXES, 1) //TODO: Update
                .requiresCorrectToolForDrops()
                .strength(5.0F));
    }
}