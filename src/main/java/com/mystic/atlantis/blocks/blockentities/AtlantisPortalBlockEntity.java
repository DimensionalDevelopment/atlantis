package com.mystic.atlantis.blocks.blockentities;

import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AtlantisPortalBlockEntity extends BlockEntity {
   protected AtlantisPortalBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
      super(pType, pPos, pBlockState);
   }

   public AtlantisPortalBlockEntity(BlockPos pPos, BlockState pBlockState) {
      this(TileEntityInit.ATLANTIS_PORTAL.get(), pPos, pBlockState);
   }

   public boolean shouldRenderFace(Direction pFace) {
      return pFace.getAxis() == Direction.Axis.Y;
   }
}