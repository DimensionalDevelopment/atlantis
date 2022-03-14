package com.mystic.atlantis.structures;

import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraftforge.registries.ForgeRegistries;

public class AtlantisConfiguredStructures {
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_OYSTER_STRUCTURE = AtlantisStructures.OYSTER_STRUCTURE.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_FOUNTAIN = AtlantisStructures.ATLANTEAN_FOUNTAIN.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_HOUSE_1 = AtlantisStructures.ATLANTEAN_HOUSE_1.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_HOUSE_3 = AtlantisStructures.ATLANTEAN_HOUSE_3.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ATLANTEAN_TOWER = AtlantisStructures.ATLANTEAN_TOWER.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(Reference.MODID, "configured_oyster_structure"), CONFIGURED_OYSTER_STRUCTURE);
        Registry.register(registry, new ResourceLocation(Reference.MODID, "configured_atlantean_fountain"), CONFIGURED_ATLANTEAN_FOUNTAIN);
        Registry.register(registry, new ResourceLocation(Reference.MODID, "configured_atlantean_house_1"), CONFIGURED_ATLANTEAN_HOUSE_1);
        Registry.register(registry, new ResourceLocation(Reference.MODID, "configured_atlantean_house_3"), CONFIGURED_ATLANTEAN_HOUSE_3);
        Registry.register(registry, new ResourceLocation(Reference.MODID, "configured_atlantean_tower"), CONFIGURED_ATLANTEAN_TOWER);
    }
}
