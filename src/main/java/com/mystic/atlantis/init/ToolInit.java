package com.mystic.atlantis.init;

import com.mystic.atlantis.util.Lazy;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum ToolInit implements Tier {
    AQUAMARINE(BlockTags.INCORRECT_FOR_IRON_TOOL, 286,5,4,2, 10, () -> Ingredient.of(ItemInit.AQUAMARINE_GEM.get())),
    ORICHALCUM(BlockTags.INCORRECT_FOR_IRON_TOOL, 500,7,5,4, 15, () -> Ingredient.of(ItemInit.ORICHALCUM_INGOT.get()));;

    private final TagKey<Block> incorrectTag;
    private final int maxUses;
    private final float toolEfficiency;
    private final float attackDamage;
    private final int harvestLvl;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

    ToolInit(TagKey<Block> incorrectTag, int uses, float efficiency, float damage, int harvest, int enchant, Supplier<Ingredient> material) {
        this.incorrectTag = incorrectTag;
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
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrectTag;
    }

    public int getHarvestLvl() {
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
    }
}
