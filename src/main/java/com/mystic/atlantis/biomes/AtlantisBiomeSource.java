package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;


public class AtlantisBiomeSource extends BiomeSource {
    public static final Codec<AtlantisBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryOps.retrieveRegistryLookup(Registries.BIOME).forGetter(AtlantisBiomeSource::biomeHolderLookup),
            Codec.intRange(1, 20).fieldOf("biome_size").orElse(2).forGetter(AtlantisBiomeSource::biomeSize),
            Codec.LONG.fieldOf("seed").stable().forGetter(AtlantisBiomeSource::seed)).apply(instance, AtlantisBiomeSource::new));

    public static final ResourceLocation ATLANTEAN_GARDEN = new ResourceLocation(Reference.MODID, "atlantean_garden");
    public static final ResourceLocation ATLANTIS_BIOME = new ResourceLocation(Reference.MODID, "atlantis_biome");
    public static final ResourceLocation JELLYFISH_FIELDS = new ResourceLocation(Reference.MODID, "jellyfish_fields");
    public static final ResourceLocation ATLANTEAN_ISLANDS = new ResourceLocation(Reference.MODID, "atlantean_islands_biome");
    public static final ResourceLocation VOLCANIC_DARKSEA = new ResourceLocation(Reference.MODID, "volcanic_darksea");
    public static final ResourceLocation GOO_LAGOONS = new ResourceLocation(Reference.MODID, "goo_lagoons");

    private final HolderLookup.RegistryLookup<Biome> biomeHolderLookup;
    private final long seed;
    private final int biomeSize;

    public AtlantisBiomeSource(HolderLookup.RegistryLookup<Biome> biomeRegistry, int biomeSize, long seed) {
        super();
        biomeHolderLookup = biomeRegistry;
        this.biomeSize = biomeSize;
        this.seed = seed;
    }

    private Holder<Biome> getHolderBiome(ResourceLocation resourceLocationBiome) {
        return biomeHolderLookup.getOrThrow(ResourceKey.create(Registries.BIOME, resourceLocationBiome)); //return ocean if check fail
    }

    @Override
    protected @NotNull Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.of(AtlantisBiomeSource.GOO_LAGOONS, AtlantisBiomeSource.VOLCANIC_DARKSEA, AtlantisBiomeSource.JELLYFISH_FIELDS, AtlantisBiomeSource.ATLANTIS_BIOME).map(this::getHolderBiome);
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler noise) {
        if ((int) noise.sample(x, y, z).temperature() > 0.50) {
            return getHolderBiome(AtlantisBiomeSource.GOO_LAGOONS);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.40 && (int) noise.sample(x, y, z).temperature() < 0.50) {
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

    public long seed() {
        return seed;
    }

    public int biomeSize() {
        return biomeSize;
    }

    public HolderLookup.RegistryLookup<Biome> biomeHolderLookup() {
        return biomeHolderLookup;
    }
}

