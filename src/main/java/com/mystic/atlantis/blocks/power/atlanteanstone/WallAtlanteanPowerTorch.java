package com.mystic.atlantis.blocks.power.atlanteanstone;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WallAtlanteanPowerTorch extends AtlanteanPowerTorch {

    private static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final Property<Boolean> WATERLOGGED = AtlanteanPowerTorch.WATERLOGGED;
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap((Map) ImmutableMap.of(
            Direction.NORTH, Block.box(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D),
            Direction.SOUTH, Block.box(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D),
            Direction.WEST, Block.box(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D),
            Direction.EAST, Block.box(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

    public WallAtlanteanPowerTorch(Properties settings) {
        super(settings.dropsLike(BlockInit.ATLANTEAN_POWER_TORCH.get()).noCollission().instabreak().lightLevel(level -> 7).sound(SoundType.WOOD));

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, true).setValue(WATERLOGGED, true));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.getValue(FACING) && !this.canSurvive(state, world, pos) ? Blocks.WATER.defaultBlockState() : state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (world.getFluidState(pos).is(FluidTags.WATER)) {
            return Blocks.WALL_TORCH.canSurvive(state, world, pos);
        } else {
            return false;
        }
    }

    @Override
    protected boolean hasNeighborSignal(Level world, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING).getOpposite();
        return world.hasSignal(pos.relative(direction), direction);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(LIT) && state.getValue(FACING) != direction ? 15 : 0;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getBoundingShape(state);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState();
        LevelReader worldView = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        Direction[] directions = ctx.getNearestLookingDirections();
        Direction[] var6 = directions;
        int var7 = directions.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            Direction direction = var6[var8];
            if (direction.getAxis().isHorizontal()) {
                Direction direction2 = direction.getOpposite();
                blockState = blockState.setValue(FACING, direction2).setValue(WATERLOGGED, true);
                if (worldView.getFluidState(blockPos).is(FluidTags.WATER)) {
                    if (this.canSurvive( blockState, worldView, blockPos )) {
                        return blockState;
                    }
                }
                return blockState;
            }
        }
        return null;
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, WATERLOGGED);
    }
}
