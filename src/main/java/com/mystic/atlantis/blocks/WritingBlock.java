package com.mystic.atlantis.blocks;

import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.inventory.WritingMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WritingBlock extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("container.writing");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape SHAPE = Shapes.block();

    public WritingBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState targetState, Level level, BlockPos targetPos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        player.openMenu(targetState.getMenuProvider(level, targetPos));
        player.awardStat(Stats.INTERACT_WITH_STONECUTTER);
        return InteractionResult.CONSUME;
    }

    @Override
    @Nullable
    public MenuProvider getMenuProvider(BlockState targetState, Level level, BlockPos targetPos) {
        return new SimpleMenuProvider((id, inventory, accessLevel) -> new WritingMenu(id, inventory, ContainerLevelAccess.create(level, targetPos)), CONTAINER_TITLE);
    }

    @Override
    public VoxelShape getShape(BlockState targetState, BlockGetter getter, BlockPos targetPos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState targetState) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState targetState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState targetState, Rotation currentRot) {
        return targetState.setValue(FACING, currentRot.rotate(targetState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState targetState, Mirror mirror) {
        return targetState.rotate(mirror.getRotation(targetState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean isPathfindable(BlockState targetState, BlockGetter level, BlockPos targetPos, PathComputationType type) {
        return false;
    }
}

