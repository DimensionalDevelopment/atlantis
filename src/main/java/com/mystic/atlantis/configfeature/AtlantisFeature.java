package com.mystic.atlantis.configfeature;

import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import net.minecraft.world.gen.feature.*;

public class AtlantisFeature {
    public static final Feature<DefaultFeatureConfig> UNDERWATER_FLOWER_ATLANTIS = register(
            "underwater_flower_atlantis", new UnderwaterFlowerAtlantis(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ALGAE_FEATURE_ATLANTIS = register(
            "algae_feature_atlantis", new AlgaeFeatureAtlantis(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SHELL_BLOCK_FEATURE = register(
            "shell_block_feature_atlantis", new ShellBlockFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ATLANTEAN_ISLANDS = register(
            "atlantean_islands_feature_atlantis", new AtlanteanIslands(DefaultFeatureConfig.CODEC));
    public static final LakeFeature JETSTREAM_LAKE_FEATURE_ATLANTIS = (LakeFeature) register(
            "jetstream_lake_feature_atlantis", new LakeFeature(SingleStateFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ATLANTEAN_VOLCANO = register(
            "atlantean_volcano_feature_atlantis", new AtlanteanVolcano(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> UNDERWATER_MUSHROOM_ATLANTIS = register(
            "underwater_mushroom_atlantis", new UnderwaterMushroomAtlantis(DefaultFeatureConfig.CODEC));

    public static <T extends FeatureConfig> Feature<T> register(String id, Feature<T> t) {
        Registry.register(Registry.FEATURE, new Identifier(Reference.MODID, id), t);
        return t;
    }

    public static final class ConfiguredFeaturesAtlantis {
        public static final ConfiguredFeature<?, ?> UNDERWATER_FLOWER_ATLANTIS = AtlantisFeature.UNDERWATER_FLOWER_ATLANTIS.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> UNDERWATER_MUSHROOM_ATLANTIS = AtlantisFeature.UNDERWATER_MUSHROOM_ATLANTIS.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> ALGAE_FEATURE_ATLANTIS = AtlantisFeature.ALGAE_FEATURE_ATLANTIS.configure(FeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> SHELL_BLOCK_FEATURE_ATLANTIS = AtlantisFeature.SHELL_BLOCK_FEATURE.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS = AtlantisFeature.ATLANTEAN_ISLANDS.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS = AtlantisFeature.ATLANTEAN_VOLCANO.configure(DefaultFeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> JETSTREAM_LAKE_FEATURE_ATLANTIS = AtlantisFeature.JETSTREAM_LAKE_FEATURE_ATLANTIS.configure(new SingleStateFeatureConfig(FluidInit.JETSTREAM_WATER.getDefaultState()));

        public static final RegistryKey<ConfiguredFeature<?,?>> UNDERWATER_FLOWER_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "underwater_flower_atlantis"));
        public static final RegistryKey<ConfiguredFeature<?,?>> UNDERWATER_MUSHROOM_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "underwater_mushroom_atlantis"));
        public static final RegistryKey<ConfiguredFeature<?,?>> ALGAE_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "algae_feature_atlantis"));
        public static final RegistryKey<ConfiguredFeature<?,?>> SHELL_BLOCK_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "shell_block_feature_atlantis"));
        public static final RegistryKey<ConfiguredFeature<?,?>> ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "atlantean_islands_feature_atlantis"));
        public static final RegistryKey<ConfiguredFeature<?,?>> ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "atlantean_volcano_feature_atlantis"));
        public static final RegistryKey<ConfiguredFeature<?,?>> JETSTREAM_LAKE_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "jetstream_lake_feature_atlantis"));

        public static void registerConfiguredFeatures() {
            register("underwater_flower_atlantis", ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(100));

            register("underwater_mushroom_atlantis", ConfiguredFeaturesAtlantis.UNDERWATER_MUSHROOM_ATLANTIS.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(100));

            register("algae_feature_atlantis", ConfiguredFeaturesAtlantis.ALGAE_FEATURE_ATLANTIS.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(80));

            register("shell_block_feature_atlantis", ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE_ATLANTIS.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(100));

            register("atlantean_islands_feature_atlantis", ConfiguredFeaturesAtlantis.ATLANTEAN_ISLANDS_FEATURE_ATLANTIS.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(45));

            register("atlantean_volcano_feature_atlantis", ConfiguredFeaturesAtlantis.ATLANTEAN_VOLCANO_FEATURE_ATLANTIS.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(18));

            register("jetstream_lake_feature_atlantis", ConfiguredFeaturesAtlantis.JETSTREAM_LAKE_FEATURE_ATLANTIS.repeat(10));
            BiomeModifications.create(new Identifier(Reference.MODID, "feature_removal")).add(ModificationPhase.REMOVALS,
                    BiomeSelectors.foundInTheEnd().or(BiomeSelectors.foundInTheNether()),
                    biomeModificationContext -> {
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, UNDERWATER_FLOWER_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, UNDERWATER_MUSHROOM_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, ALGAE_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.SURFACE_STRUCTURES, SHELL_BLOCK_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.RAW_GENERATION, ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.RAW_GENERATION, ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.LAKES, JETSTREAM_LAKE_FEATURE_ATLANTIS_KEY);
            });
        }

        private static <FC extends FeatureConfig> void register(String name, ConfiguredFeature<FC, ?> feature) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Reference.MODID, name), feature);
        }
    }
}
