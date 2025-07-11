package com.mystic.atlantis.blocks.ancient_cuprum;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CuprumBulbBlock extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public CuprumBulbBlock(BlockBehaviour.Properties p_308970_) {
        super(p_308970_);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)).setValue(POWERED, Boolean.valueOf(false)));
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (pOldState.getBlock() != pState.getBlock() && pLevel instanceof ServerLevel serverlevel) {
            this.checkAndFlip(pState, serverlevel, pPos);
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverlevel) {
            this.checkAndFlip(pState, serverlevel, pPos);
        }
    }

    public void checkAndFlip(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (flag != pState.getValue(POWERED)) {
            BlockState blockstate = pState;
            if (!pState.getValue(POWERED)) {
                blockstate = pState.cycle(LIT);
            }

            pLevel.setBlock(pPos, blockstate.setValue(POWERED, Boolean.valueOf(flag)), 3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT, POWERED);
    }

    /**
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#hasAnalogOutputSignal} whenever possible. Implementing/overriding is fine.
     */
    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    /**
     * Returns the analog signal this block emits. This is the signal a comparator can read from it.
     *
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getAnalogOutputSignal} whenever possible. Implementing/overriding is fine.
     */
    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).getValue(LIT) ? 15 : 0;
    }
}
