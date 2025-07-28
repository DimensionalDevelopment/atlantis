package com.mystic.atlantis.init;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.enchantments.LightningProtection;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentInit {

    public static final ResourceKey<Enchantment> LIGHTNING_PROTECTION = ResourceKey.create(Registries.ENCHANTMENT, Atlantis.id("lightning_protection")); //, () -> new LightningProtection(Enchantment.Rarity.COMMON, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.values()));

    public static void init(BootstrapContext<Enchantment> context) {
        context.register(LIGHTNING_PROTECTION, new Enchantment(Component.literal(""), new Enchantment.EnchantmentDefinition()))
    }
}
