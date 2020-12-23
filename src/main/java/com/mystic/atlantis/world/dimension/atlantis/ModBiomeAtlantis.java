package com.mystic.atlantis.world.dimension.atlantis;

import io.github.opencubicchunks.cubicchunks.core.CubicChunks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public abstract class ModBiomeAtlantis extends Biome {

    public ModBiomeAtlantis(BiomeProperties biomeProperties) {
        super(biomeProperties);
        initSpawnList();
    }

    public abstract void initSpawnList();

    @Nonnull
    @Override
    public BiomeDecorator createBiomeDecorator() {
        return new DecoratorBaseAtlantis();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        if (worldIn.provider instanceof WorldProviderDimensionAtlantis) {
            generateCustomBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
        } else {
            generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void generateCustomBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        int i = worldIn.getSeaLevel();
        IBlockState iblockstate = this.topBlock;
        IBlockState iblockstate1 = this.fillerBlock;
        int j = -1;
        int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int l = x & 15;
        int i1 = z & 15;

        for (int j1 = CubicChunks.MAX_SUPPORTED_BLOCK_Y; j1 >= 0; --j1) {
            if (j1 <= rand.nextInt(5)) {
                chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
            } else {
                IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

                if (iblockstate2.getMaterial() == Material.SAND) {
                    j = -1;
                } else if (iblockstate2.getBlock() == Blocks.SAND) {
                    if (j == -1) {
                        if (k <= 0) {
                            iblockstate = Blocks.SAND.getDefaultState();
                            iblockstate1 = Blocks.SANDSTONE.getDefaultState();
                        } else if (j1 >= i - 4 && j1 <= i + 1) {
                            iblockstate = this.topBlock;
                            iblockstate1 = this.fillerBlock;
                        }

                        /*if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.WATER)) {
                            iblockstate = worldIn.provider instanceof WorldProviderDimensionAtlantis ? ModBlocks.Unknown_Fluid.getDefaultState() : WATER;
                        }*/ //TODO replace unknown fluid with a new type of watery fluid!!!

                        j = k;

                        if (j1 >= i - 1) {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
                        } else if (j1 < i - 7 - k) {
                            iblockstate = Blocks.SAND.getDefaultState();
                            iblockstate1 = SANDSTONE;
                            chunkPrimerIn.setBlockState(i1, j1, l, SANDSTONE);
                        } else {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                        }
                    } else if (j > 0) {
                        --j;
                        chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                    }
                }
            }
        }
    }
}
