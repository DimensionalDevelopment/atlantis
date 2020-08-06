package com.nosiphus.atlantis.world.dimension.atlantis;

import com.nosiphus.atlantis.init.ModBiome;
import com.nosiphus.atlantis.init.ModDimension;

import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import io.github.opencubicchunks.cubicchunks.api.worldgen.ICubeGenerator;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.BiomeSource;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.IBuilder;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.structure.CubicCaveGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.Loader;

import javax.lang.model.type.UnknownTypeException;

public class DimensionAtlantis extends WorldProvider
{

	public static final boolean CC = Loader.isModLoaded("cubicchunks");
	public static final boolean CWG = Loader.isModLoaded("cubicgen");
	IBuilder terrainBuilder;
	BiomeSource biomeSource;
	CubicCaveGenerator cubicCaveGenerator;

	public DimensionAtlantis(IBuilder terrainBuilder, BiomeSource biomeSource, CubicCaveGenerator cubicCaveGenerator)
	{
		this.terrainBuilder = terrainBuilder;
		this.biomeProvider = new BiomeProviderSingle(ModBiome.ATLANTIS_DIMENSION);
		this.biomeSource = biomeSource;
		this.cubicCaveGenerator = cubicCaveGenerator;
	}

	@Override 
	public DimensionType getDimensionType() 
	{
		
		return ModDimension.ATLANTIS;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		if (CC && CWG) {
			return null;
		} else {
			return new ChunkGeneratorAtlantis(world, true, world.getSeed());
		}
	}

	public ICubeGenerator createCubeGenerator(){
		if (CC && CWG) {
			return new CubicChunkGeneratorAtlantis(world, true, cubicCaveGenerator , terrainBuilder, biomeSource);
		} else {
			return null;
		}
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

