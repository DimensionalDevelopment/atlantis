package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AncientWood extends Block {

    public AncientWood(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));

    }
}
