package com.mystic.atlantis.blocks.power;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class AtlanteanPowerStone extends PoweredBlock {

    public AtlanteanPowerStone(Properties settings) {
        super(settings
                .sound(SoundType.STONE)
                .color(MaterialColor.COLOR_BLUE));
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if(isSubmerged(world, pos)){
            return 15;
        } else {
            return 0;
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClientSide) {
            getSignal(state, world, pos, Direction.UP);
            super.neighborChanged(state, world, pos, block, fromPos, true);
        }
    }

    public boolean isSubmerged(BlockGetter world, BlockPos pos){
        return world.getBlockState(pos.above()).getMaterial() == Material.WATER;
    }

}
