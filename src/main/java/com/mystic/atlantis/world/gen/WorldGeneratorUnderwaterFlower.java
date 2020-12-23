package com.mystic.atlantis.world.gen;

import com.mystic.atlantis.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGeneratorUnderwaterFlower extends WorldGenerator
{

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        {
            for (int i = 0; i < 64; ++i) {
                BlockPos blockpos = position.add(rand.nextInt(18) - rand.nextInt(18), rand.nextInt(1), rand.nextInt(18) - rand.nextInt(18));

                if (isWaterBlock(worldIn, blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS || worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRAVEL || worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.SAND || worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.DIRT) {
                    worldIn.setBlockState(blockpos, ModBlocks.UNDERWATER_FLOWER.getDefaultState(), 2);
                }
            }

            return true;
        }
    }

    public boolean isWaterBlock(World worldIn, BlockPos pos)
    {
        return isWater(worldIn.getBlockState(pos), worldIn, pos);
    }

    public boolean isWater(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getMaterial() == Material.WATER;
    }
}
