package com.mystic.atlantis.world.biomes;

import com.mystic.atlantis.init.ModBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.storage.WorldInfo;

public class BiomeProviderAtlantis extends BiomeProvider {

    public BiomeProviderAtlantis(WorldInfo worldinfo) {
        super(worldinfo);
        getBiomesToSpawnIn().clear();
        getBiomesToSpawnIn().add(ModBiomes.BIOME_ATLANTIS);
        allowedBiomes.clear();
        allowedBiomes.add(ModBiomes.BIOME_ATLANTIS);
    }
}
