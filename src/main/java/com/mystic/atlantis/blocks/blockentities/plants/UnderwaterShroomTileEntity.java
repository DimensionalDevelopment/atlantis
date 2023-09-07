package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class UnderwaterShroomTileEntity extends GeneralPlantBlockEntity<UnderwaterShroomTileEntity> {
    public UnderwaterShroomTileEntity(BlockPos targetPos, BlockState targetState) {
        super(TileEntityInit.UNDERWATER_SHROOM_TILE, "underwatershroom_controller", targetPos, targetState);
    }
}
