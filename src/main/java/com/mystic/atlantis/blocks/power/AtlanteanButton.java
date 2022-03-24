package com.mystic.atlantis.blocks.power;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static com.mystic.atlantis.blocks.power.AtlanteanPowerTorch.WATERLOGGED;

public class AtlanteanButton extends WoodButtonBlock implements SimpleWaterloggedBlock {
    public AtlanteanButton(Properties arg) {
        super(arg);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected SoundEvent getSound(boolean bl) {
        return bl ? SoundEvents.WOODEN_BUTTON_CLICK_ON : SoundEvents.WOODEN_BUTTON_CLICK_OFF;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(FACING, POWERED, FACE ,WATERLOGGED);
    }
}
