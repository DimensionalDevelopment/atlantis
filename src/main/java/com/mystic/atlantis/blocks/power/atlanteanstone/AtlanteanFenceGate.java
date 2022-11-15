package com.mystic.atlantis.blocks.power.atlanteanstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.mystic.atlantis.blocks.power.atlanteanstone.AtlanteanPowerDust.WATERLOGGED;

public class AtlanteanFenceGate extends FenceGateBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty OPEN;
    public static final BooleanProperty POWERED;
    public static final BooleanProperty IN_WALL;
    protected static final VoxelShape Z_SHAPE;
    protected static final VoxelShape X_SHAPE;
    protected static final VoxelShape Z_SHAPE_LOW;
    protected static final VoxelShape X_SHAPE_LOW;
    protected static final VoxelShape Z_COLLISION_SHAPE;
    protected static final VoxelShape X_COLLISION_SHAPE;
    protected static final VoxelShape Z_OCCLUSION_SHAPE;
    protected static final VoxelShape X_OCCLUSION_SHAPE;
    protected static final VoxelShape Z_OCCLUSION_SHAPE_LOW;
    protected static final VoxelShape X_OCCLUSION_SHAPE_LOW;

    public AtlanteanFenceGate(Properties arg) {
        super(arg);
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false).setValue(POWERED, false).setValue(IN_WALL, false).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        if (arg.getValue(IN_WALL)) {
            return arg.getValue(FACING).getAxis() == Direction.Axis.X ? X_SHAPE_LOW : Z_SHAPE_LOW;
        } else {
            return arg.getValue(FACING).getAxis() == Direction.Axis.X ? X_SHAPE : Z_SHAPE;
        }
    }

    @Override
    public BlockState updateShape(BlockState arg, Direction arg2, BlockState arg3, LevelAccessor arg4, BlockPos arg5, BlockPos arg6) {
        Direction.Axis axis = arg2.getAxis();
        if (arg.getValue(FACING).getClockWise().getAxis() != axis) {
            return super.updateShape(arg, arg2, arg3, arg4, arg5, arg6);
        } else {
            boolean bl = this.isWall(arg3) || this.isWall(arg4.getBlockState(arg5.relative(arg2.getOpposite())));
            return arg.setValue(IN_WALL, bl);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        if (arg.getValue(OPEN)) {
            return Shapes.empty();
        } else {
            return arg.getValue(FACING).getAxis() == Direction.Axis.Z ? Z_COLLISION_SHAPE : X_COLLISION_SHAPE;
        }
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState arg, BlockGetter arg2, BlockPos arg3) {
        if (arg.getValue(IN_WALL)) {
            return arg.getValue(FACING).getAxis() == Direction.Axis.X ? X_OCCLUSION_SHAPE_LOW : Z_OCCLUSION_SHAPE_LOW;
        } else {
            return arg.getValue(FACING).getAxis() == Direction.Axis.X ? X_OCCLUSION_SHAPE : Z_OCCLUSION_SHAPE;
        }
    }

    @Override
    public boolean isPathfindable(BlockState arg, BlockGetter arg2, BlockPos arg3, PathComputationType arg4) {
        switch(arg4) {
            case LAND:
            case WATER:
                return arg.getValue(OPEN);
            case AIR:
                return false;
            default:
                return false;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext arg) {
        Level level = arg.getLevel();
        BlockPos blockPos = arg.getClickedPos();
        boolean bl = level.hasNeighborSignal(blockPos);
        Direction direction = arg.getHorizontalDirection();
        Direction.Axis axis = direction.getAxis();
        boolean bl2 = axis == Direction.Axis.Z && (this.isWall(level.getBlockState(blockPos.west())) || this.isWall(level.getBlockState(blockPos.east()))) || axis == Direction.Axis.X && (this.isWall(level.getBlockState(blockPos.north())) || this.isWall(level.getBlockState(blockPos.south())));
        return this.defaultBlockState().setValue(FACING, direction).setValue(OPEN, bl).setValue(POWERED, bl).setValue(IN_WALL, bl2);
    }

    private boolean isWall(BlockState arg) {
        return arg.is(BlockTags.WALLS);
    }

    @Override
    public InteractionResult use(BlockState arg, Level arg2, BlockPos arg3, Player arg4, InteractionHand arg5, BlockHitResult arg6) {
        if (arg.getValue(OPEN)) {
            arg = arg.setValue(OPEN, false);
            arg2.setBlock(arg3, arg, 10);
        } else {
            Direction direction = arg4.getDirection();
            if (arg.getValue(FACING) == direction.getOpposite()) {
                arg = arg.setValue(FACING, direction);
            }

            arg = arg.setValue(OPEN, true);
            arg2.setBlock(arg3, arg, 10);
        }

        boolean bl = arg.getValue(OPEN);
        arg2.levelEvent(arg4, bl ? 1008 : 1014, arg3, 0);
        arg2.gameEvent(arg4, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, arg3);
        return InteractionResult.sidedSuccess(arg2.isClientSide);
    }

    @Override
    public void neighborChanged(BlockState arg, Level arg2, BlockPos arg3, Block arg4, BlockPos arg5, boolean bl) {
        if (!arg2.isClientSide) {
            boolean bl2 = arg2.hasNeighborSignal(arg3);
            if (arg.getValue(POWERED) != bl2) {
                arg2.setBlock(arg3, arg.setValue(POWERED, bl2).setValue(OPEN, bl2), 2);
                if (arg.getValue(OPEN) != bl2) {
                    arg2.levelEvent(null, bl2 ? 1008 : 1014, arg3, 0);
                    arg2.gameEvent(null, bl2 ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, arg3);
                }
            }

        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(FACING, OPEN, POWERED, IN_WALL, WATERLOGGED);
    }

    public static boolean connectsToDirection(BlockState arg, Direction arg2) {
        return arg.getValue(FACING).getAxis() == arg2.getClockWise().getAxis();
    }

    static {
        OPEN = BlockStateProperties.OPEN;
        POWERED = BlockStateProperties.POWERED;
        IN_WALL = BlockStateProperties.IN_WALL;
        Z_SHAPE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
        X_SHAPE = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
        Z_SHAPE_LOW = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 13.0D, 10.0D);
        X_SHAPE_LOW = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 13.0D, 16.0D);
        Z_COLLISION_SHAPE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 24.0D, 10.0D);
        X_COLLISION_SHAPE = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 24.0D, 16.0D);
        Z_OCCLUSION_SHAPE = Shapes.or(Block.box(0.0D, 5.0D, 7.0D, 2.0D, 16.0D, 9.0D), Block.box(14.0D, 5.0D, 7.0D, 16.0D, 16.0D, 9.0D));
        X_OCCLUSION_SHAPE = Shapes.or(Block.box(7.0D, 5.0D, 0.0D, 9.0D, 16.0D, 2.0D), Block.box(7.0D, 5.0D, 14.0D, 9.0D, 16.0D, 16.0D));
        Z_OCCLUSION_SHAPE_LOW = Shapes.or(Block.box(0.0D, 2.0D, 7.0D, 2.0D, 13.0D, 9.0D), Block.box(14.0D, 2.0D, 7.0D, 16.0D, 13.0D, 9.0D));
        X_OCCLUSION_SHAPE_LOW = Shapes.or(Block.box(7.0D, 2.0D, 0.0D, 9.0D, 13.0D, 2.0D), Block.box(7.0D, 2.0D, 14.0D, 9.0D, 13.0D, 16.0D));
    }
}
