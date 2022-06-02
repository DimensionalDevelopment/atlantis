package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.lighting.AtlantisChunkSkylightProvider;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class AtlantisBiomeSource extends BiomeSource {
    public static final Codec<AtlantisBiomeSource> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((biomeSource) ->
                    biomeSource.BIOME_REGISTRY), Codec.intRange(1, 20).fieldOf("biome_size").orElse(2).forGetter((biomeSource) ->
                    biomeSource.biomeSize), Codec.LONG.fieldOf("seed").stable().forGetter((biomeSource) ->
                    biomeSource.seed)).apply(instance, instance.stable(AtlantisBiomeSource::new)));

    public static final ResourceLocation ATLANTEAN_GARDEN = new ResourceLocation(Reference.MODID, "atlantean_garden");
    public static final ResourceLocation ATLANTIS_BIOME = new ResourceLocation(Reference.MODID, "atlantis_biome");
    public static final ResourceLocation JELLYFISH_FIELDS = new ResourceLocation(Reference.MODID, "jellyfish_fields");
    public static final ResourceLocation ATLANTEAN_ISLANDS = new ResourceLocation(Reference.MODID, "atlantean_islands_biome");
    public static final ResourceLocation VOLCANIC_DARKSEA = new ResourceLocation(Reference.MODID, "volcanic_darksea");
    public static Registry<Biome> BIOME_REGISTRY;
    public static Registry<Biome> LAYERS_BIOME_REGISTRY;
    private final long seed;
    private final int biomeSize;

    public AtlantisBiomeSource(Registry<Biome> biomeRegistry, int biomeSize, long seed) {
        super(biomeRegistry.entrySet().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(Reference.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
        BIOME_REGISTRY = biomeRegistry;
        AtlantisBiomeSource.LAYERS_BIOME_REGISTRY = biomeRegistry;
        this.biomeSize = biomeSize;
        this.seed = seed;
    }

    @Override
    protected @NotNull Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public @NotNull BiomeSource withSeed(long seed) {
        return new AtlantisBiomeSource(BIOME_REGISTRY, this.biomeSize, seed);
    }

    @Override
    public @NotNull Biome getNoiseBiome(int x, int y, int z, Climate.Sampler noise) {
        if ((int) noise.sample(x, y, z).temperature() > 0.40) {
            return Objects.requireNonNull(BIOME_REGISTRY.get(AtlantisBiomeSource.ATLANTEAN_GARDEN));
        } else if ((int) noise.sample(x, y, z).temperature() > 0.30 && noise.sample(x, y, z).temperature() < 0.40) {
            return Objects.requireNonNull(BIOME_REGISTRY.get(AtlantisBiomeSource.JELLYFISH_FIELDS));
        } else if ((int) noise.sample(x, y, z).temperature() > 0.20 && noise.sample(x, y, z).temperature() < 0.30) {
            return Objects.requireNonNull(BIOME_REGISTRY.get(AtlantisBiomeSource.ATLANTIS_BIOME));
        } else if ((int) noise.sample(x, y, z).temperature() > 0.10 && noise.sample(x, y, z).temperature() < 0.20) {
            return Objects.requireNonNull(BIOME_REGISTRY.get(AtlantisBiomeSource.ATLANTEAN_ISLANDS));
        } else {
            return Objects.requireNonNull(BIOME_REGISTRY.get(AtlantisBiomeSource.VOLCANIC_DARKSEA));
        }
    }
}

