package com.mystic.atlantis.configfeature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import com.mojang.serialization.Codec;

import com.mystic.atlantis.init.BlockInit;

public class UnderwaterFlowerAtlantis extends Feature<CountConfig> {

    private static final Block WATER = Blocks.WATER;

    public UnderwaterFlowerAtlantis(Codec<CountConfig> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }

    public boolean generate(StructureWorldAccess reader, ChunkGenerator generator, Random rand, BlockPos pos, CountConfig config) {
        int i = 0;
        int j = config.getCount().getValue(rand);

        for(int k = 0; k < j; ++k) {
            int l = rand.nextInt(8) - rand.nextInt(8);
            int i1 = rand.nextInt(8) - rand.nextInt(8);
            int j1 = reader.getTopY(Heightmap.Type.OCEAN_FLOOR, pos.getX() + l, pos.getZ() + i1);
            BlockPos blockpos = new BlockPos(pos.getX() + l, j1, pos.getZ() + i1);
            BlockState blockstate = BlockInit.UNDERWATER_FLOWER.getDefaultState();
            if (reader.getBlockState(blockpos).isOf(Blocks.WATER) && blockstate.canPlaceAt(reader, blockpos)) {
                reader.setBlockState(blockpos, blockstate, 2);
                ++i;
            }

        }

        return i > 0;
    }
}
