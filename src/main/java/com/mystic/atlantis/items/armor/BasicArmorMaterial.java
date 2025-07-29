package com.mystic.atlantis.items.armor;

import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BasicArmorMaterial {
    public static final DeferredRegister<ArmorMaterial> REGISTER = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, Reference.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARMOR_AQUAMARINE = register( "aquamarine", 24, new int[] {2, 6, 7, 3,6} , 9, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, () -> Ingredient.of(ItemInit.AQUAMARINE_GEM.get()));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARMOR_ORICHALCUM = register( "orichalcum", 24, new int[] {2, 6, 7, 3, 6} , 9, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, () -> Ingredient.of(ItemInit.ORICHALCUM_INGOT.get()));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARMOR_BROWN_WROUGHT = register("wrought", 24, new int[] {3, 5, 5, 4, 5} , 7, SoundEvents.ARMOR_EQUIP_IRON, 2.0F, 0.0F, () -> Ingredient.of(ItemInit.BROWN_WROUGHT_PATCHES.get()));



    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, Holder<SoundEvent> soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> supplier) {
        return REGISTER.register(name, id -> new ArmorMaterial(
                IntStream.of(damageReductionAmountArray).mapToObj(value -> Map.entry(ArmorItem.Type.values()[value], value)).collect(BasicArmorMaterial.asMap()),
                enchantability,
                soundEvent,
                supplier,
                List.of(new ArmorMaterial.Layer(id)),
                toughness,
                knockbackResistance
        ));
    }

    public static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> asMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

}
