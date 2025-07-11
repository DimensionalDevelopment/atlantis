package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TuberUpBlockEntity extends GeneralPlantBlockEntity<TuberUpBlockEntity> {
    public TuberUpBlockEntity(BlockPos targetPos, BlockState targetState) {
        super(BlockEntityInit.TUBER_UP, "tuberup_controller", targetPos, targetState);
    }
}
