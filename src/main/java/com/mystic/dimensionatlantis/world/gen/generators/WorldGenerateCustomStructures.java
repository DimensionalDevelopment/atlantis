package com.mystic.dimensionatlantis.world.gen.generators;

import java.util.Random;
import com.mystic.dimensionatlantis.init.ModDimension;
import com.mystic.dimensionatlantis.world.biomes.BiomeATLANTIS;
import com.mystic.dimensionatlantis.world.gen.generators.WorldGenStructure;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenerateCustomStructures implements IWorldGenerator
{
	public final WorldGenStructure ATLANTEAN_FOUNTAIN = new WorldGenStructure("atlantean_fountain");
	public final WorldGenStructure BIG_OYSTER = new WorldGenStructure("big_oyster");
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		if(world.provider.getDimension() == ModDimension.ATLANTIS.getId()) {
			
			generateStructure(ATLANTEAN_FOUNTAIN, world, random, chunkX, chunkZ, 3);
			generateBigOyster(BIG_OYSTER, world, random, chunkX, chunkZ, 5);
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
			if(BiomeDictionary.hasType(BiomeATLANTIS.getBiome(10), Type.OCEAN))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
	}
	
	private void generateBigOyster(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance)
	{
		
		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		int y = calculateGenerationHeight(world, x, z);
		BlockPos pos = new BlockPos(x,y,z);
		
		 world.provider.getBiomeForCoords(pos);
		BiomeATLANTIS.getBiome(10);
		
		
		if(world.getWorldType() != WorldType.FLAT)
		{
			if(BiomeDictionary.hasType(BiomeATLANTIS.getBiome(10), Type.OCEAN))
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
	    for (int y=0; y<256; y++)
	    {
	        if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.WATER)
	        {
	            return y;
	        }
	    }
		return 260;
	}

}