package com.mystic.atlantis.datagen;

import com.mojang.datafixers.util.Pair;
import com.mystic.atlantis.Atlantis;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

import static net.minecraft.data.worldgen.Pools.EMPTY;

public class TemplatePoolInit {

    public static final ResourceKey<StructureTemplatePool> ATLANTEAN_VILLAGE_DECOS = key("atlantean_village/decos");
    public static final ResourceKey<StructureTemplatePool> ATLANTEAN_VILLAGE_HOUSE = key("atlantean_village/house");
    public static final ResourceKey<StructureTemplatePool> ATLANTEAN_VILLAGE_ROADS = key("atlantean_village/roads");
    public static final ResourceKey<StructureTemplatePool> ATLANTEAN_VILLAGE_START = key("atlantean_village/start");
    public static final ResourceKey<StructureTemplatePool> ATLANTEAN_VILLAGE_TERMINATOR = key("atlantean_village/terminator");
    public static final ResourceKey<StructureTemplatePool> ATLANTEAN_FOUNTAIN = key("atlantean_fountain");
    public static final ResourceKey<StructureTemplatePool> ATLANTEAN_SPIRE = key("atlantean_spire");
    public static final ResourceKey<StructureTemplatePool> ATLANTEAN_TEMPLE = key("atlantean_temple");
    public static final ResourceKey<StructureTemplatePool> ATLANTIS_HOUSE_1 = key("atlantis_house_1");
    public static final ResourceKey<StructureTemplatePool> ATLANTIS_HOUSE_3 = key("atlantis_house_3");
    public static final ResourceKey<StructureTemplatePool> ATLANTIS_TOWER = key("atlantis_tower");
    public static final ResourceKey<StructureTemplatePool> OYSTER_STRUCTURE = key("oyster_structure");
    private final HolderGetter<StructureProcessorList> listRegistry;
    private HolderGetter<StructureTemplatePool> templateRegistry;
    private BootstrapContext<StructureTemplatePool> context;

