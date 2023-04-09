package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class DeadGlowStoneBlock extends Block {
	
    public DeadGlowStoneBlock(Properties settings) {
        super(settings
                .sound(SoundType.GLASS)
                .strength(3.0F, 3.0F));
    }
}
