package com.mystic.atlantis.blocks.power;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class AtlanteanPowerDust extends RedstoneWireBlock implements Waterloggable {

    public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
    public AtlanteanPowerDust(Settings settings) {
        super(settings.noCollision().breakInstantly());
        this.setDefaultState(getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.NONE).with(WIRE_CONNECTION_EAST, WireConnection.NONE).with(WIRE_CONNECTION_SOUTH, WireConnection.NONE).with(WIRE_CONNECTION_WEST, WireConnection.NONE).with(POWER, 0).with(WATERLOGGED, Boolean.TRUE));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            return this.canRunOnTop(world, blockPos, blockState);
        } else {
            return false;
        }
    }

    private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, WIRE_CONNECTION_EAST, WIRE_CONNECTION_NORTH, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, POWER);
    }
}
