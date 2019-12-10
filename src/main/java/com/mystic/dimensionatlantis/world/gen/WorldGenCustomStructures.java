package com.mystic.dimensionatlantis.world.gen;

import java.util.ArrayList;
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
import net.minecraftforge.fml.common.IWorldGenerator;
import scala.actors.threadpool.Arrays;

public class WorldGenCustomStructures implements IWorldGenerator
{
	public static final WorldGenStructure ATLANTEAN_FOUNTAIN = new WorldGenStructure("atlantean_fountain");
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		if(world.provider.getDimension() == ModDimension.ATLANTIS.getId()) {
			
			generateStructure(ATLANTEAN_FOUNTAIN, world, random, chunkX, chunkZ, 100, BiomeATLANTIS.class);
		
		}
	}
	
	private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		int y = calculateGenerationHeight(world, x, z);
		BlockPos pos = new BlockPos(x,y,z);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
	}
	
	private static int calculateGenerationHeight(World world, int x, int z)
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