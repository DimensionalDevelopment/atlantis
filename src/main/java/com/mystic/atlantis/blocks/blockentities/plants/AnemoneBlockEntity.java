package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AnemoneBlockEntity extends GeneralPlantBlockEntity<AnemoneBlockEntity> {
    public AnemoneBlockEntity(BlockPos targetPos, BlockState targetState) {
        super(BlockEntityInit.ANEMONE, "anemone_controller", targetPos, targetState);
    }
}
