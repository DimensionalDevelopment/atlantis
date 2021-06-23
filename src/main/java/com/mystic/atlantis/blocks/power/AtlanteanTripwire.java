package com.mystic.atlantis.blocks.power;

import com.google.common.base.MoreObjects;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AtlanteanTripwire extends TripwireBlock implements Waterloggable {

    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter((entry) -> {
        return entry.getKey().getAxis().isHorizontal();
    }).collect(Util.toMap());
    private final AtlanteanTripwireHook hookBlock;
    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

    public AtlanteanTripwire(AtlanteanTripwireHook hookBlock, Settings settings) {
        super(hookBlock, settings.breakInstantly());
        this.hookBlock = hookBlock;
        this.setDefaultState(this.getDefaultState().with(POWERED, false).with(ATTACHED, false).with(DISARMED, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, true));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction.getAxis().isHorizontal() ? state.with(FACING_PROPERTIES.get(direction), this.shouldConnectTo(neighborState, direction)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean shouldConnectTo(BlockState state, Direction facing) {
        if (state.getFluidState().isIn(FluidTags.WATER)) {
            if (state.isOf(this.hookBlock)) {
                return state.get(AtlanteanTripwireHook.FACING) == facing.getOpposite();
            } else {
                return state.isOf(this);
            }
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
