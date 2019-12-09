package com.mystic.dimensionatlantis.init;


import com.mystic.dimensionatlantis.world.biomes.BiomeATLANTIS;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBiome
{
	public static final Biome ATLANTIS_DIMENSION = new BiomeATLANTIS();
	
	public static void registerBiomes()
	{
		initBiome(ATLANTIS_DIMENSION, "Atlantis", BiomeType.WARM, Type.OCEAN, Type.WATER);
	}
	
	private static Biome initBiome(Biome biome, String name, BiomeType biomeType, Type type, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(biomeType, new BiomeEntry(biome, 10));
		BiomeManager.addSpawnBiome(biome);
		return biome;
	}
}
