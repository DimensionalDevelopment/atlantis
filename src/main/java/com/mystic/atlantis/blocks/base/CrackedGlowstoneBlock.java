package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class CrackedGlowstoneBlock extends Block {
	
    public CrackedGlowstoneBlock(Properties settings) {
        super(settings
                .sound(SoundType.GLASS)
                .strength(3.0F, 3.0F)
                .lightLevel((state) -> 7));
    }
}
