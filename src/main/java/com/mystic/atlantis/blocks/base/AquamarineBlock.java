package com.mystic.atlantis.blocks.base;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AquamarineBlock extends Block {
	
    public AquamarineBlock(Properties settings) {
        super(settings
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 15.0F));
    }
}
