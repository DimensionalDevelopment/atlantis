package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SeashroomBlockEntity extends GeneralPlantBlockEntity<SeashroomBlockEntity> {
    public SeashroomBlockEntity(BlockPos targetPos, BlockState targetState) {
        super(BlockEntityInit.SEASHROOM, "seashroom_controller", targetPos, targetState);
    }
}
