package com.mystic.atlantis.biomes;

import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.gen.ChunkRandom;

public enum AtlantisBiomeLayer implements InitLayer {
    INSTANCE;

    public int sample(LayerRandomnessSource noise, int x, int z) {
        return AtlantisBiomeSource.LAYERS_BIOME_REGISTRY.getRawId(AtlantisBiomeSource.LAYERS_BIOME_REGISTRY.get(AtlantisBiomeSource.ATLANTIS_BIOME));
    }

    public static void setSeed(long seed) {
        ChunkRandom sharedseedrandom = new ChunkRandom(seed);
    }
}
