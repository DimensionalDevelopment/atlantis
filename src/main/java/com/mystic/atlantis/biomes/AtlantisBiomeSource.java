package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.Map;
import java.util.stream.Collectors;


public class AtlantisBiomeSource extends BiomeSource {
    public static final Codec<AtlantisBiomeSource> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((biomeSource) ->
                    biomeSource.BIOME_REGISTRY), Codec.intRange(1, 20).fieldOf("biome_size").orElse(2).forGetter((biomeSource) ->
                    biomeSource.biomeSize), Codec.LONG.fieldOf("seed").stable().forGetter((biomeSource) ->
                    biomeSource.seed)).apply(instance, instance.stable(AtlantisBiomeSource::new)));

    public static final Identifier ATLANTIS_BIOME = new Identifier(Reference.MODID, "atlantis_biome");
    public static final Identifier JELLYFISH_FIELDS = new Identifier(Reference.MODID, "jellyfish_fields");
    public static final Identifier ATLANTEAN_ISLANDS = new Identifier(Reference.MODID, "atlantean_islands_biome");
    public static final Identifier VOLCANIC_DARKSEA = new Identifier(Reference.MODID, "volcanic_darksea");
    private final Registry<Biome> BIOME_REGISTRY;
    public static Registry<Biome> LAYERS_BIOME_REGISTRY;
    private final long seed;
    private final int biomeSize;

    protected AtlantisBiomeSource(Registry<Biome> biomeRegistry, int biomeSize, long seed) {
        super(biomeRegistry.getEntries().stream()
                .filter(entry -> entry.getKey().getValue().getNamespace().equals(Reference.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
        this.BIOME_REGISTRY = biomeRegistry;
        AtlantisBiomeSource.LAYERS_BIOME_REGISTRY = biomeRegistry;
        this.biomeSize = biomeSize;
        this.seed = seed;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new AtlantisBiomeSource(this.BIOME_REGISTRY, this.biomeSize, seed);
    }

    @Override
    public Biome getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        if ((int) noise.sample(x, y, z).temperatureNoise() > 0.30) {
            return BIOME_REGISTRY.get(AtlantisBiomeSource.JELLYFISH_FIELDS);
        } else if ((int) noise.sample(x, y, z).temperatureNoise() > 0.20 && noise.sample(x, y, z).temperatureNoise() < 0.30) {
            return BIOME_REGISTRY.get(AtlantisBiomeSource.ATLANTIS_BIOME);
        } else if ((int) noise.sample(x, y, z).temperatureNoise() > 0.10 && noise.sample(x, y, z).temperatureNoise() < 0.20) {
            return BIOME_REGISTRY.get(AtlantisBiomeSource.ATLANTEAN_ISLANDS);
        } else {
            return BIOME_REGISTRY.get(AtlantisBiomeSource.VOLCANIC_DARKSEA);
        }
    }
}

