package com.mystic.atlantis.structures;

import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class AtlantisConfiguredStructures {
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_OYSTER_STRUCTURE = AtlantisStructures.OYSTER_STRUCTURE.get().configure(FeatureConfig.DEFAULT);

    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_FOUNTAIN = AtlantisStructures.ATLANTEAN_FOUNTAIN.get().configure(FeatureConfig.DEFAULT);

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(Reference.MODID, "configured_oyster_structure"), CONFIGURED_OYSTER_STRUCTURE);
        Registry.register(registry, new Identifier(Reference.MODID, "configured_atlantean_fountain"), CONFIGURED_ATLANTEAN_FOUNTAIN);

        FlatChunkGeneratorConfig.STRUCTURE_TO_FEATURES.put(AtlantisStructures.OYSTER_STRUCTURE.get(), CONFIGURED_OYSTER_STRUCTURE);
        FlatChunkGeneratorConfig.STRUCTURE_TO_FEATURES.put(AtlantisStructures.ATLANTEAN_FOUNTAIN.get(), CONFIGURED_ATLANTEAN_FOUNTAIN);
    }
}
