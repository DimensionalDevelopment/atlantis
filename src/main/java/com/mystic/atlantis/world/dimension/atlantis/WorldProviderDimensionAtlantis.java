package com.mystic.atlantis.world.dimension.atlantis;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderDimensionAtlantis extends WorldProvider
{
	public WorldProviderDimensionAtlantis()
	{}

	@Override
	public DimensionType getDimensionType()
	{

		return DimensionType.byName("Atlantis");
	}

	@Override
	public IChunkGenerator createChunkGenerator() {

		return new ChunkGeneratorAtlantis(world, false, world.getSeed());

	}

	@Override
	public boolean canRespawnHere() 
	{
		
		return false;
	}
	
	@Override
	public boolean isSurfaceWorld() 
	{
		
		return false;
	}
}

