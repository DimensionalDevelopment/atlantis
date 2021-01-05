package com.mystic.atlantis.structures;

import com.mystic.atlantis.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class AtlantisConfiguredStructures {
    public static StructureFeature<?, ?> CONFIGURED_OYSTER_STRUCTURE = AtlantisStructures.OYSTER_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(Reference.MODID, "structures/oyster_structure"), CONFIGURED_OYSTER_STRUCTURE);

        FlatGenerationSettings.STRUCTURES.put(AtlantisStructures.OYSTER_STRUCTURE, CONFIGURED_OYSTER_STRUCTURE);
    }
}
