package com.mystic.atlantis.items.armor;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class BasicArmorMaterial {

    public static final DeferredRegister<ArmorMaterial> REGISTER = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, Reference.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARMOR_AQUAMARINE = register(
            "aquamarine", 24, makeProtectionMap(2, 6, 7, 3), 9,
            SoundEvents.ARMOR_EQUIP_IRON.value(), 1.0F, 0.0F, () -> Ingredient.of(ItemInit.AQUAMARINE_GEM.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARMOR_ORICHALCUM = register(
            "orichalcum", 24, makeProtectionMap(3, 6, 6, 2), 9,
            SoundEvents.ARMOR_EQUIP_IRON.value(), 1.0F, 0.0F, () -> Ingredient.of(ItemInit.ORICHALCUM_INGOT.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARMOR_BROWN_WROUGHT = register(
            "wrought", 24, makeProtectionMap(3, 5, 5, 2), 7,
            SoundEvents.ARMOR_EQUIP_LEATHER.value(), 2.0F, 0.0F, () -> Ingredient.of(ItemInit.BROWN_WROUGHT_PATCHES.get()));

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(
            String name,
            int maxDamageFactor,
            EnumMap<ArmorItem.Type, Integer> protection,
            int enchantability,
            SoundEvent equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(Atlantis.id(name)));

        return REGISTER.register(name, () -> new ArmorMaterial(
                protection,
                enchantability,
                Holder.direct(equipSound),
                repairIngredient,
                layers,
                toughness,
                knockbackResistance
        ));
    }

    private static EnumMap<ArmorItem.Type, Integer> makeProtectionMap(int head, int chest, int legs, int feet) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.HELMET, head);
        map.put(ArmorItem.Type.CHESTPLATE, chest);
        map.put(ArmorItem.Type.LEGGINGS, legs);
        map.put(ArmorItem.Type.BOOTS, feet);
        map.put(ArmorItem.Type.BODY, chest);
        return map;
    }
}
