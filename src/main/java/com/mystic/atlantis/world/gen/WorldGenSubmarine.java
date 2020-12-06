package com.mystic.atlantis.world.gen;

import com.mystic.atlantis.world.biomes.BiomeATLANTIS;
import com.mystic.atlantis.init.ModDimension;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenSubmarine implements IWorldGenerator{

    public final WorldGenStructure ATLANTEAN_SUBMARINE = new WorldGenStructure("atlantean_submarine");

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == ModDimension.ATLANTIS.getId()) {

            generateStructure(ATLANTEAN_SUBMARINE, world, random, chunkX, chunkZ, 35);
        }
    }


    private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance)
    {

        int x = (chunkX * 16) + random.nextInt(15) + 8;
        int z = (chunkZ * 16) + random.nextInt(15) + 8;
        int y = calculateGenerationHeight(world, x, z);
        BlockPos pos = new BlockPos(x,y,z);

        world.provider.getBiomeForCoords(pos);
        BiomeATLANTIS.getBiome(10);


        if(world.getWorldType() != WorldType.FLAT)
        {
            if(BiomeDictionary.hasType(BiomeATLANTIS.getBiome(10), BiomeDictionary.Type.OCEAN))
            {
                if(random.nextInt(chance) == 0)
                {
                    generator.generate(world, random, pos);
                }
            }
        }
    }

    private int calculateGenerationHeight(World world, int x, int z) {
        Random random = world.rand;
        int y = random.nextInt(250);
        if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.WATER) {
            return y;
        }
        return 260;
    }

}
