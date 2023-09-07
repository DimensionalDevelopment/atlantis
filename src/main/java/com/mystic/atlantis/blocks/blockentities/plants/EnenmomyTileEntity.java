package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnenmomyTileEntity extends GeneralPlantBlockEntity<EnenmomyTileEntity> {
    public EnenmomyTileEntity(BlockPos targetPos, BlockState targetState) {
        super(TileEntityInit.ENENMOMY_TILE, "enenmomy_controller", targetPos, targetState);
    }
}
