package com.mystic.atlantis.structures;

import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AtlantisStructures {
    public static final DeferredRegister<StructureType<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(Registries.STRUCTURE_TYPE, Reference.MODID);

    public static final Supplier<StructureType<AtlanteanCityStructure>> ATLANTEAN_CITY = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_city", () -> (StructureType<AtlanteanCityStructure>) () -> AtlanteanCityStructure.CODEC);
    public static final Supplier<StructureType<AtlanteanFountain>> ATLANTEAN_FOUNTAIN = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_fountain", () -> (StructureType<AtlanteanFountain>) () -> AtlanteanFountain.CODEC);
    public static final Supplier<StructureType<AtlantisHouse1>> ATLANTIS_HOUSE_1 = DEFERRED_REGISTRY_STRUCTURE.register("atlantis_house_1", () -> (StructureType<AtlantisHouse1>) () -> AtlantisHouse1.CODEC);
    public static final Supplier<StructureType<AtlantisHouse3>> ATLANTIS_HOUSE_3 = DEFERRED_REGISTRY_STRUCTURE.register("atlantis_house_3", () -> (StructureType<AtlantisHouse3>) () -> AtlantisHouse3.CODEC);
    public static final Supplier<StructureType<AtlantisTower>> ATLANTIS_TOWER = DEFERRED_REGISTRY_STRUCTURE.register("atlantis_tower", () -> (StructureType<AtlantisTower>) () -> AtlantisTower.CODEC);
    public static final Supplier<StructureType<OysterStructure>> OYSTER_STRUCTURE = DEFERRED_REGISTRY_STRUCTURE.register("oyster_structure", () -> (StructureType<OysterStructure>) () -> OysterStructure.CODEC);
    public static final Supplier<StructureType<AtlanteanTemple>> ATLANTEAN_TEMPLE = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_temple", () -> (StructureType<AtlanteanTemple>) () -> AtlanteanTemple.CODEC);
    public static final Supplier<StructureType<AtlanteanTemple>> ATLANTEAN_SPIRE = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_spire", () -> (StructureType<AtlanteanTemple>) () -> AtlanteanTemple.CODEC);
}
