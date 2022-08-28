package com.mystic.atlantis.blocks;

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

import static com.mystic.atlantis.blocks.AtlanteanWoodDoors.WATERLOGGED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AGE_1;

public class AtlanteanFireMelonFruit extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final IntegerProperty AGE = AGE_1;
    public static final BooleanProperty SPIKED = BooleanProperty.create("spiked");

    protected static final VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(11.0D, 7.0D, 6.0D, 15.0D, 12.0D, 10.0D), Block.box(9.0D, 5.0D, 5.0D, 15.0D, 12.0D, 11.0D), Block.box(7.0D, 3.0D, 4.0D, 15.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(1.0D, 7.0D, 6.0D, 5.0D, 12.0D, 10.0D), Block.box(1.0D, 5.0D, 5.0D, 7.0D, 12.0D, 11.0D), Block.box(1.0D, 3.0D, 4.0D, 9.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 1.0D, 10.0D, 12.0D, 5.0D), Block.box(5.0D, 5.0D, 1.0D, 11.0D, 12.0D, 7.0D), Block.box(4.0D, 3.0D, 1.0D, 12.0D, 12.0D, 9.0D)};
    protected static final VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 11.0D, 10.0D, 12.0D, 15.0D), Block.box(5.0D, 5.0D, 9.0D, 11.0D, 12.0D, 15.0D), Block.box(4.0D, 3.0D, 7.0D, 12.0D, 12.0D, 15.0D)};

    public AtlanteanFireMelonFruit(Properties arg) {
        super(arg);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0).setValue(WATERLOGGED, Boolean.TRUE));
    }

    public HolderSet<Block> getAir(){
        Holder<Block> airHolderSet = Holder.direct(Blocks.AIR);
        return HolderSet.direct(airHolderSet);
    }

    public boolean OnlyWater(LevelReader worldReader, BlockPos pos, BlockState state) {
        return worldReader.isWaterAt(pos);
    }

    @Override
    public boolean canSurvive(BlockState arg, LevelReader arg2, BlockPos arg3) {
        BlockState state = arg2.getBlockState(arg3.relative(arg.getValue(FACING)));
        if(OnlyWater(arg2, arg3, arg)){
            return state.is(BlockInit.ATLANTEAN_FIRE_MELON_STEM.get());
        }else{
            return false;
        }
    }

    @Override
    public VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        int i = arg.getValue(AGE);
        switch(arg.getValue(FACING)) {
            case SOUTH:
                return SOUTH_AABB[i];
            case NORTH:
            default:
                return NORTH_AABB[i];
            case WEST:
                return WEST_AABB[i];
            case EAST:
                return EAST_AABB[i];
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext arg) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = arg.getLevel();
        BlockPos blockpos = arg.getClickedPos();
        Direction[] var5 = arg.getNearestLookingDirections();

        for (Direction direction : var5) {
            if (direction.getAxis().isHorizontal()) {
                blockstate = blockstate.setValue(FACING, direction);
                if (blockstate.canSurvive(levelreader, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState arg, Direction arg2, BlockState arg3, LevelAccessor arg4, BlockPos arg5, BlockPos arg6) {
        return arg2 == arg.getValue(FACING) && !arg.canSurvive(arg4, arg5) ? Blocks.WATER.defaultBlockState() : super.updateShape(arg, arg2, arg3, arg4, arg5, arg6);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter arg, BlockPos arg2, BlockState arg3, boolean bl) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level arg, RandomSource random, BlockPos arg2, BlockState arg3) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel arg, RandomSource random, BlockPos arg2, BlockState arg3) {
        if(arg3.getValue(AGE) == 0) {
            arg.setBlock(arg2, BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get().withPropertiesOf(arg3), 4);
        } else {
            arg.setBlock(arg2, arg3.setValue(AGE, arg3.getValue(AGE)), 0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(FACING, AGE ,WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState arg, BlockGetter arg2, BlockPos arg3, PathComputationType arg4) {
        return false;
    }
}
