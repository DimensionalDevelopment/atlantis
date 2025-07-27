package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.mystic.atlantis.datagen.ConfiguredFeaturesInit.*;
import static net.minecraft.world.level.levelgen.Heightmap.Types.OCEAN_FLOOR_WG;
import static net.minecraft.world.level.levelgen.placement.BiomeFilter.biome;
import static net.minecraft.world.level.levelgen.placement.HeightmapPlacement.onHeightmap;
import static net.minecraft.world.level.levelgen.placement.InSquarePlacement.spread;
import static net.minecraft.world.level.levelgen.placement.RarityFilter.onAverageOnceEvery;

public class PlacedFeatureInit {
    public static final ResourceKey<PlacedFeature> ANCIENT_CUPRUM_PLACED = key("ancient_cuprum_placed");
    public static final ResourceKey<PlacedFeature> AQUAMARINE_PLACED = key("aquamarine_placed");
    public static final ResourceKey<PlacedFeature> PALM_TREE_PLACED = key("palm_tree_placed");
    public static final ResourceKey<PlacedFeature> NYMPH_TREE_PLACED = key("nymph_tree_placed");
    public static final ResourceKey<PlacedFeature> GLOWSTONES_PLACED = key("glowstones_placed");
    public static final ResourceKey<PlacedFeature> ISLANDS_PLACED = key("islands_placed");
    public static final ResourceKey<PlacedFeature> VOLCANOES_PLACED = key("volcanoes_placed");
    public static final ResourceKey<PlacedFeature> SEABLOOM_PLACED = key("seabloom_placed");
    public static final ResourceKey<PlacedFeature> GARDEN_FOLIAGE_PLACED = key("garden_foliage_placed");
    public static final ResourceKey<PlacedFeature> JETSTREAM_LAKE_PLACED = key("jetstream_lake_placed");
    public static final ResourceKey<PlacedFeature> SEASHROOM_PLACED = key("seashroom_placed");
    public static final ResourceKey<PlacedFeature> SALT_ROCK_GEODE_PLACED = key("salt_rock_geode_placed");
    public static final ResourceKey<PlacedFeature> SALTY_SEA_LAKE_PLACED = key("salty_sea_lake_placed");
    public static final ResourceKey<PlacedFeature> SHELL_BLOCK_PLACED = key("shell_block_placed");
    public static final ResourceKey<PlacedFeature> SUNKEN_GRAVEL_PLACED = key("sunken_gravel_placed");

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Atlantis.id(name));
    }

    public PlacedFeatureInit(BootstrapContext<PlacedFeature> context) {
        var registry = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, registry, ANCIENT_CUPRUM_PLACED, ANCIENT_CUPRUM_CONFIGURED,
                count(20),
                spread(),
                absolute(-64, 64),
                biome());

        register(context, registry, AQUAMARINE_PLACED, AQUAMARINE_CONFIGURED,
                count(25),
                spread(),
                absolute(-64, 64),
                biome());

        register(context, registry, PALM_TREE_PLACED, PALM_TREE_CONFIGURED,
                count(2),
                spread(),
                onHeightmap(OCEAN_FLOOR_WG),
                biome());

        register(context, registry, NYMPH_TREE_PLACED, NYMPH_TREE_CONFIGURED,
                count(5),
                spread(),
                onHeightmap(OCEAN_FLOOR_WG),
                biome());

        register(context, registry, GLOWSTONES_PLACED, GLOWSTONES_CONFIGURED,
                biome(),
                spread(),
                onHeightmap(OCEAN_FLOOR_WG),
                absolute(65, 300));

        register(context, registry, ISLANDS_PLACED, ISLANDS_CONFIGURED,
                biome(),
                spread(),
                absolute(90, 270));

        register(context, registry, VOLCANOES_PLACED, VOLCANOES_CONFIGURED,
                biome(),
                spread(),
                onHeightmap(OCEAN_FLOOR_WG),
                absolute(65, 300));

        register(context, registry, SEABLOOM_PLACED, SEABLOOM_CONFIGURED,
                onAverageOnceEvery(1),
                biome(),
                onHeightmap(OCEAN_FLOOR_WG),
                countOnEveryLayer(0, 99));

        register(context, registry, GARDEN_FOLIAGE_PLACED, GARDEN_FOLIAGE_CONFIGURED,
                onAverageOnceEvery(1),
                biome(),
                onHeightmap(OCEAN_FLOOR_WG),
                countOnEveryLayer(0, 99));

        register(context, registry, JETSTREAM_LAKE_PLACED, JETSTREAM_LAKE_CONFIGURED,
                count(2),
                absolute(-60, 80),
                biome());

        register(context, registry, SEASHROOM_PLACED, SEASHROOM_CONFIGURED,
                count(1),
                biome(),
                onHeightmap(OCEAN_FLOOR_WG),
                countOnEveryLayer(0, 99));

        register(context, registry, SALT_ROCK_GEODE_PLACED, SALT_ROCK_GEODE_CONFIGURED,
                biome(),
                absolute(-30, 70),
                count(10));

        register(context, registry, SALTY_SEA_LAKE_PLACED, SALTY_SEA_LAKE_CONFIGURED,
                count(2),
                absolute(-60, 80),
                biome());

        register(context, registry, SHELL_BLOCK_PLACED, SHELL_BLOCK_CONFIGURED,
                onAverageOnceEvery(1),
                biome(),
                onHeightmap(OCEAN_FLOOR_WG),
                countOnEveryLayer(0, 99));

        register(context, registry, SUNKEN_GRAVEL_PLACED, SUNKEN_GRAVEL_CONFIGURED,
                count(10),
                spread(),
                absolute(-64, 45),
                biome());
    }

    private static CountOnEveryLayerPlacement countOnEveryLayer(int min, int max) {
        return CountOnEveryLayerPlacement.of(BiasedToBottomInt.of(min, max)); //TODO: Haunted figure out what its replacement is.
    }

    private static void register(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?,?>> registry, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuredKey, PlacementModifier... modifiers) {
        context.register(key, new PlacedFeature(registry.getOrThrow(configuredKey), List.of(modifiers)));
    }

    private static HeightRangePlacement absolute(int min, int max) {
        return HeightRangePlacement.of(
                UniformHeight.of(
                        VerticalAnchor.absolute(min),
                        VerticalAnchor.absolute(max)
        ));
    }

    private  static CountPlacement count(int count) {
        return CountPlacement.of(count);
    }
}
