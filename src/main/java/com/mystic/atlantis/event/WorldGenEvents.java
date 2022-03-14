package com.mystic.atlantis.event;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Reference.MODID)
public class WorldGenEvents {

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        List<OreConfiguration.TargetBlockState> ORE_IRON_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlockInit.AQUAMARINE_ORE.get().defaultBlockState()));
        ConfiguredFeature<?,?> AQUAMARINE_ORE = FeatureUtils.register("ore_money", Feature.ORE.configured(new OreConfiguration(ORE_IRON_TARGET_LIST, 9)));
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        PlacedFeature ORE_AQUAMARINE_OVERWORLD = PlacementUtils.register("ore_money", AQUAMARINE_ORE.placed(commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.top()))));

        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_AQUAMARINE_OVERWORLD);

        event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AtlantisFeature.ConfiguredFeaturesAtlantis.GARDEN_FOLIAGE_PLACER_ATLANTIS_PLACED);
        event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AtlantisFeature.ConfiguredFeaturesAtlantis.UNDERWATER_FLOWER_ATLANTIS_PLACED);
        event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AtlantisFeature.ConfiguredFeaturesAtlantis.UNDERWATER_MUSHROOM_ATLANTIS_PLACED);
        event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AtlantisFeature.ConfiguredFeaturesAtlantis.SHELL_BLOCK_FEATURE_ATLANTIS_PLACED);
        event.getGeneration().addFeature(GenerationStep.Decoration.RAW_GENERATION, AtlantisFeature.ConfiguredFeaturesAtlantis.ATLANTEAN_ISLANDS_FEATURE_ATLANTIS_PLACED);
        event.getGeneration().addFeature(GenerationStep.Decoration.RAW_GENERATION, AtlantisFeature.ConfiguredFeaturesAtlantis.ATLANTEAN_VOLCANO_FEATURE_ATLANTIS_PLACED);
        event.getGeneration().addFeature(GenerationStep.Decoration.LAKES, AtlantisFeature.ConfiguredFeaturesAtlantis.JETSTREAM_LAKE_FEATURE_ATLANTIS_PLACED);
        event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AtlantisFeature.ConfiguredFeaturesAtlantis.ATLANTEAN_TREE_PLACED);
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }
    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }
}