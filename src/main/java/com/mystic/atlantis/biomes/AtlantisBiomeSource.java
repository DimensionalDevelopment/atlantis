package com.mystic.atlantis.biomes;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
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

import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;


public class AtlantisBiomeSource extends MultiNoiseBiomeSource {
    public static final Codec<AtlantisBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryOps.retrieveRegistryLookup(Registries.BIOME).forGetter(AtlantisBiomeSource::biomeHolderLookup),
            Codec.intRange(1, 20).fieldOf("biome_size").orElse(2).forGetter(AtlantisBiomeSource::biomeSize),
            Codec.LONG.fieldOf("seed").stable().forGetter(AtlantisBiomeSource::seed)).apply(instance, AtlantisBiomeSource::new));

    public static final ResourceLocation ATLANTEAN_GARDEN = new ResourceLocation(Reference.MODID, "atlantean_garden");
    public static final ResourceLocation ATLANTIS_BIOME = new ResourceLocation(Reference.MODID, "atlantis_biome");
    public static final ResourceLocation JELLYFISH_FIELDS = new ResourceLocation(Reference.MODID, "aquariel_jellyfish_fields");
    public static final ResourceLocation ATLANTEAN_ISLANDS = new ResourceLocation(Reference.MODID, "atlantean_islands_biome");
    public static final ResourceLocation VOLCANIC_DARKSEA = new ResourceLocation(Reference.MODID, "volcanic_darksea");
    public static final ResourceLocation GOO_LAGOONS = new ResourceLocation(Reference.MODID, "goo_lagoons");
    public static final ResourceLocation COCONUT_ISLES = new ResourceLocation(Reference.MODID, "coconut_isles");

    private final HolderLookup.RegistryLookup<Biome> biomeHolderLookup;
    private final long seed;
    private final int biomeSize;

    public AtlantisBiomeSource(HolderLookup.RegistryLookup<Biome> biomeRegistry, int biomeSize, long seed) {
        super(Either.left(new Climate.ParameterList<>(List.of(Pair.of(new Climate.ParameterPoint(
                new Climate.Parameter(0, 1), new Climate.Parameter(0, 1), new Climate.Parameter(0, 1), new Climate.Parameter(0, 1), new Climate.Parameter(0, 1),
                new Climate.Parameter(0, 1), 0L), biomeRegistry.getOrThrow(Biomes.OCEAN))))));
        biomeHolderLookup = biomeRegistry;
        this.biomeSize = biomeSize;
        this.seed = seed;
    }

    private Holder<Biome> getHolderBiome(ResourceLocation resourceLocationBiome) {
        return biomeHolderLookup.getOrThrow(ResourceKey.create(Registries.BIOME, resourceLocationBiome)); //return ocean if check fail
    }

    @Override
    protected @NotNull Codec<? extends MultiNoiseBiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.of(AtlantisBiomeSource.GOO_LAGOONS, AtlantisBiomeSource.VOLCANIC_DARKSEA, AtlantisBiomeSource.JELLYFISH_FIELDS,
                AtlantisBiomeSource.ATLANTIS_BIOME, AtlantisBiomeSource.ATLANTEAN_GARDEN, AtlantisBiomeSource.ATLANTEAN_ISLANDS,
                AtlantisBiomeSource.COCONUT_ISLES).map(this::getHolderBiome);
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler noise) {
        Climate.TargetPoint climate = noise.sample(x, y, z);
        double temperature = climate.temperature();
        double humidity = climate.humidity();
        double continentalness = climate.continentalness();

        if (y >= 63) {
            return getHolderBiome(AtlantisBiomeSource.COCONUT_ISLES);
        } else if (y >= 47) {
            return getHolderBiome(AtlantisBiomeSource.ATLANTIS_BIOME);
        } else {
            double combined = (temperature * 0.4) + (humidity * 0.4) + (continentalness * 0.2);

            if (combined > 0.5) {
                return getHolderBiome(AtlantisBiomeSource.GOO_LAGOONS);
            } else if (combined > 0.25) {
                return getHolderBiome(AtlantisBiomeSource.VOLCANIC_DARKSEA);
            } else if (combined > 0.0) {
                return getHolderBiome(AtlantisBiomeSource.JELLYFISH_FIELDS);
            } else if (combined > -0.25) {
                return getHolderBiome(AtlantisBiomeSource.ATLANTEAN_ISLANDS);
            } else {
                return getHolderBiome(AtlantisBiomeSource.ATLANTEAN_GARDEN);
            }
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

