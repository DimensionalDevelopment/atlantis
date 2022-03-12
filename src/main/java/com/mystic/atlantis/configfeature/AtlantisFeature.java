package com.mystic.atlantis.configfeature;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AtlantisFeature {
    public static final Feature<NoneFeatureConfiguration> UNDERWATER_FLOWER_ATLANTIS = register(
            "underwater_flower_atlantis", new UnderwaterFlowerAtlantis(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> GARDEN_FOLIAGE_PLACER_ATLANTIS = register(
            "garden_foliage_placer_atlantis", new GardenFoliagePlacerAtlantis(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> SHELL_BLOCK_FEATURE = register(
            "shell_block_feature_atlantis", new ShellBlockFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> ATLANTEAN_ISLANDS = register(
            "atlantean_islands_feature_atlantis", new AtlanteanIslands(NoneFeatureConfiguration.CODEC));
    public static final LakeFeature JETSTREAM_LAKE_FEATURE_ATLANTIS = (LakeFeature) register(
            "jetstream_lake_feature_atlantis", new LakeFeature(LakeFeature.Configuration.CODEC)); //TODO: Update
    public static final Feature<NoneFeatureConfiguration> ATLANTEAN_VOLCANO = register(
            "atlantean_volcano_feature_atlantis", new AtlanteanVolcano(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> UNDERWATER_MUSHROOM_ATLANTIS = register(
            "underwater_mushroom_atlantis", new UnderwaterMushroomAtlantis(NoneFeatureConfiguration.CODEC));

    public static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> registerTree(String name, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MODID, name),
                feature);
    }

    public static <T extends FeatureConfiguration> Feature<T> register(String id, Feature<T> t) {
        Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, id), t);
        return t;
    }

    public static final class ConfiguredFeaturesAtlantis {
        public static final ConfiguredFeature<?, ?> UNDERWATER_FLOWER_ATLANTIS = AtlantisFeature.UNDERWATER_FLOWER_ATLANTIS.configured(NoneFeatureConfiguration.NONE);
        public static final ConfiguredFeature<?, ?> UNDERWATER_MUSHROOM_ATLANTIS = AtlantisFeature.UNDERWATER_MUSHROOM_ATLANTIS.configured(NoneFeatureConfiguration.NONE);
        public static final ConfiguredFeature<?, ?> SHELL_BLOCK_FEATURE_ATLANTIS = AtlantisFeature.SHELL_BLOCK_FEATURE.configured(NoneFeatureConfiguration.NONE);
        public static final ConfiguredFeature<?, ?> GARDEN_FOLIAGE_PLACER_ATLANTIS = AtlantisFeature.GARDEN_FOLIAGE_PLACER_ATLANTIS.configured(NoneFeatureConfiguration.NONE);
        public static final ConfiguredFeature<?, ?> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS = AtlantisFeature.ATLANTEAN_ISLANDS.configured(NoneFeatureConfiguration.NONE);
        public static final ConfiguredFeature<?, ?> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS = AtlantisFeature.ATLANTEAN_VOLCANO.configured(NoneFeatureConfiguration.NONE);
        public static final ConfiguredFeature<?, ?> JETSTREAM_LAKE_FEATURE_ATLANTIS = AtlantisFeature.JETSTREAM_LAKE_FEATURE_ATLANTIS.configured(new LakeFeature.Configuration(BlockStateProvider.simple(FluidInit.JETSTREAM_WATER), BlockStateProvider.simple(Blocks.STONE)));
        public static final ConfiguredFeature<TreeConfiguration, ?> ATLANTEAN_TREE =
                registerTree("atlantean_tree", Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockInit.ATLANTEAN_LOGS),
                        new StraightTrunkPlacer(5, 6, 3),
                        BlockStateProvider.simple(BlockInit.ATLANTEAN_LEAVES),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                        new TwoLayersFeatureSize(1, 0, 2)).build()));

        public static final ResourceKey<PlacedFeature> GARDER_FOLIAGE_PLACER_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "garden_foliage_placer_atlantis"));
        public static final ResourceKey<PlacedFeature> UNDERWATER_FLOWER_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "underwater_flower_atlantis"));
        public static final ResourceKey<PlacedFeature> UNDERWATER_MUSHROOM_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "underwater_mushroom_atlantis"));
        public static final ResourceKey<PlacedFeature> SHELL_BLOCK_FEATURE_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "shell_block_feature_atlantis"));
        public static final ResourceKey<PlacedFeature> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "atlantean_islands_feature_atlantis"));
        public static final ResourceKey<PlacedFeature> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "atlantean_volcano_feature_atlantis"));
        public static final ResourceKey<PlacedFeature> JETSTREAM_LAKE_FEATURE_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "jetstream_lake_feature_atlantis"));
        public static final ResourceKey<PlacedFeature> ATLANTEAN_TREE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "atlantean_tree"));

        public static void registerConfiguredFeatures() {
            register("garden_foliage_placer_atlantis", ConfiguredFeaturesAtlantis.GARDEN_FOLIAGE_PLACER_ATLANTIS.placed(HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(200)));

            register("underwater_flower_atlantis", ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS.placed(HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(100)));

            register("underwater_mushroom_atlantis", ConfiguredFeaturesAtlantis.UNDERWATER_MUSHROOM_ATLANTIS.placed(HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(100)));

            register("shell_block_feature_atlantis", ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE_ATLANTIS.placed(HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(100)));

            register("atlantean_islands_feature_atlantis", ConfiguredFeaturesAtlantis.ATLANTEAN_ISLANDS_FEATURE_ATLANTIS.placed(HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(45)));

            register("atlantean_volcano_feature_atlantis", ConfiguredFeaturesAtlantis.ATLANTEAN_VOLCANO_FEATURE_ATLANTIS.placed(HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(18)));

            register("jetstream_lake_feature_atlantis", ConfiguredFeaturesAtlantis.JETSTREAM_LAKE_FEATURE_ATLANTIS.placed(CountPlacement.of(10)));

//            TODO: DO we need this?
//            BiomeModifications.create(new ResourceLocation(Reference.MODID, "feature_removal")).add(ModificationPhase.REMOVALS,
//                    BiomeSelectors.foundInTheEnd().or(BiomeSelectors.foundInTheNether()),
//                    biomeModificationContext -> {
//                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, GARDER_FOLIAGE_PLACER_ATLANTIS_KEY);
//                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, UNDERWATER_FLOWER_ATLANTIS_KEY);
//                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, UNDERWATER_MUSHROOM_ATLANTIS_KEY);
//                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, SHELL_BLOCK_FEATURE_ATLANTIS_KEY);
//                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.RAW_GENERATION, ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_KEY);
//                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.RAW_GENERATION, ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_KEY);
//                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.LAKES, JETSTREAM_LAKE_FEATURE_ATLANTIS_KEY);
//                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, ATLANTEAN_TREE_KEY);
//            });
        }

        private static void register(String name, PlacedFeature feature) {
            Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Reference.MODID, name), feature);
        }
    }
}
