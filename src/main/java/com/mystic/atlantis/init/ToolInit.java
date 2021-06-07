package com.mystic.atlantis.init;

import java.util.function.Supplier;

import com.mystic.atlantis.util.Lazy;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum ToolInit implements ToolMaterial {
    AQUAMARINE(286,5,4,2, 10, () -> Ingredient.ofItems(ItemInit.AQUAMARINE_GEM));

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
    public int getDurability() {
        return maxUses;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return toolEfficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return harvestLvl;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
