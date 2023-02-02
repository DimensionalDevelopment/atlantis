package com.mystic.atlantis.blocks;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AquamarineBlock extends Block
{
    public AquamarineBlock(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 15.0F));
    }
}
