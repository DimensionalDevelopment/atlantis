package com.mystic.atlantis.blocks.signs;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AtlanteanWallSign extends WallSignBlock {
    public AtlanteanWallSign(Properties arg, WoodType arg2) {
        super(arg, arg2);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }
}
