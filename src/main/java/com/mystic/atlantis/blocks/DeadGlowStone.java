package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class DeadGlowStone extends Block {
    public DeadGlowStone(Properties properties) {
        super(properties
                .sound(SoundType.GLASS)
                .strength(3.0F, 3.0F));
    }
}
