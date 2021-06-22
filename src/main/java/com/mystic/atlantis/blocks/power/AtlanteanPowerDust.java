package com.mystic.atlantis.blocks.power;

import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.mixin.RedstoneAccessor;

public class AtlanteanPowerDust extends RedstoneWireBlock implements Waterloggable {

    public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (((RedstoneAccessor) this).getWiresGivePower() && direction != Direction.DOWN) {
            int i = state.get(POWER);
            if (i == 0) {
                return 0;
            } else {
                return direction != Direction.UP && !((WireConnection)this.getPlacementState(world, state, pos).get((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction.getOpposite()))).isConnected() ? 0 : i;
            }
        } else {
            return 0;
        }
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return !((RedstoneAccessor) this).getWiresGivePower() ? 0 : state.getWeakRedstonePower(world, pos, direction);
    }

    @Override
    protected void update(World world, BlockPos pos, BlockState state) {
        int receivedPower = this.getReceivedRedstonePower(world, pos);
        if (state.get(POWER) != receivedPower) {
            if (world.getBlockState(pos) == state) {
                world.setBlockState(pos, state.with(POWER, receivedPower), Block.NOTIFY_LISTENERS);
            }

            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();

            for (Direction direction : var6) {
                set.add(pos.offset(direction));
            }

            for (BlockPos blockPos : set) {
                world.updateNeighborsAlways(blockPos, this);
            }
        }
    }

    @Override
    public int getReceivedRedstonePower(World world, BlockPos pos) {
        ((RedstoneAccessor) this).setWiresGivePower(false);
        int receivedPower = world.getReceivedRedstonePower(pos);
        ((RedstoneAccessor) this).setWiresGivePower(true);
        int calculatedPower = 0;
        if (receivedPower < 15 && receivedPower > 0) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.increasePower(blockState));
                BlockPos blockPos2 = pos.up();
                if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.up())));
                } else if (!blockState.isSolidBlock(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.down())));
                }
            }

            return Math.max(receivedPower - 1, calculatedPower - 1);
        } else if (receivedPower == 0) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                calculatedPower = Math.max(calculatedPower, this.increasePower(blockState));
                BlockPos blockPos2 = pos.up();
                if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.up())));
                } else if (!blockState.isSolidBlock(world, blockPos)) {
                    calculatedPower = Math.max(calculatedPower, this.increasePower(world.getBlockState(blockPos.down())));
                }
            }

            return Math.max(receivedPower, calculatedPower - 1);
        } else {
            return Math.max(receivedPower - 1, calculatedPower - 1);
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return ((RedstoneAccessor) this).getWiresGivePower();
    }

    private int increasePower(BlockState state) {
        if (state.isOf(Blocks.REDSTONE_WIRE)) {
            return state.get(RedstoneWireBlock.POWER);
        } else if (state.isOf(BlockInit.ATLANTEAN_POWER_DUST_WIRE)) {
            return state.get(RedstoneWireBlock.POWER);
        }
        return 0;
    }

    public AtlanteanPowerDust(Settings settings) {
        super(settings.noCollision().breakInstantly());
        this.setDefaultState(getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.NONE).with(WIRE_CONNECTION_EAST, WireConnection.NONE).with(WIRE_CONNECTION_SOUTH, WireConnection.NONE).with(WIRE_CONNECTION_WEST, WireConnection.NONE).with(POWER, 0).with(WATERLOGGED, Boolean.TRUE));
        ((RedstoneAccessor) this).setWiresGivePower(true);
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
