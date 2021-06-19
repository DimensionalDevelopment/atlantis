package com.mystic.atlantis.blocks.power;

import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class AtlanteanPowerStone extends RedstoneBlock {
    public AtlanteanPowerStone(Settings settings) {
        super(settings
                .dropsLike(Blocks.REDSTONE_BLOCK)
                .sounds(BlockSoundGroup.STONE)
                .mapColor(MapColor.BLUE)
                .requiresTool());
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if(isSubmerged(world, pos)){
            return 15;
        } else {
            return 0;
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            getWeakRedstonePower(state, world, pos, Direction.UP);
            super.neighborUpdate(state, world, pos, block, fromPos, true);
        }
    }

    public boolean isSubmerged(BlockView world, BlockPos pos){
        return world.getBlockState(pos.up()).getMaterial() == Material.WATER;
    }
}
