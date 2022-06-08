package com.mystic.atlantis.configfeature;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AtlantisFeature {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> UNDERWATER_FLOWER_ATLANTIS = register(
            "underwater_flower_atlantis", ()->new UnderwaterFlowerAtlantis(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GARDEN_FOLIAGE_PLACER_ATLANTIS = register(
            "garden_foliage_placer_atlantis", ()-> new GardenFoliagePlacerAtlantis(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SHELL_BLOCK_FEATURE = register(
            "shell_block_feature_atlantis",()-> new ShellBlockFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ATLANTEAN_ISLANDS = register(
            "atlantean_islands_feature_atlantis",()-> new AtlanteanIslands(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject< Feature<LakeFeature.Configuration>> JETSTREAM_LAKE_FEATURE_ATLANTIS =  register(
            "jetstream_lake_feature_atlantis",()-> new LakeFeature(LakeFeature.Configuration.CODEC)); //TODO: Update
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ATLANTEAN_VOLCANO = register(
            "atlantean_volcano_feature_atlantis",()-> new AtlanteanVolcano(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> UNDERWATER_MUSHROOM_ATLANTIS = register(
            "underwater_mushroom_atlantis",()-> new UnderwaterMushroomAtlantis(NoneFeatureConfiguration.CODEC));

    public static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> registerTree(String name, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MODID, name),
                feature);
    }

    public static <T extends FeatureConfiguration> RegistryObject<Feature<T>> register(String id, Supplier<Feature<T>> t) {
        return FEATURES.register(id, t);
    }

    public static final class ConfiguredFeaturesAtlantis {
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> UNDERWATER_FLOWER_ATLANTIS = FeatureUtils.register("underwater_flower_atlantis", AtlantisFeature.UNDERWATER_FLOWER_ATLANTIS.get());
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> UNDERWATER_MUSHROOM_ATLANTIS = FeatureUtils.register("underwater_mushroom_atlantis", AtlantisFeature.UNDERWATER_MUSHROOM_ATLANTIS.get());
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> SHELL_BLOCK_FEATURE_ATLANTIS = FeatureUtils.register("shell_block_feature_atlantis", AtlantisFeature.SHELL_BLOCK_FEATURE.get());
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> GARDEN_FOLIAGE_PLACER_ATLANTIS = FeatureUtils.register("garden_foliage_placer_atlantis", AtlantisFeature.GARDEN_FOLIAGE_PLACER_ATLANTIS.get());
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS = FeatureUtils.register("atlantean_islands_feature_atlantis", AtlantisFeature.ATLANTEAN_ISLANDS.get());
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS = FeatureUtils.register("atlantean_volcano_feature_atlantis", AtlantisFeature.ATLANTEAN_VOLCANO.get());
        public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> JETSTREAM_LAKE_FEATURE_ATLANTIS = FeatureUtils.register("jetstream_lake", Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(FluidInit.JETSTREAM_WATER.get().defaultBlockState()), BlockStateProvider.simple(Blocks.STONE.defaultBlockState())));
        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> ATLANTEAN_TREE = FeatureUtils.register("atlantean_tree", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockInit.ATLANTEAN_LOGS.get()),
                new StraightTrunkPlacer(5, 6, 3),
                BlockStateProvider.simple(BlockInit.ATLANTEAN_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                new TwoLayersFeatureSize(1, 0, 2)).build());

//        public static final ResourceKey<PlacedFeature> GARDEN_FOLIAGE_PLACER_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "garden_foliage_placer_atlantis"));
//        public static final ResourceKey<PlacedFeature> UNDERWATER_FLOWER_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "underwater_flower_atlantis"));
//        public static final ResourceKey<PlacedFeature> UNDERWATER_MUSHROOM_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "underwater_mushroom_atlantis"));
//        public static final ResourceKey<PlacedFeature> SHELL_BLOCK_FEATURE_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "shell_block_feature_atlantis"));
//        public static final ResourceKey<PlacedFeature> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "atlantean_islands_feature_atlantis"));
//        public static final ResourceKey<PlacedFeature> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "atlantean_volcano_feature_atlantis"));
//        public static final ResourceKey<PlacedFeature> JETSTREAM_LAKE_FEATURE_ATLANTIS_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "jetstream_lake_feature_atlantis"));
//        public static final ResourceKey<PlacedFeature> ATLANTEAN_TREE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "atlantean_tree"));

        public static final Holder<PlacedFeature> GARDEN_FOLIAGE_PLACER_ATLANTIS_PLACED = PlacementUtils.register("garden_foliage_placer_atlantis", ConfiguredFeaturesAtlantis.GARDEN_FOLIAGE_PLACER_ATLANTIS, HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(200));
        public static final Holder<PlacedFeature> UNDERWATER_FLOWER_ATLANTIS_PLACED = PlacementUtils.register("underwater_flower_atlantis", ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS, HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(100));
        public static final Holder<PlacedFeature> UNDERWATER_MUSHROOM_ATLANTIS_PLACED = PlacementUtils.register("underwater_mushroom_atlantis", ConfiguredFeaturesAtlantis.UNDERWATER_MUSHROOM_ATLANTIS, HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(100));
        public static final Holder<PlacedFeature> SHELL_BLOCK_FEATURE_ATLANTIS_PLACED = PlacementUtils.register("shell_block_feature_atlantis", ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE_ATLANTIS, HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(100));
        public static final Holder<PlacedFeature> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_PLACED = PlacementUtils.register("atlantean_islands_feature_atlantis", ConfiguredFeaturesAtlantis.ATLANTEAN_ISLANDS_FEATURE_ATLANTIS, HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(45));
        public static final Holder<PlacedFeature> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_PLACED = PlacementUtils.register("atlantean_volcano_feature_atlantis", ConfiguredFeaturesAtlantis.ATLANTEAN_VOLCANO_FEATURE_ATLANTIS, HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top())), InSquarePlacement.spread(), CountPlacement.of(18));
        public static final Holder<PlacedFeature> JETSTREAM_LAKE_FEATURE_ATLANTIS_PLACED = PlacementUtils.register("jetstream_lake", ConfiguredFeaturesAtlantis.JETSTREAM_LAKE_FEATURE_ATLANTIS, (CountPlacement.of(10)));
        public static final Holder<PlacedFeature> ATLANTEAN_TREE_PLACED = PlacementUtils.register("atlantean_tree", ConfiguredFeaturesAtlantis.ATLANTEAN_TREE);
        
        public static void registerConfiguredFeatures() {
            register("garden_foliage_placer_atlantis", GARDEN_FOLIAGE_PLACER_ATLANTIS_PLACED.value());
            register("underwater_flower_atlantis", UNDERWATER_FLOWER_ATLANTIS_PLACED.value());
            register("underwater_mushroom_atlantis", UNDERWATER_FLOWER_ATLANTIS_PLACED.value());
            register("shell_block_feature_atlantis", SHELL_BLOCK_FEATURE_ATLANTIS_PLACED.value());
            register("atlantean_islands_feature_atlantis", ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_PLACED.value());
            register("atlantean_volcano_feature_atlantis", ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_PLACED.value());
            register("jetstream_lake_feature_atlantis", JETSTREAM_LAKE_FEATURE_ATLANTIS_PLACED.value());
        }

        private static void register(String name, PlacedFeature feature) {
            Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Reference.MODID, name), feature);
        }
    }
}

