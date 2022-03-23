package com.mystic.atlantis.blocks.blockentities;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;

public class AtlanteanWoodTrapdoor extends TrapDoorBlock {

    public AtlanteanWoodTrapdoor(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
//                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}
