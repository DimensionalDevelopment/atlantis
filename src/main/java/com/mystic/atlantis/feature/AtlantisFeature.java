package com.mystic.atlantis.feature;

import com.mystic.atlantis.feature.trees.NymphTree;
import com.mystic.atlantis.feature.trees.PalmTree;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AtlantisFeature {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Reference.MODID);

    public static void init(IEventBus bus) {
        FEATURES.register(bus);
    }

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SEABLOOM_FEATURE = register(
            "seabloom_feature", ()->new SeabloomFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> GARDEN_FOLIAGE_FEATURE = register(
            "garden_foliage_feature", ()-> new GardenFoliageFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SHELL_BLOCK_FEATURE = register(
            "shell_block_feature",()-> new ShellBlockFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> ISLANDS_FEATURE = register(
            "islands_feature",()-> new IslandsFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<LakeFeature.Configuration>> JETSTREAM_LAKE_FEATURE =  register(
            "jetstream_lake_feature",()-> new LakeFeature(LakeFeature.Configuration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<LakeFeature.Configuration>> SALTY_SEA_LAKE_FEATURE =  register(
            "salty_sea_lake_feature",()-> new LakeFeature(LakeFeature.Configuration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> VOLCANOES_FEATURE = register(
            "volcanoes_feature",()-> new VolcanoesFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SEASHROOM_FEATURE = register(
            "seashroom_feature",()-> new SeashroomFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> NYMPH_TREE_FEATURE = register(
            "nymph_tree_feature", () -> new NymphTree(TreeConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> PALM_TREE_FEATURE = register(
            "palm_tree_feature", () -> new PalmTree(TreeConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> GLOWSTONES_FEATURE = register(
            "glowstones_feature", () -> new GlowstonesFeature(NoneFeatureConfiguration.CODEC));

    public static <T extends FeatureConfiguration> DeferredHolder<Feature<?>, Feature<T>> register(String id, Supplier<Feature<T>> t) {
        return FEATURES.register(id, t);
    }
}

