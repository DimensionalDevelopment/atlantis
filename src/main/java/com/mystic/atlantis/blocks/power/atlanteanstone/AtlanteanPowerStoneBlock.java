package com.mystic.atlantis.blocks.power.atlanteanstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class AtlanteanPowerStoneBlock extends PoweredBlock {

    public AtlanteanPowerStoneBlock(Properties settings) {
        super(settings
                .sound(SoundType.STONE)
                .mapColor(MapColor.COLOR_BLUE));
    }

    @Override
    public int getSignal(BlockState targetState, BlockGetter getter, BlockPos targetPos, Direction curDir) {
        if(isSubmergedInWater(getter, targetPos)){
            return 15;
        } else {
            return 0;
        }
    }

    @Override
    public void neighborChanged(BlockState targetState, Level level, BlockPos targetPos, Block targetBlock, BlockPos fromPos, boolean notify) {
        if (!level.isClientSide) {
            getSignal(targetState, level, targetPos, Direction.UP);
            super.neighborChanged(targetState, level, targetPos, targetBlock, fromPos, true);
        }
    }

    public boolean isSubmergedInWater(BlockGetter getter, BlockPos targetPos){
        return getter.getBlockState(targetPos.above()).is(Blocks.WATER);
    }

}
