package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class DeadGlowstoneBlock extends Block {
	
    public DeadGlowstoneBlock(Properties settings) {
        super(settings
                .sound(SoundType.GLASS)
                .strength(3.0F, 3.0F));
    }
}
