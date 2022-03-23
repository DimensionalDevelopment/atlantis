package com.mystic.atlantis.blocks;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class AtlanteanFireMelonFruit extends HorizontalDirectionalBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final BooleanProperty SPIKED = BooleanProperty.create("spiked");

    protected static final VoxelShape[] EAST_AABB = new VoxelShape[]{Block.box(11.0D, 7.0D, 6.0D, 15.0D, 12.0D, 10.0D), Block.box(9.0D, 5.0D, 5.0D, 15.0D, 12.0D, 11.0D), Block.box(7.0D, 3.0D, 4.0D, 15.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] WEST_AABB = new VoxelShape[]{Block.box(1.0D, 7.0D, 6.0D, 5.0D, 12.0D, 10.0D), Block.box(1.0D, 5.0D, 5.0D, 7.0D, 12.0D, 11.0D), Block.box(1.0D, 3.0D, 4.0D, 9.0D, 12.0D, 12.0D)};
    protected static final VoxelShape[] NORTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 1.0D, 10.0D, 12.0D, 5.0D), Block.box(5.0D, 5.0D, 1.0D, 11.0D, 12.0D, 7.0D), Block.box(4.0D, 3.0D, 1.0D, 12.0D, 12.0D, 9.0D)};
    protected static final VoxelShape[] SOUTH_AABB = new VoxelShape[]{Block.box(6.0D, 7.0D, 11.0D, 10.0D, 12.0D, 15.0D), Block.box(5.0D, 5.0D, 9.0D, 11.0D, 12.0D, 15.0D), Block.box(4.0D, 3.0D, 7.0D, 12.0D, 12.0D, 15.0D)};

    public AtlanteanFireMelonFruit(Properties arg) {
        super(arg);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0));
    }

    public boolean isRandomlyTicking(BlockState arg) {
        return arg.getValue(AGE) < 3;
    }

    public void randomTick(BlockState blockState, ServerLevel level, BlockPos blockPos, Random random) {
        int i = blockState.getValue(AGE);
        if (i < 3 && ForgeHooks.onCropsGrowPre(level, blockPos, blockState, level.random.nextInt(5) == 0)) {
            level.setBlock(blockPos, BlockInit.ATLANTEAN_FIRE_MELON_FRUIT.get().defaultBlockState(), 2);
            ForgeHooks.onCropsGrowPost(level, blockPos, blockState);
        } else {
            level.setBlock(blockPos, BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get().defaultBlockState(), 2);
        }
    }

    public boolean canSurvive(BlockState arg, LevelReader arg2, BlockPos arg3) {
        BlockState blockstate = arg2.getBlockState(arg3.relative(arg.getValue(FACING)));
        return blockstate.is(BlockTags.JUNGLE_LOGS);
    }

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

    public BlockState updateShape(BlockState arg, Direction arg2, BlockState arg3, LevelAccessor arg4, BlockPos arg5, BlockPos arg6) {
        return arg2 == arg.getValue(FACING) && !arg.canSurvive(arg4, arg5) ? Blocks.AIR.defaultBlockState() : super.updateShape(arg, arg2, arg3, arg4, arg5, arg6);
    }

    public boolean isValidBonemealTarget(BlockGetter arg, BlockPos arg2, BlockState arg3, boolean bl) {
        return true;
    }

    public boolean isBonemealSuccess(Level arg, Random random, BlockPos arg2, BlockState arg3) {
        return true;
    }

    public void performBonemeal(ServerLevel arg, Random random, BlockPos arg2, BlockState arg3) {
        if(arg3.getValue(AGE) == 1) {
            arg.setBlock(arg2, BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get().defaultBlockState(), 2);
        } else {
            arg.setBlock(arg2, arg3.setValue(AGE, arg3.getValue(AGE) + 1), 2);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(FACING, AGE);
    }

    public boolean isPathfindable(BlockState arg, BlockGetter arg2, BlockPos arg3, PathComputationType arg4) {
        return false;
    }
}
