package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AtlanteanCore extends Block {
    public AtlanteanCore(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
                .lightLevel((state) -> 7)
                .strength(4.0F, 25.0F));
    }
}
