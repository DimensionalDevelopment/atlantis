package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class PalmLogBlock extends RotatedPillarBlock {

    public PalmLogBlock(BlockBehaviour.Properties properties) {
        super(properties
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops()
                .strength(2.0F));
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.AXE_STRIP)) {
            return BlockInit.STRIPPED_PALM_LOG.get().defaultBlockState();
        }
        return state;
    }
}
