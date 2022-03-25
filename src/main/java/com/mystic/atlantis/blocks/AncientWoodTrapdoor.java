package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AncientWoodTrapdoor extends TrapDoorBlock {

    public AncientWoodTrapdoor(BlockBehaviour.Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .noOcclusion()
//                .breakByTool(FabricToolTags.AXES, 2) //TODO: Update
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
    }
}
