package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.blocks.base.LinguisticGlyph;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.items.armor.AtlantisArmorSet;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class AtlantisItemModelProvider extends ItemModelProvider {
    public AtlantisItemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, "atlantis", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_A);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_B);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_C);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_D);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_E);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_F);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_G);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_H);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_I);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_J);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_K);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_L);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_M);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_N, "linguistic_glyph_n_scroll");
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_O);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_P);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_Q);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_R);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_S, "linguistic_glyph_s_scroll");
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_T);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_U);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_V);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_W);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_X);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_Y);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_Z);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_0);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_1);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_2);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_3);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_4);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_5);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_6);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_7);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_8);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_9);

        block(BlockInit.ORICHALCUM_BLOCK);
        block(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK);

        for (TrailsGroup trailsGroup : BlockInit.ANCIENT_CUPRUM.values()) {
            blockGenerated(trailsGroup.waxed_door().getId().getPath(), trailsGroup.door().getId().getPath());
            blockGenerated(trailsGroup.door().getId().getPath());
        }

        blockGenerated("ancient_acacia_door");
        blockGenerated("ancient_birch_door");
        blockGenerated("ancient_dark_oak_door");
        blockGenerated("ancient_jungle_door");
        blockGenerated("ancient_oak_door");
        blockGenerated("ancient_spruce_door");
        blockGenerated("ancient_mangrove_door");
        blockGenerated("ancient_cherry_door");
        blockGenerated("ancient_bamboo_door");
        blockGenerated("ancient_crimson_door");
        blockGenerated("ancient_warped_door");

        blockGenerated("seabloom", BlockInit.SEABLOOM);
        blockGenerated("red_seabloom", BlockInit.RED_SEABLOOM);
        blockGenerated("yellow_seabloom", BlockInit.YELLOW_SEABLOOM);
        blockGenerated("purple_seashroom", BlockInit.PURPLE_SEASHROOM);
        blockGenerated("yellow_seashroom", BlockInit.YELLOW_SEASHROOM);
        blockGenerated("palm_sapling", BlockInit.PALM_SAPLING);
        blockGenerated("nymph_sapling", BlockInit.NYMPH_SAPLING);

        item(ItemInit.ORICHALCUM_UPGRADE_SMITHING_TEMPLATE);
        item(ItemInit.RUBYCLAW_CRAB_EGG);
        item(ItemInit.RUBYCLAW_CRAB_BUCKET);
        item(ItemInit.AQUAIEL_JELLYFISH_EGG);
        item(ItemInit.AQUAIEL_JELLYFISH_BUCKET);
        item(ItemInit.AQUAIEL_STRING);
        item(ItemInit.LEVIATHAN_EGG);
        item(ItemInit.GLITTERTAIL_SHRIMP_BUCKET);
        item(ItemInit.GLITTERTAIL_SHRIMP_EGG);
        item(ItemInit.STARFISH_EGG);
        item(ItemInit.THALASSIAN_SEAHORSE_BUCKET);
        item(ItemInit.THALASSIAN_SEAHORSE_EGG);
        item(ItemInit.COCONUT_CRAB_EGG);
        item(ItemInit.ZOMBIE_STARFISH_EGG);
        item(ItemInit.PANBEE);
        item(ItemInit.COLUMN_CAVITATION);
        item(ItemInit.ORICHALCUM_INGOT);
        item(ItemInit.ORICHALCUM_BLEND);

        armorSet(ItemInit.ORICHALCUM);
        armorSet(ItemInit.AQUAMARINE);
        armorSet(ItemInit.BROWN_WROUGHT);

        itemTool(ItemInit.ORICHALCUM_AXE);
        itemTool(ItemInit.ORICHALCUM_PICKAXE);
        itemTool(ItemInit.ORICHALCUM_SHOVEL);
        itemTool(ItemInit.ORICHALCUM_SWORD);
        itemTool(ItemInit.ORICHALCUM_HOE);
        itemTool(ItemInit.ORICHALCUM_HAMMER);
        itemTool(ItemInit.AQUAMARINE_HAMMER);
        itemTool(ItemInit.AQUAMARINE_AXE);
        itemTool(ItemInit.AQUAMARINE_PICKAXE);
        itemTool(ItemInit.AQUAMARINE_SHOVEL);
        itemTool(ItemInit.AQUAMARINE_SWORD);
        itemTool(ItemInit.AQUAMARINE_HOE);
        item(ItemInit.BROKEN_SHELLS);
        item(ItemInit.SODIUM_NUGGET);
        item(ItemInit.ANCIENT_CUPRUM_INGOT);
        item(ItemInit.RAW_ANCIENT_CUPRUM);
        item(ItemInit.SEASALT);
        item(ItemInit.FIRE_MELON_JELLY_BOTTLE);
        item(ItemInit.JELLY_BOTTLE);
        item(ItemInit.AQUAMARINE_GEM);
        item(ItemInit.ORB_OF_ATLANTIS);
        item(ItemInit.ATLANTEAN_CRYSTAL);
        item(ItemInit.OCEAN_STONE);
        item(ItemInit.DROP_OF_ATLANTIS);
        item(ItemInit.BROWN_WROUGHT_PATCHES);
        item(ItemInit.CRAB_LEGS);
        item(ItemInit.SHRIMP);
        item(ItemInit.COOKED_SHRIMP);
        item(ItemInit.AQUATIC_POWER_DUST);
        item(ItemInit.AQUAIEL_STRING);
        item(ItemInit.SUBMARINE);
        item(ItemInit.WATER_PILL);
        item(ItemInit.NYMPH_SIGN);
        item(ItemInit.PALM_SIGN);
        item(ItemInit.FIRE_MELON_FRUIT);
        item(ItemInit.FIRE_MELON_FRUIT_SPIKED);
        item(ItemInit.FIRE_MELON_SEEDS);
        item(ItemInit.FIRE_MELON_SPIKE);
        item(ItemInit.COCONUT_SLICE);
        item(ItemInit.JETSTREAM_WATER_BUCKET);
        item(ItemInit.SALTY_SEAWATER_BUCKET);
        item(ItemInit.NYMPH_BOAT);
        item(ItemInit.PALM_BOAT);
        item(ItemInit.ATLANTEAN_AMULET);
        item(ItemInit.ATLANTEAN_SPEAR);
        item(ItemInit.ANCIENT_CUPRUM_INGOT);
        item(ItemInit.RAW_ANCIENT_CUPRUM);
    }

    private void armorSet(AtlantisArmorSet set) {
        item(set.helmet());
        item(set.chestplate());
        item(set.leggings());
        item(set.boots());
    }

    private <T extends Item> void itemTool(DeferredItem<T> tool) {
        getBuilder(tool.getId().getPath())
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", items(tool.getId()));
    }

    private void withParent(DeferredBlock<Block> block, LinguisticGlyph glyph) {
        withExistingParent(block.getId().getPath(), block(Atlantis.id("linguistic_" + glyph.name().toLowerCase())));
    }

    private <T extends Block> void block(DeferredBlock<T> block) {
        withExistingParent(block.getId().getPath(), block(block.getId()));
    }

    private <T extends Item> void item(DeferredItem<T> item) {
        try {
            getBuilder(item.getId().getPath())
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", items(item.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private <T extends Item> void item(DeferredItem<T> item, String id) {
        try {
            getBuilder(item.getId().getPath())
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", items(modLoc(id)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private <T extends Block> void blockGenerated(String id, DeferredBlock<T> block) {
        try {
            getBuilder(block.getId().getPath())
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", block(modLoc(id)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private <T extends Block> void blockGenerated(String id) {
        try {
            getBuilder(id)
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", items(modLoc(id)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private <T extends Block> void blockGenerated(String id, String path) {
        try {
            getBuilder(id)
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", items(modLoc(path)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private ResourceLocation block(ResourceLocation location) {
        return ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "block/" + location.getPath());
    }


    private ResourceLocation items(ResourceLocation location) {
        return ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath());
    }
}
