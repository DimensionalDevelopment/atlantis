package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BurntDeepTileEntity extends GeneralPlantBlockEntity<BurntDeepTileEntity> {
    public BurntDeepTileEntity(BlockPos targetPos, BlockState targetState) {
        super(TileEntityInit.BURNT_DEEP_TILE, "burntdeep_controller", targetPos, targetState);
    }
}
