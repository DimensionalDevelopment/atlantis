package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class UnderwaterFlowerAtlantis extends Feature<FeatureSpreadConfig> {

    private static final Block WATER = Blocks.WATER;

    public UnderwaterFlowerAtlantis(Codec<FeatureSpreadConfig> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, FeatureSpreadConfig config) {
        int i = 0;
        int j = config.func_242799_a().func_242259_a(rand);

        for(int k = 0; k < j; ++k) {
            int l = rand.nextInt(8) - rand.nextInt(8);
            int i1 = rand.nextInt(8) - rand.nextInt(8);
            int j1 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + l, pos.getZ() + i1);
            BlockPos blockpos = new BlockPos(pos.getX() + l, j1, pos.getZ() + i1);
            BlockState blockstate = BlockInit.UNDERWATER_FLOWER.get().getDefaultState();
            if (reader.getBlockState(blockpos).isIn(Blocks.WATER) && blockstate.isValidPosition(reader, blockpos)) {
                reader.setBlockState(blockpos, blockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
