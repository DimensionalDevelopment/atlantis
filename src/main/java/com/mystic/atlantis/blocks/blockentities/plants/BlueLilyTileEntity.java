package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlueLilyTileEntity extends GeneralPlantBlockEntity<BlueLilyTileEntity> {
    public BlueLilyTileEntity(BlockPos targetPos, BlockState targetState) {
        super(TileEntityInit.BLUE_LILY_TILE, "bluelily_controller", targetPos, targetState);
    }
}
