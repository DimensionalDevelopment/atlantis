package com.mystic.atlantis.blocks.power;

import com.google.common.base.MoreObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class AtlanteanTripwireHook extends TripWireHookBlock implements SimpleWaterloggedBlock {
    private static final Property<Boolean> WATERLOGGED = AtlanteanPowerTorch.WATERLOGGED;

    public AtlanteanTripwireHook(Properties settings) {
        super(settings.instabreak());
        this.defaultBlockState().setValue(WATERLOGGED, Boolean.TRUE);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos) ? Blocks.WATER.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, ATTACHED, WATERLOGGED);
    }



    @Override
    public void calculateState(Level world, BlockPos pos, BlockState state, boolean beingRemoved, boolean bl, int i, @Nullable BlockState blockState) {
        Direction direction = state.getValue(TripWireHookBlock.FACING);
        boolean bl2 = state.getValue(ATTACHED);
        boolean bl3 = state.getValue(POWERED);
        boolean bl4 = !beingRemoved;
        boolean bl5 = false;
        int j = 0;
        BlockState[] blockStates = new BlockState[42];

        BlockPos blockPos2;
        for(int k = 1; k < 42; ++k) {
            blockPos2 = pos.relative(direction, k);
            BlockState blockState2 = world.getBlockState(blockPos2);
            if (blockState2.is(this)) {
                if (blockState2.getValue(TripWireHookBlock.FACING) == direction.getOpposite()) {
                    j = k;
                }
                break;
            }

            if (!blockState2.is(this) && k != i) {
                blockStates[k] = null;
                bl4 = false;
            } else {
                if (k == i) {
                    blockState2 = MoreObjects.firstNonNull(blockState, blockState2);
                }

                boolean bl6 = !(Boolean)blockState2.getValue(TripWireBlock.DISARMED);
                boolean bl7 = blockState2.getValue(TripWireBlock.POWERED);
                bl5 |= bl6 && bl7;
                blockStates[k] = blockState2;
                if (k == i) {
                    world.scheduleTick(pos, this, 10);
                    bl4 &= bl6;
                }
            }
        }

        bl4 &= j > 1;
        bl5 &= bl4;
        BlockState blockState3 = this.defaultBlockState().setValue(ATTACHED, bl4).setValue(POWERED, bl5);
        if (j > 0) {
            blockPos2 = pos.relative(direction, j);
            Direction direction2 = direction.getOpposite();
            world.setBlock(blockPos2, blockState3.setValue(TripWireHookBlock.FACING, direction2), Block.UPDATE_ALL);
            this.notifyNeighbors(world, blockPos2, direction2);
            this.playSound(world, blockPos2, bl4, bl5, bl2, bl3);
        }

        this.playSound(world, pos, bl4, bl5, bl2, bl3);
        if (!beingRemoved) {
            world.setBlock(pos, blockState3.setValue(TripWireHookBlock.FACING, direction), Block.UPDATE_ALL);
            if (bl) {
                this.notifyNeighbors(world, pos, direction);
            }
        }

        if (bl2 != bl4) {
            for(int l = 1; l < j; ++l) {
                BlockPos blockPos3 = pos.relative(direction, l);
                BlockState blockState4 = blockStates[l];
                if (blockState4 != null) {
                    world.setBlock(blockPos3, blockState4.setValue(ATTACHED, bl4), Block.UPDATE_ALL);
                    if (!world.getBlockState(blockPos3).isAir()) {
                    }
                }
            }
        }
    }

    private void playSound(Level world, BlockPos pos, boolean attached, boolean on, boolean detached, boolean off) {
        if (on && !off) {
            world.playSound(null, pos, SoundEvents.TRIPWIRE_CLICK_ON, SoundSource.BLOCKS, 0.4F, 0.6F);
            world.gameEvent(GameEvent.BLOCK_PRESS, pos);
        } else if (!on && off) {
            world.playSound(null, pos, SoundEvents.TRIPWIRE_CLICK_OFF, SoundSource.BLOCKS, 0.4F, 0.5F);
            world.gameEvent(GameEvent.BLOCK_UNPRESS, pos);
        } else if (attached && !detached) {
            world.playSound(null, pos, SoundEvents.TRIPWIRE_ATTACH, SoundSource.BLOCKS, 0.4F, 0.7F);
            world.gameEvent(GameEvent.BLOCK_ATTACH, pos);
        } else if (!attached && detached) {
            world.playSound(null, pos, SoundEvents.TRIPWIRE_DETACH, SoundSource.BLOCKS, 0.4F, 1.2F / (world.random.nextFloat() * 0.2F + 0.9F));
            world.gameEvent(GameEvent.BLOCK_DETACH, pos);
        }
    }

    private void notifyNeighbors(Level world, BlockPos pos, Direction direction) {
        world.updateNeighborsAt(pos, this);
        world.updateNeighborsAt(pos.relative(direction.getOpposite()), this);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (world.getFluidState(pos).is(FluidTags.WATER)) {
            Direction direction = state.getValue(FACING);
            BlockPos blockPos = pos.relative(direction.getOpposite());
            BlockState blockState = world.getBlockState(blockPos);
            return direction.getAxis().isHorizontal() && blockState.isFaceSturdy(world, blockPos, direction);
        } else {
            return false;
        }
    }
}
