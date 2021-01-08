package com.mystic.atlantis.configfeature;

import com.mystic.atlantis.biomes.AtlantisBiomes;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class AtlantisFeature
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MODID);

    public static final RegistryObject<Feature<CountConfig>> UNDERWATER_FLOWER_ATLANTIS = FEATURES.register(
            "underwater_flower_atlantis", () -> new UnderwaterFlowerAtlantis(CountConfig.CODEC));
    public static final RegistryObject<Feature<DefaultFeatureConfig>> ALGAE_FEATURE_ATLANTIS = FEATURES.register(
            "algae_feature_atlantis", () -> new AlgaeFeatureAtlantis(DefaultFeatureConfig.CODEC));
    public static final RegistryObject<Feature<CountConfig>> SHELL_BLOCK_FEATURE = FEATURES.register(
            "shell_block_feature", () -> new ShellBlockFeature(CountConfig.CODEC));
    public static final RegistryObject<Feature<AtlantisOreFeatureConfig>> AQUAMARINE_ORE_FEATURE = FEATURES.register(
            "aquamarine_ore_feature", () -> new AquamarineOreFeature(AtlantisOreFeatureConfig.CODEC));

    public static final int l = 20;

    public static final class ConfiguredFeaturesAtlantis {
        public static final ConfiguredFeature<?, ?> UNDERWATER_FLOWER_ATLANTIS = AtlantisFeature.UNDERWATER_FLOWER_ATLANTIS.get().configure(new CountConfig(UniformIntDistribution.of(l)));

        public static final ConfiguredFeature<?, ?> ALGAE_FEATURE_ATLANTIS = AtlantisFeature.ALGAE_FEATURE_ATLANTIS.get().configure(FeatureConfig.DEFAULT);

        public static final ConfiguredFeature<?, ?> SHELL_BLOCK_FEATURE = AtlantisFeature.SHELL_BLOCK_FEATURE.get().configure(new CountConfig(UniformIntDistribution.of(l)));

        public static final ConfiguredFeature<?, ?> AQUAMARINE_ORE_FEATURE = AtlantisFeature.AQUAMARINE_ORE_FEATURE.get().configure(new AtlantisOreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, BlockInit.AQUAMARINE_ORE.get().getDefaultState(), 30, "Aquamarine_Ore_Feature_Configuration"));

        public static void registerConfiguredFeatures() {
            register("underwater_flower_altantis", ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS.rangeOf(32).spreadHorizontally().repeat(100));

            register("algae_feature_altantis", ConfiguredFeaturesAtlantis.ALGAE_FEATURE_ATLANTIS.rangeOf(32).spreadHorizontally().repeat(80));

            register("shell_block_feature", ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE.rangeOf(32).spreadHorizontally().repeat(100));

            register("aquamarine_ore_feature", ConfiguredFeaturesAtlantis.AQUAMARINE_ORE_FEATURE.rangeOf(15).spreadHorizontally().repeat(30));
        }

        public static void generateUnderwaterFlowerFeature(BiomeLoadingEvent event) {
            event.getGeneration().feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS);
        }

        public static void generateAlgaeFeatureAltantis(BiomeLoadingEvent event){
            event.getGeneration().feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeaturesAtlantis.ALGAE_FEATURE_ATLANTIS);
        }

        public static void generateShellBlockFeature(BiomeLoadingEvent event) {
            event.getGeneration().feature(GenerationStep.Feature.SURFACE_STRUCTURES, ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE);
        }

        public static void generateAquamarineOreFeature(BiomeLoadingEvent event) {
            event.getGeneration().feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeaturesAtlantis.AQUAMARINE_ORE_FEATURE);
        }

        private static <FC extends FeatureConfig> void register(String name, ConfiguredFeature<FC, ?> feature) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Reference.MODID, name), feature);
        }
    }
}
