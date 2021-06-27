package com.mystic.atlantis.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class AtlanteanSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public AtlanteanSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int minSurfaceLevel,  long seed, TernarySurfaceConfig config) {
        SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, minSurfaceLevel, seed, config);

        int xpos = x & 15;
        int zpos = z & 15;

        // Adds underwater surface blocks that default surface builder cant do.
        for (int ypos = startHeight; ypos >= minSurfaceLevel; --ypos) {
            BlockPos blockpos = new BlockPos(xpos, ypos, zpos);
            BlockState currentBlockState = chunk.getBlockState(blockpos);

            if (ypos >= 300) {
                chunk.setBlockState(blockpos, Blocks.WATER.getDefaultState(), false); //TODO Change to jetstream water in the future!!!
            }

            if (currentBlockState == config.getUnderwaterMaterial()) {
                if (ypos >= 40 && ypos <= 45) {
                    chunk.setBlockState(blockpos, Blocks.WATER.getDefaultState(), false); //TODO Change to dense water in the future!!!
                }
            }

            if (ypos >= 30 && ypos <= 32){
                chunk.setBlockState(blockpos, Blocks.SANDSTONE.getDefaultState(), false); //TODO Change to algae growth block!!!
            }
        }
    }
}
