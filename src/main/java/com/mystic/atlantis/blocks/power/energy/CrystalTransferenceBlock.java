package com.mystic.atlantis.blocks.power.energy;

import com.mystic.atlantis.blocks.blockentities.energy.CrystalStorage;
import com.mystic.atlantis.blocks.blockentities.energy.CrystalTransference;
import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystalTransferenceBlock extends BaseEntityBlock {
    public CrystalTransferenceBlock(Properties properties) {
        super(properties.strength(4.5F)
                .mapColor(MapColor.COLOR_LIGHT_BLUE)
                .lightLevel((state) -> 5)
                .sound(SoundType.AMETHYST)
                .randomTicks());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrystalTransference(pos, state);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public int getSignal(BlockState targetState, BlockGetter getter, BlockPos targetPos, Direction curDir) {
        if (getter.getBlockEntity(targetPos) instanceof CrystalTransference crystalTransference) {
            if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 300) {
                return 15;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 280) {
                return 14;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 260) {
                return 13;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 240) {
                return 12;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 220) {
                return 11;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 200) {
                return 10;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 180) {
                return 9;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 160) {
                return 8;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 140) {
                return 7;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 120) {
                return 6;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 100) {
                return 5;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 80) {
                return 4;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 60) {
                return 3;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 40) {
                return 2;
            } else if (crystalTransference.ENERGY_STORAGE.getEnergyStored() >= 20) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void neighborChanged(BlockState targetState, Level level, BlockPos targetPos, Block targetBlock, BlockPos fromPos, boolean notify) {
        if (!level.isClientSide) {
            getSignal(targetState, level, targetPos, Direction.NORTH);
            super.neighborChanged(targetState, level, targetPos, targetBlock, fromPos, true);
        }
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState pState, LootParams.Builder pParams) {
        BlockEntity blockentity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        ItemStack stack = new ItemStack(BlockItem.byBlock(this));
        if (blockentity instanceof CrystalTransference crystalTransference) {
            stack = new ItemStack(this, 1);
            BlockItem.setBlockEntityData(stack, TileEntityInit.CRYSTAL_TRANSFERENCE.get(), crystalTransference.saveWithoutMetadata());
        }
        return List.of(stack);
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public <A extends BlockEntity> BlockEntityTicker getTicker(Level level, BlockState state, BlockEntityType<A> type) {
        return createTickerHelper(type, TileEntityInit.CRYSTAL_TRANSFERENCE.get(), CrystalTransference::tick);
    }
}
