package com.mystic.atlantis.items;

import com.mystic.atlantis.blocks.base.AtlanteanPortalFrame;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

public class OrbOfAtlantis extends DefaultItem {
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(BlockInit.ATLANTEAN_PORTAL_FRAME.get()) && !blockstate.getValue(AtlanteanPortalFrame.HAS_EYE)) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                BlockState blockstate1 = blockstate.setValue(AtlanteanPortalFrame.HAS_EYE, Boolean.valueOf(true));
                Block.pushEntitiesUp(blockstate, blockstate1, level, blockpos);
                level.setBlock(blockpos, blockstate1, 2);
                level.updateNeighbourForOutputSignal(blockpos, BlockInit.ATLANTEAN_PORTAL_FRAME.get());
                pContext.getItemInHand().shrink(1);
                level.playLocalSound(blockpos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = AtlanteanPortalFrame.getOrCreatePortalShape().find(level, blockpos);
                if (blockpattern$blockpatternmatch != null) {
                    BlockPos blockpos1 = blockpattern$blockpatternmatch.getFrontTopLeft().offset(-3, 0, -3);

                    for (int i = 0; i < 3; ++i) {
                        for (int j = 0; j < 3; ++j) {
                            level.setBlock(blockpos1.offset(i, 0, j), BlockInit.ATLANTEAN_PORTAL.get().defaultBlockState(), 2);
                        }
                    }

                    level.playLocalSound(blockpos, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }

                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.PASS;
        }
    }
}
