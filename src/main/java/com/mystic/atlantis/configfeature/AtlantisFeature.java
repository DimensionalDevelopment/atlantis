package com.mystic.atlantis.configfeature;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.util.Reference;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class AtlantisFeature {
    public static final Feature<DefaultFeatureConfig> UNDERWATER_FLOWER_ATLANTIS = register(
            "underwater_flower_atlantis", new UnderwaterFlowerAtlantis(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SHELL_BLOCK_FEATURE = register(
            "shell_block_feature_atlantis", new ShellBlockFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ATLANTEAN_ISLANDS = register(
            "atlantean_islands_feature_atlantis", new AtlanteanIslands(DefaultFeatureConfig.CODEC));
    public static final LakeFeature JETSTREAM_LAKE_FEATURE_ATLANTIS = (LakeFeature) register(
            "jetstream_lake_feature_atlantis", new LakeFeature(LakeFeature.Config.CODEC)); //TODO: Update
    public static final Feature<DefaultFeatureConfig> ATLANTEAN_VOLCANO = register(
            "atlantean_volcano_feature_atlantis", new AtlanteanVolcano(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> UNDERWATER_MUSHROOM_ATLANTIS = register(
            "underwater_mushroom_atlantis", new UnderwaterMushroomAtlantis(DefaultFeatureConfig.CODEC));

    public static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerTree(String name, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Reference.MODID, name),
                feature);
    }

    public static <T extends FeatureConfig> Feature<T> register(String id, Feature<T> t) {
        Registry.register(Registry.FEATURE, new Identifier(Reference.MODID, id), t);
        return t;
    }

    public static final class ConfiguredFeaturesAtlantis {
        public static final ConfiguredFeature<?, ?> UNDERWATER_FLOWER_ATLANTIS = AtlantisFeature.UNDERWATER_FLOWER_ATLANTIS.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> UNDERWATER_MUSHROOM_ATLANTIS = AtlantisFeature.UNDERWATER_MUSHROOM_ATLANTIS.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> SHELL_BLOCK_FEATURE_ATLANTIS = AtlantisFeature.SHELL_BLOCK_FEATURE.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS = AtlantisFeature.ATLANTEAN_ISLANDS.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS = AtlantisFeature.ATLANTEAN_VOLCANO.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> JETSTREAM_LAKE_FEATURE_ATLANTIS = AtlantisFeature.JETSTREAM_LAKE_FEATURE_ATLANTIS.configure(new LakeFeature.Config(BlockStateProvider.of(FluidInit.JETSTREAM_WATER), BlockStateProvider.of(Blocks.STONE)));
        public static final ConfiguredFeature<TreeFeatureConfig, ?> ATLANTEAN_TREE =
                registerTree("atlantean_tree", Feature.TREE.configure(new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlockInit.ATLANTEAN_LOGS),
                        new StraightTrunkPlacer(5, 6, 3),
                        BlockStateProvider.of(BlockInit.ATLANTEAN_LEAVES),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
                        new TwoLayersFeatureSize(1, 0, 2)).build()));

        public static final RegistryKey<PlacedFeature> UNDERWATER_FLOWER_ATLANTIS_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(Reference.MODID, "underwater_flower_atlantis"));
        public static final RegistryKey<PlacedFeature> UNDERWATER_MUSHROOM_ATLANTIS_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(Reference.MODID, "underwater_mushroom_atlantis"));
        public static final RegistryKey<PlacedFeature> SHELL_BLOCK_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(Reference.MODID, "shell_block_feature_atlantis"));
        public static final RegistryKey<PlacedFeature> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(Reference.MODID, "atlantean_islands_feature_atlantis"));
        public static final RegistryKey<PlacedFeature> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(Reference.MODID, "atlantean_volcano_feature_atlantis"));
        public static final RegistryKey<PlacedFeature> JETSTREAM_LAKE_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(Reference.MODID, "jetstream_lake_feature_atlantis"));
        public static final RegistryKey<PlacedFeature> ATLANTEAN_TREE_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(Reference.MODID, "atlantean_tree"));

        public static void registerConfiguredFeatures() {
            register("underwater_flower_atlantis", ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS.withPlacement(HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.getBottom(), YOffset.getTop())), SquarePlacementModifier.of(), CountPlacementModifier.of(100)));

            register("underwater_mushroom_atlantis", ConfiguredFeaturesAtlantis.UNDERWATER_MUSHROOM_ATLANTIS.withPlacement(HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.getBottom(), YOffset.getTop())), SquarePlacementModifier.of(), CountPlacementModifier.of(100)));

            register("shell_block_feature_atlantis", ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE_ATLANTIS.withPlacement(HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.getBottom(), YOffset.getTop())), SquarePlacementModifier.of(), CountPlacementModifier.of(100)));

            register("atlantean_islands_feature_atlantis", ConfiguredFeaturesAtlantis.ATLANTEAN_ISLANDS_FEATURE_ATLANTIS.withPlacement(HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.getBottom(), YOffset.getTop())), SquarePlacementModifier.of(), CountPlacementModifier.of(45)));

            register("atlantean_volcano_feature_atlantis", ConfiguredFeaturesAtlantis.ATLANTEAN_VOLCANO_FEATURE_ATLANTIS.withPlacement(HeightRangePlacementModifier.of(UniformHeightProvider.create(YOffset.getBottom(), YOffset.getTop())), SquarePlacementModifier.of(), CountPlacementModifier.of(18)));

            register("jetstream_lake_feature_atlantis", ConfiguredFeaturesAtlantis.JETSTREAM_LAKE_FEATURE_ATLANTIS.withPlacement(CountPlacementModifier.of(10)));
            BiomeModifications.create(new Identifier(Reference.MODID, "feature_removal")).add(ModificationPhase.REMOVALS,
                    BiomeSelectors.foundInTheEnd().or(BiomeSelectors.foundInTheNether()),
                    biomeModificationContext -> {
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, UNDERWATER_FLOWER_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, UNDERWATER_MUSHROOM_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, SHELL_BLOCK_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.RAW_GENERATION, ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.RAW_GENERATION, ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.LAKES, JETSTREAM_LAKE_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, ATLANTEAN_TREE_KEY);
            });
        }

        private static void register(String name, PlacedFeature feature) {
            Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Reference.MODID, name), feature);
        }
    }
}
