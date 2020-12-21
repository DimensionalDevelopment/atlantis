package com.mystic.atlantis.world.gen;

import java.util.Random;

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
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomStructures implements IWorldGenerator
{
	public final WorldGenStructure ATLANTEAN_FOUNTAIN = new WorldGenStructure("atlantean_fountain");
	public final WorldGenStructure BIG_OYSTER = new WorldGenStructure("big_oyster");
	public final WorldGenStructure ATLANTEAN_SHIP_WRECK = new WorldGenStructure("atlantean_ship_wreck");
	public final WorldGenStructure BLACK_SHELL_STRUCTURE = new WorldGenStructure("black_shell_structure");
	public final WorldGenStructure BLUE_SHELL_STRUCTURE = new WorldGenStructure("blue_shell_structure");
	public final WorldGenStructure BROWN_SHELL_STRUCTURE = new WorldGenStructure("brown_shell_structure");
	public final WorldGenStructure CYAN_SHELL_STRUCTURE = new WorldGenStructure("cyan_shell_structure");
	public final WorldGenStructure GRAY_SHELL_STRUCTURE = new WorldGenStructure("gray_shell_structure");
	public final WorldGenStructure GREEN_SHELL_STRUCTURE = new WorldGenStructure("green_shell_structure");
	public final WorldGenStructure LIGHT_BLUE_SHELL_STRUCTURE = new WorldGenStructure("light_blue_shell_structure");
	public final WorldGenStructure LIGHT_GRAY_SHELL_STRUCTURE = new WorldGenStructure("light_gray_shell_structure");
	public final WorldGenStructure LIME_SHELL_STRUCTURE = new WorldGenStructure("lime_shell_structure");
	public final WorldGenStructure MAGENTA_SHELL_STRUCTURE = new WorldGenStructure("magenta_shell_structure");
	public final WorldGenStructure ORANGE_SHELL_STRUCTURE = new WorldGenStructure("orange_shell_structure");
	public final WorldGenStructure PINK_SHELL_STRUCTURE = new WorldGenStructure("pink_shell_structure");
	public final WorldGenStructure PURPLE_SHELL_STRUCTURE = new WorldGenStructure("purple_shell_structure");
	public final WorldGenStructure RED_SHELL_STRUCTURE = new WorldGenStructure("red_shell_structure");
	public final WorldGenStructure WHITE_SHELL_STRUCTURE = new WorldGenStructure("white_shell_structure");
	public final WorldGenStructure YELLOW_SHELL_STRUCTURE = new WorldGenStructure("yellow_shell_structure");
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		if(world.provider.getDimension() == ModDimension.ATLANTIS.getId()) {
			
			generateStructure(ATLANTEAN_FOUNTAIN, world, random, chunkX, chunkZ, 55);
			generateStructure(BIG_OYSTER, world, random, chunkX, chunkZ, 45);
			generateStructure(ATLANTEAN_SHIP_WRECK, world, random, chunkX, chunkZ, 35);
			generateStructure(BLACK_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(CYAN_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(BLUE_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(BROWN_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(GRAY_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(GREEN_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(LIGHT_BLUE_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(LIGHT_GRAY_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(LIME_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(MAGENTA_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(ORANGE_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(PINK_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(PURPLE_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(RED_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(WHITE_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
			generateStructure(YELLOW_SHELL_STRUCTURE, world, random, chunkX, chunkZ, 3);
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