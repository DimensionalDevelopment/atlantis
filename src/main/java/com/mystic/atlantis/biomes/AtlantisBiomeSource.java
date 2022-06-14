package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AtlantisBiomeSource extends BiomeSource {
    public static final Codec<AtlantisBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance.group
            (RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter(
            (biomeSource) -> biomeSource.BIOME_REGISTRY), Codec.intRange(1, 20).fieldOf("biome_size").orElse(2).forGetter(
            (biomeSource) -> biomeSource.biomeSize), Codec.LONG.fieldOf("seed").stable().forGetter(
            (biomeSource) -> biomeSource.seed)).apply(instance, instance.stable(AtlantisBiomeSource::new)));

    public static final ResourceLocation ATLANTEAN_GARDEN = new ResourceLocation(Reference.MODID, "atlantean_garden");
    public static final ResourceLocation ATLANTIS_BIOME = new ResourceLocation(Reference.MODID, "atlantis_biome");
    public static final ResourceLocation JELLYFISH_FIELDS = new ResourceLocation(Reference.MODID, "jellyfish_fields");
    public static final ResourceLocation ATLANTEAN_ISLANDS = new ResourceLocation(Reference.MODID, "atlantean_islands_biome");
    public static final ResourceLocation VOLCANIC_DARKSEA = new ResourceLocation(Reference.MODID, "volcanic_darksea");
    public static Registry<Biome> BIOME_REGISTRY;
    public Registry<Biome> LAYERS_BIOME_REGISTRY;
    private long seed;
    private int biomeSize;

    public AtlantisBiomeSource(Registry<Biome> biomeRegistry, int biomeSize, long seed) {
        super(biomeRegistry.getResourceKey(Objects.requireNonNull(biomeRegistry.get(biomeRegistry.keySet().stream().reduce(
                (resourceLocation, resourceLocation2) -> resourceLocation).orElse(Biomes.WARM_OCEAN.getRegistryName()))
        )).stream().map(biomeRegistry::getHolderOrThrow));
        BIOME_REGISTRY = biomeRegistry;
        this.LAYERS_BIOME_REGISTRY = biomeRegistry;
        this.biomeSize = biomeSize;
        this.seed = seed;
    }

    private static Holder<Biome> getHolderBiome(ResourceLocation resourceLocationBiome) {
        return Holder.direct(Objects.requireNonNull(BIOME_REGISTRY.get(resourceLocationBiome))); //return ocean if check fail
    }

    @Override
    protected @NotNull Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public @NotNull BiomeSource withSeed(long seed) {
        return new AtlantisBiomeSource(BIOME_REGISTRY, biomeSize, seed);
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler noise) {
        if ((int) noise.sample(x, y, z).temperature() > 0.40) {
            return getHolderBiome(AtlantisBiomeSource.VOLCANIC_DARKSEA);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.30 && noise.sample(x, y, z).temperature() < 0.40) {
            return getHolderBiome(AtlantisBiomeSource.JELLYFISH_FIELDS);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.20 && noise.sample(x, y, z).temperature() < 0.30) {
            return getHolderBiome(AtlantisBiomeSource.ATLANTIS_BIOME);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.10 && noise.sample(x, y, z).temperature() < 0.20) {
            return getHolderBiome(AtlantisBiomeSource.ATLANTEAN_ISLANDS);
        } else {
            return getHolderBiome(AtlantisBiomeSource.ATLANTEAN_GARDEN);
        }
    }
}

