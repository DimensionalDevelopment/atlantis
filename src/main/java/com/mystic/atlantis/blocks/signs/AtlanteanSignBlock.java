package com.mystic.atlantis.blocks.signs;

import static net.minecraft.world.level.block.WallSignBlock.FACING;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AtlanteanSignBlock extends StandingSignBlock {
    public AtlanteanSignBlock(Properties arg, WoodType arg2) {
        super(arg, arg2);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(FACING, ROTATION ,WATERLOGGED);
    }
}
