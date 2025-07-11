package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlueLilyBlockEntity extends GeneralPlantBlockEntity<BlueLilyBlockEntity> {
    public BlueLilyBlockEntity(BlockPos targetPos, BlockState targetState) {
        super(BlockEntityInit.BLUE_LILY, "bluelily_controller", targetPos, targetState);
    }
}
