package com.mystic.atlantis.blocks;

import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;

public class AncientWoodDoorBlock extends DoorBlock implements SimpleWaterloggedBlock {
    public static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;

    public AncientWoodDoorBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .noOcclusion()
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(HINGE, DoorHingeSide.LEFT).setValue(POWERED, false).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean isPathfindable(BlockState targetState, BlockGetter world, BlockPos targetPos, PathComputationType type) {
        return switch (type) {
            case LAND, AIR -> targetState.getValue(OPEN);
            case WATER -> targetState.getValue(WATERLOGGED);
        };
    }

    @Override
    public InteractionResult use(BlockState targetState, Level world, BlockPos targetPos, Player player, InteractionHand hand, BlockHitResult hit) {
        InteractionResult result = super.use(targetState, world, targetPos, player, hand, hit);
        if (result.consumesAction()) {
            world.scheduleTick(targetPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return result;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos targetPos, BlockState targetState, LivingEntity placer, ItemStack stack) {
        BlockPos above = targetPos.above();
        world.setBlock(above, targetState.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, world.getFluidState(above).getType() == Fluids.WATER), 3);
    }

    @Override
    public void neighborChanged(BlockState targetState, Level level, BlockPos targetPos, Block targetBlock, BlockPos fromPos, boolean notify) {
        boolean neighbourSignal = level.hasNeighborSignal(targetPos) || level.hasNeighborSignal(targetPos.relative(targetState.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        super.neighborChanged(targetState, level, targetPos, targetBlock, fromPos, notify);
        if (neighbourSignal && !level.isClientSide && targetState.getValue(WATERLOGGED)) {
            level.scheduleTick(targetPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean isWater = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        BlockState state = super.getStateForPlacement(ctx);
        if (state == null) return null;
        if (isWater) return state.setValue(WATERLOGGED, true);
        return state;
    }

    @Override
    public BlockState updateShape(BlockState targetState, Direction direction, BlockState neighborState, LevelAccessor accessor, BlockPos targetPos, BlockPos neighborPos) {
        if (targetState.getValue(WATERLOGGED)) {
            accessor.scheduleTick(targetPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
        }

        BlockState newState = super.updateShape(targetState, direction, neighborState, accessor, targetPos, neighborPos);
        return newState;
    }

    @Override
    public FluidState getFluidState(BlockState targetState) {
        return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos targetPos, BlockState targetState, Player player) {
        DoubleBlockHalf doubleBlockHalf = targetState.getValue(HALF);
        
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos belowPos = targetPos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.is(targetState.getBlock()) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                level.setBlock(belowPos, level.getFluidState(belowPos).getType() == Fluids.WATER ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, belowPos, Block.getId(belowState));
            }
        }
        
        super.playerWillDestroy(level, targetPos, targetState, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING, OPEN, HINGE, POWERED ,WATERLOGGED);
    }
}
