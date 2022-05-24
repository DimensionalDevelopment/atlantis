package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.util.Reference;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;


public class AtlantisBiomeSource extends BiomeSource {
    public static final Codec<AtlantisBiomeSource> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter((biomeSource) ->
                    biomeSource.BIOME_REGISTRY), Codec.intRange(1, 20).fieldOf("biome_size").orElse(2).forGetter((biomeSource) ->
                    biomeSource.biomeSize), Codec.LONG.fieldOf("seed").stable().forGetter((biomeSource) ->
                    biomeSource.seed)).apply(instance, instance.stable(AtlantisBiomeSource::new)));

    public static final ResourceKey<Biome> ATLANTIS_BIOME = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Reference.MODID, "atlantis_biome"));
    public static final ResourceKey<Biome> JELLYFISH_FIELDS = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Reference.MODID, "jellyfish_fields"));
    public static final ResourceKey<Biome> ATLANTEAN_ISLANDS = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Reference.MODID, "atlantean_islands_biome"));
    public static final ResourceKey<Biome> VOLCANIC_DARKSEA = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Reference.MODID, "volcanic_darksea"));
    private final Registry<Biome> BIOME_REGISTRY;
    public static Registry<Biome> LAYERS_BIOME_REGISTRY;
    private final long seed;
    private final int biomeSize;

    protected AtlantisBiomeSource(Registry<Biome> biomeRegistry, int biomeSize, long seed) {
        super(biomeRegistry.holders()
                .filter(entry -> entry.key().location().getNamespace().equals(Reference.MODID))
                .collect(Collectors.toList()));
        this.BIOME_REGISTRY = biomeRegistry;
        AtlantisBiomeSource.LAYERS_BIOME_REGISTRY = biomeRegistry;
        this.biomeSize = biomeSize;
        this.seed = seed;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new AtlantisBiomeSource(this.BIOME_REGISTRY, this.biomeSize, seed);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler noise) {
        if ((int) noise.sample(x, y, z).temperature() > 0.30) {
            return BIOME_REGISTRY.getOrCreateHolder(AtlantisBiomeSource.JELLYFISH_FIELDS);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.20 && noise.sample(x, y, z).temperature() < 0.30) {
            return BIOME_REGISTRY.getOrCreateHolder(AtlantisBiomeSource.ATLANTIS_BIOME);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.10 && noise.sample(x, y, z).temperature() < 0.20) {
            return BIOME_REGISTRY.getOrCreateHolder(AtlantisBiomeSource.ATLANTEAN_ISLANDS);
        } else {
            return BIOME_REGISTRY.getOrCreateHolder(AtlantisBiomeSource.VOLCANIC_DARKSEA);
        }
    }
}

