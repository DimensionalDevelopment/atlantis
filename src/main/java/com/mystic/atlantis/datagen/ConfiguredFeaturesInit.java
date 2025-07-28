package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.base.PalmLeavesBlock;
import com.mystic.atlantis.blocks.base.PalmLogBlock;
import com.mystic.atlantis.feature.AtlantisFeature;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ConfiguredFeaturesInit {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_CUPRUM_CONFIGURED = Atlantis.configuredFeatureKey("ancient_cuprum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AQUAMARINE_CONFIGURED = Atlantis.configuredFeatureKey("aquamarine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUNKEN_GRAVEL_CONFIGURED = Atlantis.configuredFeatureKey("sunken_gravel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SALT_ROCK_GEODE_CONFIGURED = Atlantis.configuredFeatureKey("salt_rock_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE_CONFIGURED = Atlantis.configuredFeatureKey("palm_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NYMPH_TREE_CONFIGURED = Atlantis.configuredFeatureKey("nymph_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSTONES_CONFIGURED = Atlantis.configuredFeatureKey("glowstones");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SALTY_SEA_LAKE_CONFIGURED = Atlantis.configuredFeatureKey("salty_sea_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SEABLOOM_CONFIGURED = Atlantis.configuredFeatureKey("seabloom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOLCANOES_CONFIGURED = Atlantis.configuredFeatureKey("volcano");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ISLANDS_CONFIGURED = Atlantis.configuredFeatureKey("islands");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JETSTREAM_LAKE_CONFIGURED = Atlantis.configuredFeatureKey("jetstream_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHELL_BLOCK_CONFIGURED = Atlantis.configuredFeatureKey("shell_block");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GARDEN_FOLIAGE_CONFIGURED = Atlantis.configuredFeatureKey("garden_foliage");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SEASHROOM_CONFIGURED = Atlantis.configuredFeatureKey("seashroom");

    public ConfiguredFeaturesInit(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        registerOre(context, ANCIENT_CUPRUM_CONFIGURED, BlockInit.ANCIENT_CUPRUM_ORE, BlockInit.DEEPSLATE_ANCIENT_CUPRUM_ORE, 6, 0);
        registerOre(context, AQUAMARINE_CONFIGURED, BlockInit.AQUAMARINE_ORE, BlockInit.DEEPSLATE_AQUAMARINE_ORE, 4, 0);
        registerOre(context, SUNKEN_GRAVEL_CONFIGURED, BlockInit.SUNKEN_GRAVEL, BlockInit.SUNKEN_GRAVEL, 40, 0);
        register(context, SALT_ROCK_GEODE_CONFIGURED, Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(
                        BlockStateProvider.simple(BlockInit.SEASALT_CHUNK.get()),
                        BlockStateProvider.simple(BlockInit.SEASALT_CHUNK.get()),
                        BlockStateProvider.simple(BlockInit.SEASALT_CHUNK.get()),
                        BlockStateProvider.simple(Blocks.CALCITE),
                        BlockStateProvider.simple(BlockInit.HARDENED_CALCITE_BLOCK.get()),
                        List.of(BlockInit.SEASALT_CHUNK.get().defaultBlockState()),
                        BlockTags.ANCIENT_CITY_REPLACEABLE,
                        BlockTags.REDSTONE_ORES
                ), new GeodeLayerSettings(3.7, 4.2, 5.3, 6.2),
                new GeodeCrackSettings(0.95, 2, 2),
                0.35, 0.085, false,
                ConstantInt.of(1), ConstantInt.of(1), ConstantInt.ZERO,
                -16, 16, 0.05, 1
        ));
        register(context, NYMPH_TREE_CONFIGURED, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockInit.NYMPH_LOG.get()),
                new FancyTrunkPlacer(25, 20, 22),
                BlockStateProvider.simple(BlockInit.NYMPH_LEAVES.get()),

                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), ConstantInt.of(3), 256),
                Optional.empty(),
                new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(5)))
                        .dirt(BlockStateProvider.simple(BlockInit.SEABED.get())).ignoreVines().build()
        );

        register(context, PALM_TREE_CONFIGURED, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockInit.PALM_LOG.get().defaultBlockState().setValue(PalmLogBlock.AXIS, Direction.Axis.Y)),
                new StraightTrunkPlacer(5, 2, 2),
                BlockStateProvider.simple(BlockInit.PALM_LEAVES.get().defaultBlockState().setValue(PalmLeavesBlock.DISTANCE, 7).setValue(PalmLeavesBlock.PERSISTENT, false).setValue(PalmLeavesBlock.WATERLOGGED, false)),
                new AcaciaFoliagePlacer(ConstantInt.of(3), ConstantInt.ZERO),
                Optional.empty(),
                new TwoLayersFeatureSize(1, 0, 1)).dirt(BlockStateProvider.simple(Blocks.SANDSTONE))
                        .decorators(List.of(new WaterAttachedToLeavesDecorator(0.25f, 0, 0, BlockStateProvider.simple(BlockInit.COCONUT.get().defaultBlockState()), List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST))))
                .ignoreVines().build());
        registerNone(context, GLOWSTONES_CONFIGURED, AtlantisFeature.GLOWSTONES_FEATURE);
        registerNone(context, ISLANDS_CONFIGURED, AtlantisFeature.ISLANDS_FEATURE);
        registerNone(context, VOLCANOES_CONFIGURED, AtlantisFeature.VOLCANOES_FEATURE);
        registerNone(context, SEABLOOM_CONFIGURED, AtlantisFeature.SEABLOOM_FEATURE);
        registerNone(context, GARDEN_FOLIAGE_CONFIGURED, AtlantisFeature.GARDEN_FOLIAGE_FEATURE);
        registerLake(context, JETSTREAM_LAKE_CONFIGURED, BlockInit.JETSTREAM_WATER.get(), Blocks.STONE);
        registerNone(context, SEASHROOM_CONFIGURED, AtlantisFeature.SEASHROOM_FEATURE);
        registerLake(context, SALTY_SEA_LAKE_CONFIGURED, BlockInit.SALTY_SEAWATER.get(), Blocks.STONE);
        registerNone(context, SHELL_BLOCK_CONFIGURED, AtlantisFeature.SHELL_BLOCK_FEATURE);

    }

    private static void registerLake(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Block fluid, Block barrier) {
        register(context, key, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(fluid), BlockStateProvider.simple(barrier)));
    }

    private static <T extends Block, V extends Block> void registerOre(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, DeferredBlock<Block> regular, DeferredBlock<Block> deepslate, int size, int discardChanceOnAirExposure) {
        register(context, key, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), regular.get().defaultBlockState()),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslate.get().defaultBlockState())

        ), size, discardChanceOnAirExposure));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static <T extends Feature<NoneFeatureConfiguration>> void registerNone(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, DeferredHolder<Feature<?>, T> holder) {
        context.register(key, new ConfiguredFeature<>(holder.get(), FeatureConfiguration.NONE));
    }
}
