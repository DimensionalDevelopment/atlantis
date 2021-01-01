package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;

import java.util.Collections;

public class AtlantisBiomeSource extends BiomeProvider {
    public static final Codec<AtlantisBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance.group(RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((biomeSource) -> biomeSource.biomeRegistry), Codec.LONG.fieldOf("seed").stable().forGetter((biomeSource) -> biomeSource.seed)).apply(instance, instance.stable(AtlantisBiomeSource::new)));

    private final Registry<Biome> biomeRegistry;
    private final long seed;

    protected AtlantisBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        super(Collections.singletonList(biomeRegistry.getOptional(Biomes.DEEP_WARM_OCEAN.getLocation()).get()));
        this.biomeRegistry = biomeRegistry;
        this.seed = seed;
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long seed) {
        return new AtlantisBiomeSource(this.biomeRegistry, seed);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return biomeRegistry.getOptional(Biomes.DEEP_WARM_OCEAN.getLocation()).get();
    }
}
