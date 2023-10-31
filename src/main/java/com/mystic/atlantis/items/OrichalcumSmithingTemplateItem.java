package com.mystic.atlantis.items;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class OrichalcumSmithingTemplateItem extends SmithingTemplateItem {
    private static final Component NETHERITE_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation("orichalcum_upgrade"))).withStyle(ChatFormatting.GRAY);
    private static final Component NETHERITE_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.orichalcum_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE);
    private static final Component NETHERITE_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.orichalcum_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE);
    private static final Component NETHERITE_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.orichalcum_upgrade.base_slot_description")));
    private static final Component NETHERITE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.orichalcum_upgrade.additions_slot_description")));

    public OrichalcumSmithingTemplateItem() {
        super(NETHERITE_UPGRADE_APPLIES_TO, NETHERITE_UPGRADE_INGREDIENTS, NETHERITE_UPGRADE, NETHERITE_UPGRADE_BASE_SLOT_DESCRIPTION, NETHERITE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION, createNetheriteUpgradeIconList(), createNetheriteUpgradeMaterialList());
    }

    private static List<ResourceLocation> createNetheriteUpgradeIconList() {
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_PICKAXE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_AXE, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_SHOVEL);
    }

    private static List<ResourceLocation> createNetheriteUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_INGOT);
    }
}
