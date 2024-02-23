package com.mystic.atlantis.blocks.base;

import com.mojang.serialization.MapCodec;
import com.mystic.atlantis.inventory.LinguisticMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class LinguisticBlock extends HorizontalDirectionalBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("container.linguistic");

    public LinguisticBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(LinguisticBlock::new);
    }

    @Override
    public InteractionResult use(BlockState targetState, Level level, BlockPos targetPos, Player player, InteractionHand curHand, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(targetState.getMenuProvider(level, targetPos));
            player.awardStat(Stats.INTERACT_WITH_LOOM);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public MenuProvider getMenuProvider(BlockState targetState, Level arg2, BlockPos arg3) {
        return new SimpleMenuProvider((id, inventory, accessLevel) -> new LinguisticMenu(id, inventory, ContainerLevelAccess.create(arg2, arg3)), CONTAINER_TITLE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
