package com.mystic.atlantis.structures;

import com.mystic.atlantis.util.Reference;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AtlantisStructures {
    public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Reference.MODID);

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> OYSTER_STRUCTURE = DEFERRED_REGISTRY_STRUCTURE.register("oyster_structure", OysterStructure::new);
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATLANTEAN_FOUNTAIN = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_fountain", AtlanteanFountain::new);
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATLANTEAN_HOUSE_1 = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_house_1", AtlantisHouse1::new);
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATLANTEAN_HOUSE_3 = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_house_3", AtlantisHouse3::new);
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATLANTEAN_TOWER = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_tower", AtlantisTower::new);

}
