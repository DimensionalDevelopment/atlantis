package com.nosiphus.atlantis.world.dimension.atlantis;

import com.nosiphus.atlantis.init.ModDimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionAtlantis extends WorldProvider
{
	public DimensionAtlantis()
	{}

	@Override
	public DimensionType getDimensionType()
	{

		return ModDimension.ATLANTIS;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {

		return new ChunkGeneratorAtlantis(world, true, world.getSeed());

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