    public static ResourceKey<StructureTemplatePool> key(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Atlantis.id(name));
    }
    public TemplatePoolInit(BootstrapContext<StructureTemplatePool> context) {
        this.context = context;
        templateRegistry = context.lookup(Registries.TEMPLATE_POOL);
        listRegistry = context.lookup(Registries.PROCESSOR_LIST);

        register(ATLANTEAN_VILLAGE_DECOS, EMPTY,
                single("atlantis:atlantean_village/decos/lamp_1"),
                single("atlantis:atlantean_village/decos/lamp_2"),
                single("atlantis:atlantean_village/decos/lamp_3"),
                single("atlantis:atlantean_village/decos/lamp_4"),
                single("atlantis:atlantean_village/deco/beekeeper"),
                single("atlantis:atlantean_village/deco/copper_golem"),
                empty(5)
        );

        register(ATLANTEAN_VILLAGE_HOUSE, ATLANTEAN_VILLAGE_DECOS,
                single("atlantis:atlantean_village/house/house_1", 20),
                single("atlantis:atlantean_village/house/house_2", 20),
                single("atlantis:atlantean_village/house/house_3", 20),
                single("atlantis:atlantean_village/house/house_4", 20),
                single("atlantis:atlantean_village/jobsite/butcher", 5),
                single("atlantis:atlantean_village/jobsite/cartographer", 5),
                single("atlantis:atlantean_village/jobsite/library", 5),
                single("atlantis:atlantean_village/jobsite/mason", 5),
                single("atlantis:atlantean_village/jobsite/pen", 5),
                single("atlantis:atlantean_village/jobsite/shepherd", 5),
                single("atlantis:atlantean_village/jobsite/smith", 5),
                single("atlantis:atlantean_village/jobsite/pen", 2),
                single("atlantis:atlantean_village/jobsite/farm", ProcessorListInit.ATLANTEAN_VILLAGE_FARM, 2),
                single("atlantis:atlantean_village/jobsite/engineer", 5),
                single("atlantis:atlantean_village/jobsite/garden", 5),
                single("atlantis:atlantean_village/jobsite/hunter", 5),
                single("atlantis:atlantean_village/jobsite/oceanographer", 5),
                single("atlantis:atlantean_village/jobsite/woodworker", 5),
                single("atlantis:atlantean_village/jobsite/chicken_coop", 2),
                single("atlantis:atlantean_village/jobsite/imengineer", 5),
                single("atlantis:atlantean_village/jobsite/hunter_trainer", 5),
                single("atlantis:atlantean_village/jobsite/restaurant", 5),
                single("atlantis:atlantean_village/jobsite/botanist", 5));

        register(ATLANTEAN_VILLAGE_TERMINATOR, EMPTY,
                single("atlantis:atlantean_village/roads/terminator", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS, 1, StructureTemplatePool.Projection.TERRAIN_MATCHING));

        register(ATLANTEAN_VILLAGE_ROADS, ATLANTEAN_VILLAGE_TERMINATOR,
                single("atlantis:atlantean_village/roads/straight", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS, 4),
                single("atlantis:atlantean_village/roads/t_1", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS),
                single("atlantis:atlantean_village/roads/t_2", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS),
                single("atlantis:atlantean_village/roads/t_3", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS),
                single("atlantis:atlantean_village/roads/bent_1", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS),
                single("atlantis:atlantean_village/roads/bent_2", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS),
                single("atlantis:atlantean_village/roads/inter", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS));

        register(ATLANTEAN_VILLAGE_START, EMPTY,
                single("atlantis:atlantean_village/town_center", ProcessorListInit.ATLANTEAN_VILLAGE_ROADS));

        register(ATLANTEAN_FOUNTAIN, EMPTY,
                single("atlantis:atlantean_fountain"));

        register(ATLANTEAN_TEMPLE, EMPTY,
                single("atlantis:atlantean_temple"));

        register(ATLANTEAN_SPIRE, EMPTY,
                single("atlantis:atlantean_spire"));

        register(ATLANTIS_HOUSE_1, EMPTY,
                single("atlantis:atlantis_house_1"));

        register(ATLANTIS_HOUSE_3, EMPTY,
                single("atlantis:atlantis_house_3"));

        register(ATLANTIS_TOWER, EMPTY,
                single("atlantis:atlantis_tower"));

        register(OYSTER_STRUCTURE, EMPTY,
                single("atlantis:oyster_structure"));
    }

    private Pair<StructurePoolElement, Integer> empty(int weight) {
        return new Pair<>(EmptyPoolElement.empty().apply(StructureTemplatePool.Projection.RIGID), weight);
    }

    @SafeVarargs
    private void register(ResourceKey<StructureTemplatePool> key, ResourceKey<StructureTemplatePool> fallback, Pair<StructurePoolElement, Integer>... pairs) {
        context.register(key, new StructureTemplatePool(pool(fallback), List.of(pairs)));
    }

    private Pair<StructurePoolElement, Integer> single(String name) {
        return single(name, 1);
    }

    private Pair<StructurePoolElement, Integer> single(String name, ResourceKey<StructureProcessorList> processList, int weight, StructureTemplatePool.Projection projection) {
        return new Pair<>(SinglePoolElement.single(name, listRegistry.getOrThrow(processList)).apply(projection), weight);
    }

    private Pair<StructurePoolElement, Integer> single(String name, ResourceKey<StructureProcessorList> processList, int weight) {
        return single(name, processList, weight, StructureTemplatePool.Projection.RIGID);
    }

    private Pair<StructurePoolElement, Integer> single(String name, int weight) {
        return new Pair<>(SinglePoolElement.single(name).apply(StructureTemplatePool.Projection.RIGID), weight);
    }

    private Pair<StructurePoolElement, Integer> single(String name, ResourceKey<StructureProcessorList> processList) {
        return new Pair<>(SinglePoolElement.single(name, listRegistry.getOrThrow(processList)).apply(StructureTemplatePool.Projection.RIGID), 1);
    }

    private Holder<StructureTemplatePool> pool(ResourceKey<StructureTemplatePool> key) {
        return templateRegistry.getOrThrow(key);
    }
}