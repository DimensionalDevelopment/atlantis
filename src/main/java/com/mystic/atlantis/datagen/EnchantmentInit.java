package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(Registries.ENCHANTMENT, Reference.MODID);

    public static final ResourceKey<Enchantment> LIGHTNING_PROTECTION = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("lightning_protection"));
    public static final ResourceKey<Enchantment> GILL_BREATH = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("gill_breath"));
    public static final ResourceKey<Enchantment> ABYSSAL_ARMOR = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("abyssal_armor"));
    public static final ResourceKey<Enchantment> REEL_BIND = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("reel_bind"));
    public static final ResourceKey<Enchantment> CURRENT_GLIDE = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("current_glide"));
    public static final ResourceKey<Enchantment> TIDE_CALL = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("tide_call"));
    public static final ResourceKey<Enchantment> ABYSS_WALKER = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("abyss_walker"));
    public static final ResourceKey<Enchantment> DEPTH_PULSE = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("depth_pulse"));


    public static void init(IEventBus bus) {
        ENCHANTMENT.register(bus);
    }

    public static Holder.Reference<Enchantment> getEnchantmentHolder(Level level, ResourceKey<Enchantment> resourceKey) {
        return level.registryAccess().registry(Registries.ENCHANTMENT).get().getHolderOrThrow(resourceKey);
    }

    public EnchantmentInit(BootstrapContext<Enchantment> context) {
        register(context, LIGHTNING_PROTECTION, builder(ItemTags.CHEST_ARMOR_ENCHANTABLE, 1, 1, 2, 32, 3, EquipmentSlotGroup.CHEST));
        register(context, GILL_BREATH, builder(ItemTags.HEAD_ARMOR_ENCHANTABLE, 1, 3, 5, 25, 3, EquipmentSlotGroup.HEAD));
        register(context, ABYSSAL_ARMOR, builder(ItemTags.CHEST_ARMOR_ENCHANTABLE, 1, 3, 8, 30, 3, EquipmentSlotGroup.CHEST));
        register(context, REEL_BIND, builder(ItemTags.FISHING_ENCHANTABLE, 1, 2, 10, 30, 2, EquipmentSlotGroup.MAINHAND));
        register(context, CURRENT_GLIDE, builder(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, 2, 7, 25, 2, EquipmentSlotGroup.FEET));
        register(context, TIDE_CALL, builder(ItemTags.TRIDENT_ENCHANTABLE, 1, 3, 15, 40, 3, EquipmentSlotGroup.MAINHAND));
        register(context, ABYSS_WALKER, builder(ItemTags.LEG_ARMOR_ENCHANTABLE, 1, 3, 10, 35, 3, EquipmentSlotGroup.LEGS));
        register(context, DEPTH_PULSE, builder(ItemTags.CHEST_ARMOR_ENCHANTABLE, 1, 1, 18, 40, 1, EquipmentSlotGroup.CHEST));

    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static Enchantment.Builder builder(TagKey<Item> tag, int minLevel, int maxLevel, int minCost, int maxCost, int weight, EquipmentSlotGroup... slots) {
        HolderSet<Item> items = HolderSet.direct(BuiltInRegistries.ITEM.getOrCreateTag(tag).stream().toList());
        return new Enchantment.Builder(new Enchantment.EnchantmentDefinition(
                items,
                Optional.of(items),
                minLevel, maxLevel,
                Enchantment.constantCost(minCost),
                Enchantment.constantCost(maxCost),
                weight,
                List.of(slots)
        ));
    }

}