package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static com.mystic.atlantis.blocks.base.AtlanteanWoodDoorBlock.WATERLOGGED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AGE_1;

public class AtlanteanFireMelonFruitBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final IntegerProperty AGE = AGE_1;
    public static final BooleanProperty SPIKED = BooleanProperty.create("spiked");
    protected static final VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(11.0D, 7.0D, 6.0D, 15.0D, 12.0D, 10.0D), Block.box(9.0D, 5.0D, 5.0D, 15.0D, 12.0D, 11.0D), Block.box(7.0D, 3.0D, 4.0D, 15.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(1.0D, 7.0D, 6.0D, 5.0D, 12.0D, 10.0D), Block.box(1.0D, 5.0D, 5.0D, 7.0D, 12.0D, 11.0D), Block.box(1.0D, 3.0D, 4.0D, 9.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 1.0D, 10.0D, 12.0D, 5.0D), Block.box(5.0D, 5.0D, 1.0D, 11.0D, 12.0D, 7.0D), Block.box(4.0D, 3.0D, 1.0D, 12.0D, 12.0D, 9.0D)};
    protected static final VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 11.0D, 10.0D, 12.0D, 15.0D), Block.box(5.0D, 5.0D, 9.0D, 11.0D, 12.0D, 15.0D), Block.box(4.0D, 3.0D, 7.0D, 12.0D, 12.0D, 15.0D)};

    public AtlanteanFireMelonFruitBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0).setValue(WATERLOGGED, Boolean.TRUE));
    }

    public HolderSet<Block> getAir(){
        Holder<Block> airHolderSet = Holder.direct(Blocks.AIR);
        return HolderSet.direct(airHolderSet);
    }

    public boolean isWaterAt(LevelReader reader, BlockPos targetPos) {
        return reader.isWaterAt(targetPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos targetPos) {
        BlockState targetState = reader.getBlockState(targetPos.relative(state.getValue(FACING)));
        if(isWaterAt(reader, targetPos)){
            return targetState.is(BlockInit.ATLANTEAN_FIRE_MELON_STEM.get());
        } else {
            return false;
        }
    }

    @Override
    public VoxelShape getShape(BlockState targetState, BlockGetter getter, BlockPos targetPos, CollisionContext context) {
        int age = targetState.getValue(AGE);
        switch(targetState.getValue(FACING)) {
            case SOUTH:
                return SOUTH_AABB[age];
            case NORTH:
            default:
                return NORTH_AABB[age];
            case WEST:
                return WEST_AABB[age];
            case EAST:
                return EAST_AABB[age];
        }
    }
    
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState targetState = this.defaultBlockState();
        LevelReader reader = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction[] lookingDirections = context.getNearestLookingDirections();

        for (Direction direction : lookingDirections) {
            if (direction.getAxis().isHorizontal()) {
                targetState = targetState.setValue(FACING, direction);
                if (targetState.canSurvive(reader, clickedPos)) {
                    return targetState;
                }
            }
        }

        return null;
    }

    @Override
    public FluidState getFluidState(BlockState targetState) {
        return targetState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(targetState);
    }

    @Override
    public BlockState updateShape(BlockState targetState, Direction targetDir, BlockState neighbourState, LevelAccessor accessor, BlockPos targetPos, BlockPos neighbourPos) {
        return targetDir == targetState.getValue(FACING) && !targetState.canSurvive(accessor, targetPos) ? Blocks.WATER.defaultBlockState() : super.updateShape(targetState, targetDir, neighbourState, accessor, targetPos, neighbourPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos targetPos, BlockState targetState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos targetPos, BlockState targetState) {
        if(targetState.getValue(AGE) == 0) {
            level.setBlock(targetPos, BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get().withPropertiesOf(targetState), 4);
        } else {
            level.setBlock(targetPos, targetState.setValue(AGE, targetState.getValue(AGE)), 0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE ,WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState targetState, BlockGetter getter, BlockPos targetPos, PathComputationType type) {
        return false;
    }
}
