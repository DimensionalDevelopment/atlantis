package com.mystic.atlantis.configfeature;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AtlantisFeature
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MODID);

    public static final RegistryObject<Feature<FeatureSpreadConfig>> UNDERWATER_FLOWER_ATLANTIS = FEATURES.register(
            "underwater_flower_atlantis", () -> new UnderwaterFlowerAtlantis(FeatureSpreadConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> ALGAE_FEATURE_ATLANTIS = FEATURES.register(
            "algae_feature_atlantis", () -> new AlgaeFeatureAtlantis(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<FeatureSpreadConfig>> SHELL_BLOCK_FEATURE = FEATURES.register(
            "shell_block_feature", () -> new ShellBlockFeature(FeatureSpreadConfig.CODEC));

    public static final int l = 20;

    public static final class ConfiguredFeaturesAtlantis {
        public static final ConfiguredFeature<?, ?> UNDERWATER_FLOWER_ATLANTIS = AtlantisFeature.UNDERWATER_FLOWER_ATLANTIS.get().withConfiguration(new FeatureSpreadConfig(FeatureSpread.func_242252_a(l)));

        public static final ConfiguredFeature<?, ?> ALGAE_FEATURE_ATLANTIS = AtlantisFeature.ALGAE_FEATURE_ATLANTIS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

        public static final ConfiguredFeature<?, ?> SHELL_BLOCK_FEATURE = AtlantisFeature.SHELL_BLOCK_FEATURE.get().withConfiguration(new FeatureSpreadConfig(FeatureSpread.func_242252_a(l)));

        private static final ConfiguredFeature<?, ?> AQUAMARINE_ORE_FEATURE = Feature.ORE
                .withConfiguration(new OreFeatureConfig(
                        OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                        BlockInit.AQUAMARINE_ORE.get().getDefaultState(),
                        9)) // vein size
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(
                        0,
                        0,
                        64)))
                .square()
                .func_242731_b(20); // number of veins per chunk

        public static void registerConfiguredFeatures() {
            register("underwater_flower_altantis", ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS.range(32).square().func_242731_b(100));

            register("algae_feature_altantis", ConfiguredFeaturesAtlantis.ALGAE_FEATURE_ATLANTIS.range(32).square().func_242731_b(80));

            register("shell_block_feature", ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE.range(32).square().func_242731_b(100));
        }

        public static void generateUnderwaterFlowerFeature(BiomeLoadingEvent event) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS);
        }

        public static void generateAlgaeFeatureAltantis(BiomeLoadingEvent event){
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeaturesAtlantis.ALGAE_FEATURE_ATLANTIS);
        }

        public static void generateShellBlockFeature(BiomeLoadingEvent event) {
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE);
        }

        public static void generateAquamarineOreFeature(BiomeLoadingEvent event) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ConfiguredFeaturesAtlantis.AQUAMARINE_ORE_FEATURE);
        }

        private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> feature) {
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MODID, name), feature);
        }
    }
}
