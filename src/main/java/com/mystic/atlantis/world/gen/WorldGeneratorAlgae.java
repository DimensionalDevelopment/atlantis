package com.mystic.atlantis.world.gen;

import com.mystic.atlantis.blocks.base.plants.Algae;
import com.mystic.atlantis.init.ModBiomes;
import com.mystic.atlantis.init.ModBlocks;
import io.github.opencubicchunks.cubicchunks.core.CubicChunks;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGeneratorAlgae extends WorldGenerator
{
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (; position.getY() < CubicChunks.MAX_SUPPORTED_BLOCK_Y; position = position.up()) {
            if (isWaterBlock(worldIn, position)) {
                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings()) {
                    if (ModBlocks.ALGAE.canPlaceBlockOnSide(worldIn, position, enumfacing)) {
                        IBlockState iblockstate = ModBlocks.ALGAE.getDefaultState().withProperty(Algae.NORTH, enumfacing == EnumFacing.NORTH).withProperty(Algae.EAST, enumfacing == EnumFacing.EAST).withProperty(Algae.SOUTH, enumfacing == EnumFacing.SOUTH).withProperty(Algae.WEST, enumfacing == EnumFacing.WEST);
                        worldIn.setBlockState(position, iblockstate, 2);
                        break;
                    }
                }
            } else {
                position = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
            }
        }
        return true;
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
