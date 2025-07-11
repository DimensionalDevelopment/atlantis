package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.AtlantisEntityInit;
import com.mystic.atlantis.structures.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Map;
import java.util.Optional;

public class StructureInit {
    public static final ResourceKey<Structure> ATLANTEAN_VILLAGE = key("atlantean_village");
    public static final ResourceKey<Structure> CONFIGURED_ATLANTEAN_FOUNTAIN = key("configured_atlantean_fountain");
    public static final ResourceKey<Structure> CONFIGURED_ATLANTEAN_HOUSE_1 = key("configured_atlantean_house_1");
    public static final ResourceKey<Structure> CONFIGURED_ATLANTEAN_HOUSE_3 = key("configured_atlantean_house_3");
    public static final ResourceKey<Structure> CONFIGURED_ATLANTEAN_SPIRE = key("configured_atlantean_spire");
    public static final ResourceKey<Structure> CONFIGURED_ATLANTEAN_TEMPLE = key("configured_atlantean_temple");
    public static final ResourceKey<Structure> CONFIGURED_ATLANTEAN_TOWER = key("configured_atlantean_tower");
    public static final ResourceKey<Structure> CONFIGURED_OYSTER_STRUCTURE = key("configured_oyster_structure");

    private static ResourceKey<Structure> key(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Atlantis.id(name));
    }

    public StructureInit(BootstapContext<Structure> context) {
        HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> holderGetter1 = context.lookup(Registries.TEMPLATE_POOL);

        context.register(ATLANTEAN_VILLAGE, new JigsawStructure(
                new Structure.StructureSettings(
                        HolderSet.direct(holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY),
                                holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY), holderGetter.getOrThrow(BiomeInit.GOO_LAGOONS_KEY),
                                holderGetter.getOrThrow(BiomeInit.AQUAIEL_JELLYFISH_FIELDS_KEY), holderGetter.getOrThrow(BiomeInit.VOLCANIC_DARKSEA_KEY),
                                holderGetter.getOrThrow(BiomeInit.COCONUT_ISLES_KEY)),
                        Map.of(
                                MobCategory.WATER_AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH,
                                        25, 2, 2))),
                                MobCategory.WATER_CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                        WeightedRandomList.create(new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(),
                                                25, 2, 2)))),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN),
                holderGetter1.getOrThrow(TemplatePoolInit.ATLANTEAN_VILLAGE_START),
                Optional.empty(),
                6, ConstantHeight.of(VerticalAnchor.absolute(0)),
                true, Optional.of(Heightmap.Types.OCEAN_FLOOR_WG),
                112));

        context.register(CONFIGURED_ATLANTEAN_FOUNTAIN, new AtlanteanFountain(
                new Structure.StructureSettings(
                        HolderSet.direct(holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY),
                                holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY), holderGetter.getOrThrow(BiomeInit.GOO_LAGOONS_KEY),
                                holderGetter.getOrThrow(BiomeInit.AQUAIEL_JELLYFISH_FIELDS_KEY), holderGetter.getOrThrow(BiomeInit.VOLCANIC_DARKSEA_KEY),
                                holderGetter.getOrThrow(BiomeInit.COCONUT_ISLES_KEY)),
                        Map.of(MobCategory.WATER_AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH,
                                        25, 2, 2))), MobCategory.WATER_CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(),
                                        25, 2, 2)))),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE),
                holderGetter1.getOrThrow(TemplatePoolInit.ATLANTEAN_FOUNTAIN),
                Optional.empty(),
                1, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.OCEAN_FLOOR_WG),
                1));

        context.register(CONFIGURED_ATLANTEAN_HOUSE_1, new AtlantisHouse1(
                new Structure.StructureSettings(
                        HolderSet.direct(holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY),
                                holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY)),
                        Map.of(MobCategory.WATER_AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH,
                                        25, 2, 2)))),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE),
                holderGetter1.getOrThrow(TemplatePoolInit.ATLANTIS_HOUSE_1),
                Optional.empty(),
                1, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.OCEAN_FLOOR_WG),
                1));

        context.register(CONFIGURED_ATLANTEAN_HOUSE_3, new AtlantisHouse3(
                new Structure.StructureSettings(
                        HolderSet.direct(holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY),
                                holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY), holderGetter.getOrThrow(BiomeInit.COCONUT_ISLES_KEY)),
                        Map.of(MobCategory.WATER_AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH,
                                        25, 2, 2)))),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE),
                holderGetter1.getOrThrow(TemplatePoolInit.ATLANTIS_HOUSE_3),
                Optional.empty(),
                1, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.OCEAN_FLOOR_WG),
                1));

        context.register(CONFIGURED_ATLANTEAN_SPIRE, new AtlanteanFountain(
                new Structure.StructureSettings(
                        HolderSet.direct(holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY),
                                holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY), holderGetter.getOrThrow(BiomeInit.COCONUT_ISLES_KEY)),
                        Map.of(
                                MobCategory.WATER_AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH,
                                        25, 2, 2))),
                                MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(
                                        new MobSpawnSettings.SpawnerData(AtlantisEntityInit.ZOMBIE_STARFISH.get(),
                                        25, 2, 2),
                                        new MobSpawnSettings.SpawnerData(EntityType.DROWNED,
                                        25, 2, 2)))),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE),
                holderGetter1.getOrThrow(TemplatePoolInit.ATLANTEAN_SPIRE),
                Optional.empty(),
                1, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.OCEAN_FLOOR_WG),
                1));

        context.register(CONFIGURED_ATLANTEAN_TEMPLE, new AtlanteanTemple(
                new Structure.StructureSettings(
                        HolderSet.direct(holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY),
                                holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY), holderGetter.getOrThrow(BiomeInit.COCONUT_ISLES_KEY)),
                        Map.of(
                                MobCategory.WATER_AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH,
                                        25, 2, 2))),
                                MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.GUARDIAN,
                                        25, 2, 2)))),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE),
                holderGetter1.getOrThrow(TemplatePoolInit.ATLANTEAN_TEMPLE),
                Optional.empty(),
                1, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.OCEAN_FLOOR_WG),
                1));

        context.register(CONFIGURED_ATLANTEAN_TOWER, new AtlantisTower(
                new Structure.StructureSettings(
                        HolderSet.direct(holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY),
                                holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY), holderGetter.getOrThrow(BiomeInit.COCONUT_ISLES_KEY)),
                        Map.of(
                                MobCategory.WATER_AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH,
                                        25, 2, 2)))),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE),
                holderGetter1.getOrThrow(TemplatePoolInit.ATLANTIS_TOWER),
                Optional.empty(),
                1, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.OCEAN_FLOOR_WG),
                1));

        context.register(CONFIGURED_OYSTER_STRUCTURE, new OysterStructure(
                new Structure.StructureSettings(
                        HolderSet.direct(holderGetter.getOrThrow(BiomeInit.ATLANTIS_BIOME_KEY), holderGetter.getOrThrow(BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY),
                                holderGetter.getOrThrow(BiomeInit.ATLANTEAN_GARDEN_KEY), holderGetter.getOrThrow(BiomeInit.GOO_LAGOONS_KEY),
                                holderGetter.getOrThrow(BiomeInit.AQUAIEL_JELLYFISH_FIELDS_KEY), holderGetter.getOrThrow(BiomeInit.VOLCANIC_DARKSEA_KEY),
                                holderGetter.getOrThrow(BiomeInit.COCONUT_ISLES_KEY)),
                        Map.of(
                                MobCategory.WATER_AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH,
                                        25, 2, 2))),
                                MobCategory.WATER_CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(),
                                        25, 2, 2)))),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE),
                holderGetter1.getOrThrow(TemplatePoolInit.OYSTER_STRUCTURE),
                Optional.empty(),
                1, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.OCEAN_FLOOR_WG),
                1));

    }
}
