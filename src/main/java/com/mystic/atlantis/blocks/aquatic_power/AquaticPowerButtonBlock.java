package com.mystic.atlantis.blocks.aquatic_power;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import static com.mystic.atlantis.blocks.aquatic_power.AquaticPowerTorchBlock.WATERLOGGED;

public class AquaticPowerButtonBlock extends ButtonBlock implements SimpleWaterloggedBlock {
	
    public AquaticPowerButtonBlock(Properties settings) {
        super(BlockSetType.OAK, 30, settings); //TODO: Custom BlockSetType
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
