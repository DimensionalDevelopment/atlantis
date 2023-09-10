package com.mystic.atlantis.blocks.power.energy;

import com.mystic.atlantis.blocks.blockentities.energy.CrystalStorage;
import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.ShulkerBoxBlock.CONTENTS;

public class CrystalGeneratorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CrystalGeneratorBlock(Properties settings) {
        super(settings
                .strength(3.5F)
                .mapColor(MapColor.COLOR_LIGHT_BLUE)
                .lightLevel((state) -> 5)
                .sound(SoundType.AMETHYST));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /* BLOCK ENTITY */

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrystalGenerator) {
                ((CrystalGenerator) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState pState, LootParams.Builder pParams) {
        BlockEntity blockentity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        List<ItemStack> list = List.of();
        if (blockentity instanceof CrystalGenerator crystalGenerator) {
            pParams = pParams.withDynamicDrop(CONTENTS, (p_56219_) -> {
                for (int i = 0; i < crystalGenerator.itemHandler.getSlots(); ++i) {
                    p_56219_.accept(crystalGenerator.itemHandler.getStackInSlot(i));
                }
            });
            ItemStack stack = new ItemStack(this, 1);
            BlockItem.setBlockEntityData(stack, TileEntityInit.CRYSTAL_GENERATOR.get(), crystalGenerator.saveWithoutMetadata());
            list = super.getDrops(pState, pParams);
            list.listIterator().add(stack);
        }
        return list;
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof CrystalGenerator) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (CrystalGenerator)entity, pPos);
            } else {
                throw new IllegalStateException("Crystal Generator's Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrystalGenerator(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, TileEntityInit.CRYSTAL_GENERATOR.get(), CrystalGenerator::tick);
    }
}
