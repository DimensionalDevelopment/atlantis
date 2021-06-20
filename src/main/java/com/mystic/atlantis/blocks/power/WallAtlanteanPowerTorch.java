package com.mystic.atlantis.blocks.power;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WallAtlanteanPowerTorch extends AtlanteanPowerTorch {

    private static final Property<Direction> FACING = HorizontalFacingBlock.FACING;
    private static final Property<Boolean> WATERLOGGED = AtlanteanPowerTorch.WATERLOGGED;
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap((Map) ImmutableMap.of(
            Direction.NORTH, Block.createCuboidShape(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D),
            Direction.SOUTH, Block.createCuboidShape(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D),
            Direction.WEST, Block.createCuboidShape(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D),
            Direction.EAST, Block.createCuboidShape(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

    public WallAtlanteanPowerTorch(Settings settings) {
        super(settings.dropsLike(BlockInit.ATLANTEAN_POWER_TORCH));
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LIT, true));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.WATER.getDefaultState() : state;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (OnlyWater(world, pos, state)) {
            Direction direction = state.get(FACING);
            BlockPos blockPos = pos.offset(direction.getOpposite());
            BlockState blockState = world.getBlockState(blockPos);
            return blockState.isSideSolidFullSquare(world, blockPos, direction);
        } else {
            return false;
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getBoundingShape(state);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction[] directions = ctx.getPlacementDirections();
        Direction[] var6 = directions;
        int var7 = directions.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            Direction direction = var6[var8];
            if (direction.getAxis().isHorizontal()) {
                Direction direction2 = direction.getOpposite();
                blockState = blockState.with(FACING, direction2);
                if (blockState.canPlaceAt(worldView, blockPos)) {
                    return blockState;
                }
            }
        }
        return null;
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.get(FACING));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, WATERLOGGED);
    }
}
