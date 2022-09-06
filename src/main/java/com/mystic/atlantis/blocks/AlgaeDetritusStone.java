package com.mystic.atlantis.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class AlgaeDetritusStone extends Block {

    public AlgaeDetritusStone(Properties properties) {
        super(properties
                .sound(SoundType.STONE)
//                .breakByTool(FabricToolTags.PICKAXE, 2) //TODO: Update
                .requiresCorrectToolForDrops()
                .strength(3.0F, 9.0F));

    }
}
