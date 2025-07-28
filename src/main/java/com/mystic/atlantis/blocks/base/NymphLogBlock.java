package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class NymphLogBlock extends RotatedPillarBlock {

    public NymphLogBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .strength(2.0F));

    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.AXE_STRIP)) {
            return BlockInit.STRIPPED_NYMPH_LOG.get().defaultBlockState();
        }
        return state;
    }
}
