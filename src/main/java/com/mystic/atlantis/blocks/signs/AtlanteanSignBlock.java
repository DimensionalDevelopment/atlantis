package com.mystic.atlantis.blocks.signs;

import static net.minecraft.world.level.block.WallSignBlock.FACING;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AtlanteanSignBlock extends StandingSignBlock {
	
    public AtlanteanSignBlock(Properties settings, WoodType type) {
        super(type, settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ROTATION ,WATERLOGGED);
    }
}
