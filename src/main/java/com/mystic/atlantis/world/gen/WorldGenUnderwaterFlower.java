package com.mystic.atlantis.world.gen;

import com.mystic.atlantis.blocks.base.AtlanteanCore;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.world.biomes.BiomeATLANTIS;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Objects;
import java.util.Random;

public class WorldGenUnderwaterFlower implements IWorldGenerator
{
    public final WorldGeneratorUnderwaterFlower UNDERWATER_FLOWER = new WorldGeneratorUnderwaterFlower();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.getDimension() == AtlantisConfig.dimensionId)
        {
            generateFlower(UNDERWATER_FLOWER, world, random, chunkX, chunkZ, 10);
        }
    }

    private void generateFlower(WorldGeneratorUnderwaterFlower generator, World world, Random random, int chunkX, int chunkZ, int chance)
    {
        int x = (chunkX * 16) + random.nextInt(15) + 8;
        int z = (chunkZ * 16) + random.nextInt(15) + 8;
        int y = calculateGenerationHeight(world, x, z);
        BlockPos pos = new BlockPos(x,y,z);

        world.provider.getBiomeForCoords(pos);
        BiomeATLANTIS.getBiome(10);


        if(world.getWorldType() != WorldType.FLAT)
        {
            if(BiomeDictionary.hasType(Objects.requireNonNull(BiomeATLANTIS.getBiome(10)), BiomeDictionary.Type.OCEAN))
            {
                if(random.nextInt(chance) == 0)
                {
                    generator.generate(world, random, pos);
                }
            }
        }
    }

    private int calculateGenerationHeight(World world, int x, int z)
    {
        for (int y=0; y<300; y++)
        {
            if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.WATER)
            {
                return y;
            }
        }
        return 3000;
    }
}
