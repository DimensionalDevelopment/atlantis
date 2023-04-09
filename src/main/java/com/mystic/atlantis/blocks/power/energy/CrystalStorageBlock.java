package com.mystic.atlantis.blocks.power.energy;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;

public class CrystalStorageBlock extends Block {
	
    public CrystalStorageBlock(Properties settings) {
        super(settings
                .strength(4.5F)
                .color(MaterialColor.COLOR_LIGHT_BLUE)
                .lightLevel((state) -> 5)
                .sound(SoundType.AMETHYST));
    }
}
