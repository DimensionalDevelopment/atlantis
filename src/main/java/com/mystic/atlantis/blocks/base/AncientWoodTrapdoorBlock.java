package com.mystic.atlantis.blocks.base;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class AncientWoodTrapdoorBlock extends TrapDoorBlock {

    public AncientWoodTrapdoorBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .noOcclusion()
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F), BlockSetType.OAK);
    }
}
