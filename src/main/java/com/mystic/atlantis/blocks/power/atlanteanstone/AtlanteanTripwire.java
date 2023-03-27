package com.mystic.atlantis.blocks.power.atlanteanstone;

import java.util.Map;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AtlanteanTripwire extends TripWireBlock implements SimpleWaterloggedBlock {

    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((entry) -> entry.getKey().getAxis().isHorizontal()).collect(Util.toMap());
    private final AtlanteanTripwireHook hookBlock;
    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

    public AtlanteanTripwire(AtlanteanTripwireHook hookBlock, Properties settings) {
        super(hookBlock, settings.instabreak());
        this.hookBlock = hookBlock;
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false).setValue(ATTACHED, false).setValue(DISARMED, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, true));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction.getAxis().isHorizontal() ? state.setValue(FACING_PROPERTIES.get(direction), this.shouldConnectTo(neighborState, direction)) : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean shouldConnectTo(BlockState state, Direction facing) {
        if (state.getFluidState().is(FluidTags.WATER)) {
            if (state.is(this.hookBlock)) {
                return state.getValue(AtlanteanTripwireHook.FACING) == facing.getOpposite();
            } else {
                return state.is(this);
            }
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
