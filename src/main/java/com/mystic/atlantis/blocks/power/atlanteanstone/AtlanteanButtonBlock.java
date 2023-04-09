package com.mystic.atlantis.blocks.power.atlanteanstone;

import static com.mystic.atlantis.blocks.power.atlanteanstone.AtlanteanPowerTorchBlock.WATERLOGGED;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class AtlanteanButtonBlock extends WoodButtonBlock implements SimpleWaterloggedBlock {
	
    public AtlanteanButtonBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected SoundEvent getSound(boolean isOn) {
        return isOn ? SoundEvents.WOODEN_BUTTON_CLICK_ON : SoundEvents.WOODEN_BUTTON_CLICK_OFF;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, FACE ,WATERLOGGED);
    }
}
