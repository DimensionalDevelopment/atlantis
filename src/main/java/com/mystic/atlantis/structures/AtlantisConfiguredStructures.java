package com.mystic.atlantis.structures;

import com.mystic.atlantis.util.Reference;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class AtlantisConfiguredStructures {
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_OYSTER_STRUCTURE = AtlantisStructures.OYSTER_STRUCTURE.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_FOUNTAIN = AtlantisStructures.ATLANTEAN_FOUNTAIN.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_HOUSE_1 = AtlantisStructures.ATLANTEAN_HOUSE_1.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_HOUSE_3 = AtlantisStructures.ATLANTEAN_HOUSE_3.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_TOWER = AtlantisStructures.ATLANTEAN_TOWER.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 0));

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(Reference.MODID, "configured_oyster_structure"), CONFIGURED_OYSTER_STRUCTURE);
        Registry.register(registry, new Identifier(Reference.MODID, "configured_atlantean_fountain"), CONFIGURED_ATLANTEAN_FOUNTAIN);
        Registry.register(registry, new Identifier(Reference.MODID, "configured_atlantean_house_1"), CONFIGURED_ATLANTEAN_HOUSE_1);
        Registry.register(registry, new Identifier(Reference.MODID, "configured_atlantean_house_3"), CONFIGURED_ATLANTEAN_HOUSE_3);
        Registry.register(registry, new Identifier(Reference.MODID, "configured_atlantean_tower"), CONFIGURED_ATLANTEAN_TOWER);
    }
}
