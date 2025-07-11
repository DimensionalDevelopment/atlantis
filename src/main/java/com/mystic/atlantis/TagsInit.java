package com.mystic.atlantis;

import java.util.Set;
import java.util.function.Supplier;

import com.mystic.atlantis.datagen.BiomeInit;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class TagsInit {
    public static void init() {
        Item.init();
        Biome.init();
    }

    public static class Item {
        public static TagKey<net.minecraft.world.item.Item> CAN_ITEM_SINK = ItemTags.create(Atlantis.id("can_item_sink"));

        public static Set<Supplier<net.minecraft.world.item.Item>> getItemsThatCanSink() {
            return Set.of(
                    ItemInit.ORICHALCUM_BLEND,
                    ItemInit.ORICHALCUM_INGOT,
                    ItemInit.ORICHALCUM_AXE,
                    ItemInit.ORICHALCUM_PICKAXE,
                    ItemInit.ORICHALCUM_SHOVEL,
                    ItemInit.ORICHALCUM_HOE,
                    ItemInit.ORICHALCUM_SWORD,
                    ItemInit.ORICHALCUM_HELMET,
                    ItemInit.ORICHALCUM_CHESTPLATE,
                    ItemInit.ORICHALCUM_LEGGINGS,
                    ItemInit.ORICHALCUM_BOOTS,
                    ItemInit.ORICHALCUM_HAMMER,
                    BlockInit.ORICHALCUM_BLOCK.lazyMap(Block::asItem));
        }

        public static void init() {}
    }

    public static class Biome {
        public static TagKey<net.minecraft.world.level.biome.Biome> HAS_ATLANTEAN_VILLAGE = TagKey.create(Registries.BIOME, Atlantis.id("has_structure/has_atlantean_village"));
        public static TagKey<net.minecraft.world.level.biome.Biome> HAS_CONFIGURED_ATLANTEAN_FOUNTAIN = TagKey.create(Registries.BIOME, Atlantis.id("has_structure/has_configured_atlantean_fountain"));
        public static TagKey<net.minecraft.world.level.biome.Biome> HAS_CONFIGURED_ATLANTEAN_HOUSE_1 = TagKey.create(Registries.BIOME, Atlantis.id("has_structure/has_configured_atlantean_house_1"));
        public static TagKey<net.minecraft.world.level.biome.Biome> HAS_CONFIGURED_ATLANTEAN_HOUSE_3 = TagKey.create(Registries.BIOME, Atlantis.id("has_structure/has_configured_atlantean_house_3"));
        public static TagKey<net.minecraft.world.level.biome.Biome> HAS_CONFIGURED_ATLANTEAN_SPIRE = TagKey.create(Registries.BIOME, Atlantis.id("has_structure/has_configured_atlantean_spire"));
        public static TagKey<net.minecraft.world.level.biome.Biome> HAS_CONFIGURED_ATLANTEAN_TEMPLE = TagKey.create(Registries.BIOME, Atlantis.id("has_structure/has_configured_atlantean_temple"));
        public static TagKey<net.minecraft.world.level.biome.Biome> HAS_CONFIGURED_ATLANTEAN_TOWER = TagKey.create(Registries.BIOME, Atlantis.id("has_structure/has_configured_atlantean_tower"));
        public static TagKey<net.minecraft.world.level.biome.Biome> HAS_CONFIGURED_OYSTER_STRUCTURE = TagKey.create(Registries.BIOME, Atlantis.id("has_structure/has_configured_oyster_structure"));

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> isDeepOcean() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.VOLCANIC_DARKSEA_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.GOO_LAGOONS_KEY,
                    BiomeInit.AQUAIEL_JELLYFISH_FIELDS_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> isOcean() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.VOLCANIC_DARKSEA_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.GOO_LAGOONS_KEY,
                    BiomeInit.AQUAIEL_JELLYFISH_FIELDS_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> hasAtlanteanVillage() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.VOLCANIC_DARKSEA_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.GOO_LAGOONS_KEY,
                    BiomeInit.AQUAIEL_JELLYFISH_FIELDS_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> hasAtlanteanFountain() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> hasAtlanteanHouse1() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> hasAtlanteanHouse3() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> hasAtlanteanSpire() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> hasAtlanteanTemple() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> hasAtlanteanTower() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static Set<ResourceKey<net.minecraft.world.level.biome.Biome>> hasOysterStructure() {
            return Set.of(
                    BiomeInit.COCONUT_ISLES_KEY,
                    BiomeInit.ATLANTEAN_ISLANDS_BIOME_KEY,
                    BiomeInit.ATLANTIS_BIOME_KEY,
                    BiomeInit.ATLANTEAN_GARDEN_KEY);
        }

        public static void init() {}
    }
}
