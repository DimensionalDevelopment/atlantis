package com.mystic.atlantis.items.armor;

import java.util.function.Supplier;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import com.mystic.atlantis.init.ItemInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BasicArmorMaterial
{
    public static final net.minecraft.item.ArmorMaterial ARMOR_AQUAMARINE = new ArmorMaterial( "aquamarine", 24, new int[] {2, 6, 7, 3} , 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, 0.0F, () -> Ingredient.ofItems(ItemInit.AQUAMARINE_INGOT));
    public static final net.minecraft.item.ArmorMaterial ARMOR_BROWN_WROUGHT = new ArmorMaterial("wrought", 24, new int[] {3, 5, 5, 4} , 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F, 0.0F, () -> Ingredient.ofItems(ItemInit.BROWN_WROUGHT_PATCHES));

    private static class ArmorMaterial implements net.minecraft.item.ArmorMaterial{

        private static final int[] Max_Damage_Array = new int[] {13,15,16,11};
        private final String name;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final float knochbackResistance;
        private final Lazy<Ingredient> repairMaterial;

        public ArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, double toughness, float knochbackResistance, Supplier<Ingredient> supplier) {
            this.name = name;
            this.maxDamageFactor = maxDamageFactor;
            this.damageReductionAmountArray = damageReductionAmountArray;
            this.enchantability = enchantability;
            this.soundEvent = soundEvent;
            this.toughness = (float)toughness;
            this.knochbackResistance = knochbackResistance;
            this.repairMaterial = new Lazy<Ingredient>(supplier);
        }

        @Override
        public int getDurability(EquipmentSlot slotIn) {
            return Max_Damage_Array[slotIn.getEntitySlotId()] * maxDamageFactor;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slotIn) {
            return damageReductionAmountArray[slotIn.getEntitySlotId()];
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
            return soundEvent;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairMaterial.get();
        }

        @Environment(EnvType.CLIENT)
        @Override
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return this.knochbackResistance;
        }
    }
}
