package com.mystic.atlantis.blocks.power.atlanteanstone;

import static com.mystic.atlantis.blocks.power.atlanteanstone.AtlanteanPowerDustBlock.WATERLOGGED;

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

public class AtlanteanFenceGateBlock extends FenceGateBlock implements SimpleWaterloggedBlock {
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

    public AtlanteanFenceGateBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false).setValue(POWERED, false).setValue(IN_WALL, false).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState targetState, BlockGetter getter, BlockPos targetPos, CollisionContext context) {
        if (targetState.getValue(IN_WALL)) {
            return targetState.getValue(FACING).getAxis() == Direction.Axis.X ? X_SHAPE_LOW : Z_SHAPE_LOW;
        } else {
            return targetState.getValue(FACING).getAxis() == Direction.Axis.X ? X_SHAPE : Z_SHAPE;
        }
    }

    @Override
    public BlockState updateShape(BlockState targetState, Direction curDir, BlockState neighbourState, LevelAccessor accessor, BlockPos targetPos, BlockPos neighbourPos) {
        Direction.Axis curAxis = curDir.getAxis();
        
        if (targetState.getValue(FACING).getClockWise().getAxis() != curAxis) {
            return super.updateShape(targetState, curDir, neighbourState, accessor, targetPos, neighbourPos);
        } else {
            boolean isNeighbourWall = this.isWall(neighbourState) || this.isWall(accessor.getBlockState(targetPos.relative(curDir.getOpposite())));
            return targetState.setValue(IN_WALL, isNeighbourWall);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState targetState, BlockGetter getter, BlockPos targetPos, CollisionContext context) {
        if (targetState.getValue(OPEN)) {
            return Shapes.empty();
        } else {
            return targetState.getValue(FACING).getAxis() == Direction.Axis.Z ? Z_COLLISION_SHAPE : X_COLLISION_SHAPE;
        }
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState targetState, BlockGetter getter, BlockPos targetPos) {
        if (targetState.getValue(IN_WALL)) {
            return targetState.getValue(FACING).getAxis() == Direction.Axis.X ? X_OCCLUSION_SHAPE_LOW : Z_OCCLUSION_SHAPE_LOW;
        } else {
            return targetState.getValue(FACING).getAxis() == Direction.Axis.X ? X_OCCLUSION_SHAPE : Z_OCCLUSION_SHAPE;
        }
    }

    @Override
    public boolean isPathfindable(BlockState targetState, BlockGetter getter, BlockPos targetPos, PathComputationType type) {
        switch(type) {
            case LAND:
            case WATER:
                return targetState.getValue(OPEN);
            case AIR:
                return false;
            default:
                return false;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        boolean hasNeighbourSignal = level.hasNeighborSignal(clickedPos);
        Direction curHorizontalDir = context.getHorizontalDirection();
        Direction.Axis curAxis = curHorizontalDir.getAxis();
        boolean isClickedStateWallOnZ = curAxis == Direction.Axis.Z && (this.isWall(level.getBlockState(clickedPos.west())) || this.isWall(level.getBlockState(clickedPos.east()))) || curAxis == Direction.Axis.X && (this.isWall(level.getBlockState(clickedPos.north())) || this.isWall(level.getBlockState(clickedPos.south())));
        return this.defaultBlockState().setValue(FACING, curHorizontalDir).setValue(OPEN, hasNeighbourSignal).setValue(POWERED, hasNeighbourSignal).setValue(IN_WALL, isClickedStateWallOnZ);
    }

    private boolean isWall(BlockState targetState) {
        return targetState.is(BlockTags.WALLS);
    }

    @Override
    public InteractionResult use(BlockState targetState, Level level, BlockPos targetPos, Player player, InteractionHand hand, BlockHitResult result) {
        if (targetState.getValue(OPEN)) {
            targetState = targetState.setValue(OPEN, false);
            level.setBlock(targetPos, targetState, 10);
        } else {
            Direction curPlayerFacingDir = player.getDirection();
            if (targetState.getValue(FACING) == curPlayerFacingDir.getOpposite()) {
                targetState = targetState.setValue(FACING, curPlayerFacingDir);
            }

            targetState = targetState.setValue(OPEN, true);
            level.setBlock(targetPos, targetState, 10);
        }

        boolean isOpen = targetState.getValue(OPEN);
        level.levelEvent(player, isOpen ? 1008 : 1014, targetPos, 0);
        level.gameEvent(player, isOpen ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, targetPos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void neighborChanged(BlockState targetState, Level level, BlockPos targetPos, Block targetBlock, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            boolean bl2 = level.hasNeighborSignal(targetPos);
            if (targetState.getValue(POWERED) != bl2) {
                level.setBlock(targetPos, targetState.setValue(POWERED, bl2).setValue(OPEN, bl2), 2);
                if (targetState.getValue(OPEN) != bl2) {
                    level.levelEvent(null, bl2 ? 1008 : 1014, targetPos, 0);
                    level.gameEvent(null, bl2 ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, targetPos);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, POWERED, IN_WALL, WATERLOGGED);
    }

    public static boolean connectsToDirection(BlockState targetState, Direction targetDir) {
        return targetState.getValue(FACING).getAxis() == targetDir.getClockWise().getAxis();
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
