package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class AtlanteanLogBlock extends RotatedPillarBlock {

    public AtlanteanLogBlock(Properties settings) {
        super(settings
                .sound(SoundType.WOOD)
                .strength(2.0F));

    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction.equals(ToolActions.AXE_STRIP)) {
            return BlockInit.STRIPPED_ATLANTEAN_LOG.get().defaultBlockState();
        }
        return state;
    }
}
