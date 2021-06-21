package com.mystic.atlantis.blocks.power;

import com.google.common.collect.Sets;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.mixin.RedstoneAccessor;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Set;

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

    protected static boolean connectsTo(BlockState state) {
        return connectsTo(state, null);
    }

    protected static boolean connectsTo(BlockState state, @Nullable Direction dir) {
        if (state.isOf(Blocks.REDSTONE_WIRE)) {
            return true;
        } else if (state.isOf(Blocks.REPEATER)) {
            Direction direction = state.get(RepeaterBlock.FACING);
            return direction == dir || direction.getOpposite() == dir;
        } else if (state.isOf(Blocks.OBSERVER)) {
            return dir == state.get(ObserverBlock.FACING);
        } else {
            return state.emitsRedstonePower() && dir != null;
        }
    }

    @Override
    protected void update(World world, BlockPos pos, BlockState state) {
        int i = this.getReceivedRedstonePower(world, pos);
        if (state.get(POWER) != i) {
            if (world.getBlockState(pos) == state) {
                world.setBlockState(pos, state.with(POWER, i), Block.NOTIFY_LISTENERS);
            }

            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction direction = var6[var8];
                set.add(pos.offset(direction));
            }

            Iterator var10 = set.iterator();

            while(var10.hasNext()) {
                BlockPos blockPos = (BlockPos)var10.next();
                world.updateNeighborsAlways(blockPos, this);
            }
        }
    }

    @Override
    public int getReceivedRedstonePower(World world, BlockPos pos) {
        ((RedstoneAccessor) this).setWiresGivePower(false);
        int i = world.getReceivedRedstonePower(pos);
        ((RedstoneAccessor) this).setWiresGivePower(true);
        int j = 0;
        if (i < 15) {
            Iterator var5 = Direction.Type.HORIZONTAL.iterator();

            while(true) {
                while(var5.hasNext()) {
                    Direction direction = (Direction)var5.next();
                    BlockPos blockPos = pos.offset(direction);
                    BlockState blockState = world.getBlockState(blockPos);
                    j = Math.max(j, this.increasePower(blockState));
                    BlockPos blockPos2 = pos.up();
                    if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                        j = Math.max(j, this.increasePower(world.getBlockState(blockPos.up())));
                    } else if (!blockState.isSolidBlock(world, blockPos)) {
                        j = Math.max(j, this.increasePower(world.getBlockState(blockPos.down())));
                    }
                }

                return Math.max(i, j - 1);
            }
        } else {
            return Math.max(i, j - 1);
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return ((RedstoneAccessor) this).getWiresGivePower();
    }

    private int increasePower(BlockState state) {
        return state.isOf(this) ? state.get(POWER) : 0;
    }

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
        //if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            return this.canRunOnTop(world, blockPos, blockState);
        /*} else {
            return false;
        }*/
    }

    private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, WIRE_CONNECTION_EAST, WIRE_CONNECTION_NORTH, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, POWER);
    }
}
