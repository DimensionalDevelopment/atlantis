package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.Atlantis;
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


public class AtlanteanBiomeSource extends BiomeSource {
    public static final MapCodec<AtlanteanBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryOps.retrieveRegistryLookup(Registries.BIOME).forGetter(AtlanteanBiomeSource::biomeHolderLookup),
            Codec.intRange(1, 20).fieldOf("biome_size").orElse(2).forGetter(AtlanteanBiomeSource::biomeSize),
            Codec.LONG.fieldOf("seed").stable().forGetter(AtlanteanBiomeSource::seed)).apply(instance, AtlanteanBiomeSource::new));

    public static final ResourceLocation ATLANTEAN_GARDEN = Atlantis.id("atlantean_garden");
    public static final ResourceLocation ATLANTIS_BIOME = Atlantis.id("atlantis_biome");
    public static final ResourceLocation JELLYFISH_FIELDS = Atlantis.id("jellyfish_fields");
    public static final ResourceLocation ATLANTEAN_ISLANDS = Atlantis.id("atlantean_islands_biome");
    public static final ResourceLocation VOLCANIC_DARKSEA = Atlantis.id("volcanic_darksea");
    public static final ResourceLocation GOO_LAGOONS = Atlantis.id("goo_lagoons");
    public static final ResourceLocation COCONUT_ISLES = Atlantis.id("coconut_isles");

    private final HolderLookup.RegistryLookup<Biome> biomeHolderLookup;
    private final long seed;
    private final int biomeSize;

    public AtlanteanBiomeSource(HolderLookup.RegistryLookup<Biome> biomeRegistry, int biomeSize, long seed) {
        super();
        biomeHolderLookup = biomeRegistry;
        this.biomeSize = biomeSize;
        this.seed = seed;
    }

    private Holder<Biome> getHolderBiome(ResourceLocation resourceLocationBiome) {
        return biomeHolderLookup.getOrThrow(ResourceKey.create(Registries.BIOME, resourceLocationBiome)); //return ocean if check fail
    }

    @Override
    protected @NotNull MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.of(AtlanteanBiomeSource.GOO_LAGOONS, AtlanteanBiomeSource.VOLCANIC_DARKSEA, AtlanteanBiomeSource.JELLYFISH_FIELDS,
                AtlanteanBiomeSource.ATLANTIS_BIOME, AtlanteanBiomeSource.ATLANTEAN_GARDEN, AtlanteanBiomeSource.ATLANTEAN_ISLANDS,
                AtlanteanBiomeSource.COCONUT_ISLES).map(this::getHolderBiome);
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler noise) {
        if ((int) noise.sample(x, y, z).temperature() > 0.50) {
            return getHolderBiome(AtlanteanBiomeSource.GOO_LAGOONS);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.40 && (int) noise.sample(x, y, z).temperature() < 0.50) {
            return getHolderBiome(AtlanteanBiomeSource.VOLCANIC_DARKSEA);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.30 && noise.sample(x, y, z).temperature() < 0.40) {
            return getHolderBiome(AtlanteanBiomeSource.JELLYFISH_FIELDS);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.20 && noise.sample(x, y, z).temperature() < 0.30) {
            return getHolderBiome(AtlanteanBiomeSource.ATLANTIS_BIOME);
        } else if ((int) noise.sample(x, y, z).temperature() > 0.10 && noise.sample(x, y, z).temperature() < 0.20) {
            if ((int) noise.sample(x, y, z).depth() == 0.00) {
                return getHolderBiome(AtlanteanBiomeSource.COCONUT_ISLES);
            } else {
                return getHolderBiome(AtlanteanBiomeSource.ATLANTEAN_ISLANDS);
            }
        } else {
            return getHolderBiome(AtlanteanBiomeSource.ATLANTEAN_GARDEN);
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

