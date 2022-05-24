package com.mystic.atlantis.init;

import com.google.common.collect.Lists;
import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.util.Lazy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.function.Supplier;

public enum ToolInit implements Tier {
    AQUAMARINE(286,5,4,2, 10, () -> Ingredient.of(ItemInit.AQUAMARINE_GEM.get())),
    ORICHAClUM(286,5,4,2, 10, () -> Ingredient.of(ItemInit.ORICHALCUM_IGNOT.get()));;

    private final int maxUses;
    private final float toolEfficiency;
    private final float attackDamage;
    private final int harvestLvl;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

    ToolInit(int uses, float efficiency, float damage, int harvest, int enchant, Supplier<Ingredient> material) {
        maxUses = uses;
        toolEfficiency = efficiency;
        attackDamage = damage;
        harvestLvl = harvest;
        enchantability = enchant;
        repairMaterial = new Lazy<>(material);
    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return toolEfficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLvl;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    public static void init() {
        TierSortingRegistry.registerTier(AQUAMARINE, Atlantis.id("aquamarine"), Lists.newArrayList(new ResourceLocation("stone")), Lists.newArrayList(new ResourceLocation("iron")));
    }
}
