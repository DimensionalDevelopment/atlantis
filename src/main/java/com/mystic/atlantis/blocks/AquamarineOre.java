package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AquamarineOre extends Block
{
    public AquamarineOre(BlockBehaviour.Properties properties)
    {
        super(properties
            .sound(SoundType.STONE)
//            .breakByTool(FabricToolTags.PICKAXES, 2) //TODO: Update
            .requiresCorrectToolForDrops()
            .strength(3.0F, 15.0F)
            .lightLevel((state) -> 2));
    }
}
