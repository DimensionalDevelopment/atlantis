package com.mystic.atlantis.items.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record AtlantisArmorSet(Holder<ArmorMaterial> material, Holder<MobEffect> effect, DeferredItem<Item> helmet, DeferredItem<Item> chestplate, DeferredItem<Item> leggings, DeferredItem<Item> boots) {
    public static final Map<Holder<ArmorMaterial>, AtlantisArmorSet> SETS = new HashMap<>();

    public static AtlantisArmorSet create(DeferredRegister.Items register, String name, Holder<ArmorMaterial> material, Holder<MobEffect> effect) {
        var properties = new Item.Properties();

        return new AtlantisArmorSet(material, effect,
            register.register(name + "_helmet", () -> new Armor(material, ArmorItem.Type.HELMET, properties)),
            register.register(name + "_chestplate", () -> new Armor(material, ArmorItem.Type.CHESTPLATE, properties)),
            register.register(name + "_leggings", () -> new Armor(material, ArmorItem.Type.LEGGINGS, properties)),
            register.register(name + "_boots", () -> new Armor(material, ArmorItem.Type.BOOTS, properties)));
    }

    protected void evaluateArmorEffects(Player player) {
        if (hasCorrectArmorOn(player)) {
            addStatusEffectForMaterial(player);
        }
    }

    private void addStatusEffectForMaterial(Player player) {
        boolean hasPlayerEffect = player.hasEffect(effect);

        if (hasCorrectArmorOn(player) && !hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(effect, 2001, 1, false, false));
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !breastplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(Player player) {
        for (ItemStack armor : player.getInventory().armor) {
            if (armor.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial().equals(material)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return helmet.is(helmet()) && breastplate.is(chestplate()) && leggings.is(leggings()) && boots.is(boots());
    }

    private static class Armor extends ArmorItem {


        public Armor(Holder<ArmorMaterial> material, Type type, Properties properties) {
            super(material, type, properties);
        }

        @Override
        public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entity, int slot, boolean selected) {
            if (!world.isClientSide()) {
                if (entity instanceof Player player) {
                    SETS.get(material).evaluateArmorEffects(player);
                }
            }

            super.inventoryTick(stack, world, entity, slot, selected);
        }
    }
}
