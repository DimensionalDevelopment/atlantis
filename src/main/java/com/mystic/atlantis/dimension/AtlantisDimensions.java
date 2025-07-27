package com.mystic.atlantis.dimension;

import com.mojang.datafixers.util.Pair;
import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.biomes.AtlanteanBiomeSource;
import com.mystic.atlantis.datagen.BiomeInit;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class AtlantisDimensions
{
    public static ResourceKey<Level> ATLANTIS_WORLD = ResourceKey.create(Registries.DIMENSION, Atlantis.id("atlantis"));
    public static final ResourceKey<DimensionType> ATLANTIS_DIMENSION_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, Atlantis.id("atlantis"));
    public static ResourceKey<LevelStem> ATLANTIS_DIMENSION_STEM = ResourceKey.create(Registries.LEVEL_STEM, Atlantis.id("atlantis"));
    public static final ResourceLocation ATLANTIS_DIMENSION_EFFECT = Atlantis.id("atlantis");

    public static DimensionType ATLANTIS_TYPE;
    public static ResourceKey<NoiseGeneratorSettings> ATLANTIS_DIMENSION_NOISE_SETTING = ResourceKey.create(Registries.NOISE_SETTINGS, Atlantis.id("atlantis"));
    public static ServerLevel ATLANTIS_DIMENSION;

    public static boolean isAtlantisDimension(Level world) {
        return world != null && world.dimension().equals(ATLANTIS_WORLD);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        AtlantisDimensions.ATLANTIS_TYPE = event.getServer().registryAccess().registryOrThrow(Registries.DIMENSION_TYPE).get(ATLANTIS_DIMENSION_TYPE_KEY);
        AtlantisDimensions.ATLANTIS_DIMENSION = event.getServer().getLevel(ATLANTIS_WORLD);
    }

    public static void registerBiomeSources() {
        Registry.register(BuiltInRegistries.BIOME_SOURCE, Atlantis.id("atlantean_biome_source"), AtlanteanBiomeSource.CODEC);
    }

    public AtlantisDimensions(BootstrapContext<LevelStem> context) {
        var holderGetter = context.lookup(Registries.BIOME);
        var holderGetter1 = context.lookup(Registries.DIMENSION_TYPE);
        var holderGetter2 = context.lookup(Registries.NOISE_SETTINGS);
        context.register(ATLANTIS_DIMENSION_STEM, new LevelStem(holderGetter1.getOrThrow(ATLANTIS_DIMENSION_TYPE_KEY),
                new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(
                                List.of(
                                        Pair.of(Climate.parameters(0.1F, 0.1F, 0.1F, 0.1F, 1F, 0F, 0F), holderGetter.getOrThrow(BiomeInit.GOO_LAGOONS_KEY)),
                                        Pair.of(Climate.parameters(1F, 1F, 1F, 1F, 1F, 1F, 0F), holderGetter.getOrThrow(BiomeInit.COCONUT_ISLES_KEY)),
                                        Pair.of(Climate.parameters(0.3F, 0.2F, 0.1F, 0.2F, 1F, 0F, 0F), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY)),
                                        Pair.of(Climate.parameters(0.1F, 0.1F, 0.2F, 0.1F, 1F, 0F, 0F), holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY)),
                                        Pair.of(Climate.parameters(0.1F, 0.1F, 0.1F, 0.2F, 1F, 0F, 0F), holderGetter.getOrThrow(BiomeInit.AQUAIEL_JELLYFISH_FIELDS_KEY)),
                                        Pair.of(Climate.parameters(0.2F, 0.1F, 0.1F, 0.1F, 1F, 0F, 0F), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY)),
                                        Pair.of(Climate.parameters(0.1F, 0.2F, 0.1F, 0.1F, 1F, 0F, 0F), holderGetter.getOrThrow(BiomeInit.VOLCANIC_DARKSEA_KEY))
                                ))), holderGetter2.getOrThrow(ATLANTIS_DIMENSION_NOISE_SETTING))));
    }
}
