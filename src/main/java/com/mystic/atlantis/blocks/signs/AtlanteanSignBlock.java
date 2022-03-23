package com.mystic.atlantis.blocks.signs;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import static net.minecraft.world.level.block.WallSignBlock.FACING;

public class AtlanteanSignBlock extends SignBlock {
    public AtlanteanSignBlock(Properties arg, WoodType arg2) {
        super(arg, arg2);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }
}
