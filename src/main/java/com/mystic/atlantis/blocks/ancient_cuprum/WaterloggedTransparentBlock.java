package com.mystic.atlantis.blocks.ancient_cuprum;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class WaterloggedTransparentBlock extends HalfTransparentBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterloggedTransparentBlock(BlockBehaviour.Properties p_313902_) {
        super(p_313902_);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_313836_) {
        FluidState fluidstate = p_313836_.getLevel().getFluidState(p_313836_.getClickedPos());
        return super.getStateForPlacement(p_313836_).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.is(Fluids.WATER)));
    }

    @Override
    public BlockState updateShape(
        BlockState p_313906_, Direction p_313739_, BlockState p_313829_, LevelAccessor p_313692_, BlockPos p_313842_, BlockPos p_313843_
    ) {
        if (p_313906_.getValue(WATERLOGGED)) {
            p_313692_.scheduleTick(p_313842_, Fluids.WATER, Fluids.WATER.getTickDelay(p_313692_));
        }

        return super.updateShape(p_313906_, p_313739_, p_313829_, p_313692_, p_313842_, p_313843_);
    }

    @Override
    public FluidState getFluidState(BlockState p_313789_) {
        return p_313789_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(true) : super.getFluidState(p_313789_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_313896_) {
        p_313896_.add(WATERLOGGED);
    }
}
