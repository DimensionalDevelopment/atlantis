package com.mystic.atlantis.blocks.power;

import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TripwireBlock;
import net.minecraft.block.TripwireHookBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class AtlanteanTripwireHook extends TripwireHookBlock implements Waterloggable {
    private static final Property<Boolean> WATERLOGGED = AtlanteanPowerTorch.WATERLOGGED;

    public AtlanteanTripwireHook(Settings settings) {
        super(settings.breakInstantly());
        this.getDefaultState().with(WATERLOGGED, Boolean.TRUE);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.WATER.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, ATTACHED, WATERLOGGED);
    }



    @Override
    public void update(World world, BlockPos pos, BlockState state, boolean beingRemoved, boolean bl, int i, @Nullable BlockState blockState) {
        Direction direction = state.get(TripwireHookBlock.FACING);
        boolean bl2 = state.get(ATTACHED);
        boolean bl3 = state.get(POWERED);
        boolean bl4 = !beingRemoved;
        boolean bl5 = false;
        int j = 0;
        BlockState[] blockStates = new BlockState[42];

        BlockPos blockPos2;
        for(int k = 1; k < 42; ++k) {
            blockPos2 = pos.offset(direction, k);
            BlockState blockState2 = world.getBlockState(blockPos2);
            if (blockState2.isOf(this)) {
                if (blockState2.get(TripwireHookBlock.FACING) == direction.getOpposite()) {
                    j = k;
                }
                break;
            }

            if (!blockState2.isOf(this) && k != i) {
                blockStates[k] = null;
                bl4 = false;
            } else {
                if (k == i) {
                    blockState2 = MoreObjects.firstNonNull(blockState, blockState2);
                }

                boolean bl6 = !(Boolean)blockState2.get(TripwireBlock.DISARMED);
                boolean bl7 = blockState2.get(TripwireBlock.POWERED);
                bl5 |= bl6 && bl7;
                blockStates[k] = blockState2;
                if (k == i) {
                    world.createAndScheduleBlockTick(pos, this, 10);
                    bl4 &= bl6;
                }
            }
        }

        bl4 &= j > 1;
        bl5 &= bl4;
        BlockState blockState3 = this.getDefaultState().with(ATTACHED, bl4).with(POWERED, bl5);
        if (j > 0) {
            blockPos2 = pos.offset(direction, j);
            Direction direction2 = direction.getOpposite();
            world.setBlockState(blockPos2, blockState3.with(TripwireHookBlock.FACING, direction2), Block.NOTIFY_ALL);
            this.updateNeighborsOnAxis(world, blockPos2, direction2);
            this.playSound(world, blockPos2, bl4, bl5, bl2, bl3);
        }

        this.playSound(world, pos, bl4, bl5, bl2, bl3);
        if (!beingRemoved) {
            world.setBlockState(pos, blockState3.with(TripwireHookBlock.FACING, direction), Block.NOTIFY_ALL);
            if (bl) {
                this.updateNeighborsOnAxis(world, pos, direction);
            }
        }

        if (bl2 != bl4) {
            for(int l = 1; l < j; ++l) {
                BlockPos blockPos3 = pos.offset(direction, l);
                BlockState blockState4 = blockStates[l];
                if (blockState4 != null) {
                    world.setBlockState(blockPos3, blockState4.with(ATTACHED, bl4), Block.NOTIFY_ALL);
                    if (!world.getBlockState(blockPos3).isAir()) {
                    }
                }
            }
        }
    }

    private void playSound(World world, BlockPos pos, boolean attached, boolean on, boolean detached, boolean off) {
        if (on && !off) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 0.4F, 0.6F);
            world.emitGameEvent(GameEvent.BLOCK_PRESS, pos);
        } else if (!on && off) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_OFF, SoundCategory.BLOCKS, 0.4F, 0.5F);
            world.emitGameEvent(GameEvent.BLOCK_UNPRESS, pos);
        } else if (attached && !detached) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_ATTACH, SoundCategory.BLOCKS, 0.4F, 0.7F);
            world.emitGameEvent(GameEvent.BLOCK_ATTACH, pos);
        } else if (!attached && detached) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_DETACH, SoundCategory.BLOCKS, 0.4F, 1.2F / (world.random.nextFloat() * 0.2F + 0.9F));
            world.emitGameEvent(GameEvent.BLOCK_DETACH, pos);
        }
    }

    private void updateNeighborsOnAxis(World world, BlockPos pos, Direction direction) {
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(direction.getOpposite()), this);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
            Direction direction = state.get(FACING);
            BlockPos blockPos = pos.offset(direction.getOpposite());
            BlockState blockState = world.getBlockState(blockPos);
            return direction.getAxis().isHorizontal() && blockState.isSideSolidFullSquare(world, blockPos, direction);
        } else {
            return false;
        }
    }
}
