package com.mystic.atlantis.configfeature;

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
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.HeightmapDecorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.heightprovider.HeightProviderType;

import java.util.Random;

public class AtlantisFeature {
    public static final Feature<CountConfig> UNDERWATER_FLOWER_ATLANTIS = register(
            "underwater_flower_atlantis", new UnderwaterFlowerAtlantis(CountConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ALGAE_FEATURE_ATLANTIS = register(
            "algae_feature_atlantis", new AlgaeFeatureAtlantis(DefaultFeatureConfig.CODEC));
    public static final Feature<CountConfig> SHELL_BLOCK_FEATURE = register(
            "shell_block_feature", new ShellBlockFeature(CountConfig.CODEC));

    public static <T extends FeatureConfig> Feature<T> register(String id, Feature<T> t) {
        Registry.register(Registry.FEATURE, new Identifier(Reference.MODID, id), t);
        return t;
    }
    
    public static final int l = 20;
    public static final int k = 30;

    public static final class ConfiguredFeaturesAtlantis {
        public static final ConfiguredFeature<?, ?> UNDERWATER_FLOWER_ATLANTIS = AtlantisFeature.UNDERWATER_FLOWER_ATLANTIS.configure(new CountConfig(UniformIntProvider.create(l, k)));
        public static final ConfiguredFeature<?, ?> ALGAE_FEATURE_ATLANTIS = AtlantisFeature.ALGAE_FEATURE_ATLANTIS.configure(FeatureConfig.DEFAULT);
        public static final ConfiguredFeature<?, ?> SHELL_BLOCK_FEATURE = AtlantisFeature.SHELL_BLOCK_FEATURE.configure(new CountConfig(UniformIntProvider.create(l, k)));

        public static final RegistryKey<ConfiguredFeature<?,?>> UNDERWATER_FLOWER_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "underwater_flower_altantis"));
        public static final RegistryKey<ConfiguredFeature<?,?>> ALGAE_FEATURE_ATLANTIS_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "algae_feature_altantis"));
        public static final RegistryKey<ConfiguredFeature<?,?>> SHELL_BLOCK_FEATURE_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Reference.MODID, "shell_block_feature"));

        public static void registerConfiguredFeatures() {
            register("underwater_flower_altantis", ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(100));

            register("algae_feature_altantis", ConfiguredFeaturesAtlantis.ALGAE_FEATURE_ATLANTIS.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(80));

            register("shell_block_feature", ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE.uniformRange(YOffset.getBottom(), YOffset.getTop()).spreadHorizontally().repeat(100));

            BiomeModifications.create(new Identifier(Reference.MODID, "feature_removal")).add(ModificationPhase.REMOVALS,
                    BiomeSelectors.foundInTheEnd().or(BiomeSelectors.foundInTheNether()),
                    biomeModificationContext -> {
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, UNDERWATER_FLOWER_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.VEGETAL_DECORATION, ALGAE_FEATURE_ATLANTIS_KEY);
                biomeModificationContext.getGenerationSettings().removeFeature(GenerationStep.Feature.SURFACE_STRUCTURES, SHELL_BLOCK_FEATURE_KEY);
            });
        }

        private static <FC extends FeatureConfig> void register(String name, ConfiguredFeature<FC, ?> feature) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Reference.MODID, name), feature);
        }
    }
}
