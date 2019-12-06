package com.mystic.dimensionatlantis.world.dimension.atlantis;

import com.mystic.dimensionatlantis.init.ModBiome;
import com.mystic.dimensionatlantis.init.ModDimension;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionAtlantis extends WorldProvider
{
	public DimensionAtlantis() 
	{
		this.biomeProvider = new BiomeProviderSingle(ModBiome.ATLANTIS_DIMENSION);
	}
	
	@Override 
	public DimensionType getDimensionType() 
	{
		
		return ModDimension.ATLANTIS;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() 
	{
		
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

