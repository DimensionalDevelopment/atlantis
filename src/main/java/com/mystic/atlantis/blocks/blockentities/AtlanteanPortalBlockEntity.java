package com.mystic.atlantis.blocks.blockentities;

import com.mystic.atlantis.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AtlanteanPortalBlockEntity extends BlockEntity {
   protected AtlanteanPortalBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
      super(pType, pPos, pBlockState);
   }

   public AtlanteanPortalBlockEntity(BlockPos pPos, BlockState pBlockState) {
      this(BlockEntityInit.ATLANTEAN_PORTAL.get(), pPos, pBlockState);
   }
}