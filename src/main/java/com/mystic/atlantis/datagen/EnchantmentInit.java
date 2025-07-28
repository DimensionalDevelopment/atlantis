package com.mystic.atlantis.datagen;

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
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(Registries.ENCHANTMENT, Reference.MODID);

    public static final ResourceKey<Enchantment> LIGHTNING_PROTECTION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Reference.MODID, "lightning_protection"));

    public static void init(IEventBus bus) {
        ENCHANTMENT.register(bus);
    }

    public static Holder.Reference<Enchantment> getEnchantmentHolder(Level level, ResourceKey<Enchantment> resourceKey) {
        return level.registryAccess().registry(Registries.ENCHANTMENT).get().getHolderOrThrow(resourceKey);
    }

    public EnchantmentInit(BootstrapContext<Enchantment> context) {
        register(context, LIGHTNING_PROTECTION, new Enchantment.Builder(new Enchantment.EnchantmentDefinition(
                HolderSet.direct(BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.CHEST_ARMOR_ENCHANTABLE).stream().toList()),
                Optional.of(HolderSet.direct(BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.CHEST_ARMOR_ENCHANTABLE).stream().toList())),
                1, 1, Enchantment.constantCost(2),Enchantment.constantCost(32),
                3, List.of(EquipmentSlotGroup.CHEST))));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
}