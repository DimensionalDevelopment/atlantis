package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AtlanteanCoreBlock extends Block {
	
    public AtlanteanCoreBlock(Properties settings) {
        super(settings
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
                .lightLevel((state) -> 7)
                .strength(4.0F, 25.0F));
    }
}
