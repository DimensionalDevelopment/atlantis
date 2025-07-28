
package com.mystic.atlantis.items.tools;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AtlanteanAmulet extends Item {
    public AtlanteanAmulet(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity.tickCount % 40 == 0) {
            if (pEntity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 1, false, false));
            }
        } else {
            if (pEntity instanceof LivingEntity livingEntity) {
                livingEntity.removeEffect(MobEffects.FIRE_RESISTANCE);
            }
        }
    }
}
