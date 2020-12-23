package com.mystic.atlantis.init;


import com.mystic.atlantis.world.biomes.BiomeATLANTIS;

import com.mystic.atlantis.world.dimension.atlantis.ModBiomeAtlantis;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModBiomes {

		public static final List<ModBiomeEntry> biomeEntryList = new ArrayList<>();

		public static final BiomeATLANTIS BIOME_ATLANTIS = new BiomeATLANTIS();

		public static void registerBiomes(RegistryEvent.Register<Biome> event) {
			registerBiome(event, BIOME_ATLANTIS, BiomeType.WARM, 2, 6, Type.OCEAN);
			}

	private static void registerBiome(RegistryEvent.Register<Biome> event, ModBiomeAtlantis biome, BiomeType type, int weight, int weightDimension, BiomeDictionary.Type... biomeDictTypes) {
			event.getRegistry().register(biome);
			for (BiomeDictionary.Type biomeDictType : biomeDictTypes) {
				BiomeDictionary.addTypes(biome, biomeDictType);
			}
			biomeEntryList.add(new ModBiomeEntry(biome, type, weight, weightDimension));
		}

		public static class ModBiomeEntry {

			private final int weight;
			private final int weightDimension;
			private final Biome biome;
			private final BiomeType type;
			private final BiomeManager.BiomeEntry entry;

			public ModBiomeEntry(ModBiomeAtlantis biome, BiomeType type, int weight, int weightDimension) {
				this.type = type;
				this.biome = biome;
				this.weight = weight;
				this.weightDimension = weightDimension;
				this.entry = new BiomeManager.BiomeEntry(biome, weight);
			}

			public ModBiomeAtlantis getBiome() {
				return (ModBiomeAtlantis) this.biome;
			}

			public BiomeManager.BiomeEntry getEntry() {
				return this.entry;
			}

			public BiomeType getType() {
				return this.type;
			}

			public int getWeight() {
				return this.weight;
			}

			public int getDimensionWeight() {
				return this.weightDimension;
			}
		}
	}
