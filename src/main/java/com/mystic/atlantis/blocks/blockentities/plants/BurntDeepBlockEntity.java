package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BurntDeepBlockEntity extends GeneralPlantBlockEntity<BurntDeepBlockEntity> {
    public BurntDeepBlockEntity(BlockPos targetPos, BlockState targetState) {
        super(BlockEntityInit.BURNT_DEEP, "burntdeep_controller", targetPos, targetState);
    }
}
