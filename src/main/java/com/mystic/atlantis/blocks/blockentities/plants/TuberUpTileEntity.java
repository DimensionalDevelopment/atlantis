package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TuberUpTileEntity extends GeneralPlantBlockEntity<TuberUpTileEntity> {
    public TuberUpTileEntity(BlockPos targetPos, BlockState targetState) {
        super(TileEntityInit.TUBER_UP_TILE, "tuberup_controller", targetPos, targetState);
    }
}
