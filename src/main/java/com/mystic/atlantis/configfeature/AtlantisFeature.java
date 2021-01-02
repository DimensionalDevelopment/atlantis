package com.mystic.atlantis.configfeature;

import com.mystic.atlantis.biomes.AtlantisBiomes;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AtlantisFeature
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MODID);

    public static final RegistryObject<Feature<FeatureSpreadConfig>> UNDERWATER_FLOWER_ATLANTIS = FEATURES.register(
            "underwater_flower_atlantis", () -> new UnderwaterFlowerAtlantis(FeatureSpreadConfig.CODEC));

    public static final int l = 20;
    public static final class ConfiguredFeaturesAtlantis {
        public static final ConfiguredFeature<?, ?> UNDERWATER_FLOWER_ATLANTIS = AtlantisFeature.UNDERWATER_FLOWER_ATLANTIS.get().withConfiguration(new FeatureSpreadConfig(FeatureSpread.func_242252_a(l)));

        public static void registerConfiguredFeatures() {
            register("underwater_flower_altantis", ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS.range(32).square().func_242731_b(100));
        }

        public static void generateUnderwaterFlowerFeature(BiomeLoadingEvent event) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS);
        }

        private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> feature) {
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MODID, name), feature);
        }
    }
}
