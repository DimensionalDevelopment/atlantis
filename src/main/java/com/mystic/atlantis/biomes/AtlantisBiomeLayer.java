package com.mystic.atlantis.biomes;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum AtlantisBiomeLayer implements IAreaTransformer0 {
    INSTANCE;

    public int apply(INoiseRandom noise, int x, int z) {
        return AtlantisBiomeSource.LAYERS_BIOME_REGISTRY.getId(AtlantisBiomeSource.LAYERS_BIOME_REGISTRY.getOrDefault(AtlantisBiomeSource.ATLANTIS_BIOME));
    }

    public static void setSeed(long seed) {
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
    }
}
