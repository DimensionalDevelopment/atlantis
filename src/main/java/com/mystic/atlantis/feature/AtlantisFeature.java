package com.mystic.atlantis.feature;

import com.mystic.atlantis.feature.trees.AtlanteanPalmTree;
import com.mystic.atlantis.feature.trees.AtlanteanTree;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AtlantisFeature {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Reference.MODID);

    public static void init(IEventBus bus) {
        FEATURES.register(bus);
    }

    public static final Supplier<Feature<NoneFeatureConfiguration>> UNDERWATER_FLOWER_ATLANTIS = register(
            "underwater_flower_atlantis", ()->new UnderwaterFlowerAtlantis(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> GARDEN_FOLIAGE_PLACER_ATLANTIS = register(
            "garden_foliage_placer_atlantis", ()-> new GardenFoliagePlacerAtlantis(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> SHELL_BLOCK_FEATURE = register(
            "shell_block_feature_atlantis",()-> new ShellBlockFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> ATLANTEAN_ISLANDS = register(
            "atlantean_islands_feature_atlantis",()-> new AtlanteanIslands(NoneFeatureConfiguration.CODEC));
    public static final Supplier< Feature<LakeFeature.Configuration>> JETSTREAM_LAKE_FEATURE_ATLANTIS =  register(
            "jetstream_lake_feature_atlantis",()-> new LakeFeature(LakeFeature.Configuration.CODEC));
    public static final Supplier< Feature<LakeFeature.Configuration>> SALTY_SEA_LAKE_FEATURE_ATLANTIS =  register(
            "salty_sea_lake_feature_atlantis",()-> new LakeFeature(LakeFeature.Configuration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> ATLANTEAN_VOLCANO = register(
            "atlantean_volcano_feature_atlantis",()-> new AtlanteanVolcano(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> UNDERWATER_MUSHROOM_ATLANTIS = register(
            "underwater_mushroom_atlantis",()-> new UnderwaterMushroomAtlantis(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<TreeConfiguration>> ATLANTEAN_TREE_ATLANTIS = register(
            "atlantean_tree_atlantis", () -> new AtlanteanTree(TreeConfiguration.CODEC));
    public static final Supplier<Feature<TreeConfiguration>> ATLANTEAN_PALM_TREE_ATLANTIS = register(
            "atlantean_palm_tree_atlantis", () -> new AtlanteanPalmTree(TreeConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> ATLANTEAN_GLOWSTONES_FEATURE = register(
            "atlantean_glowstones_feature_atlantis", () -> new AtlanteanGlowstonesFeature(NoneFeatureConfiguration.CODEC));

    public static <T extends FeatureConfiguration> Supplier<Feature<T>> register(String id, Supplier<Feature<T>> t) {
        return FEATURES.register(id, t);
    }
}

