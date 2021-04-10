package com.mystic.atlantis.event;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class DimensionFoodEvent implements UseItemCallback {

    static StatusEffectInstance water_breathing = new StatusEffectInstance(StatusEffects.WATER_BREATHING, 99999, 4, false, false);
    static StatusEffectInstance haste = new StatusEffectInstance(StatusEffects.HASTE, 99999, 3, false, false);

    @Override
    public TypedActionResult<ItemStack> interact(PlayerEntity player, World world, Hand hand) {
        if(world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD)
        {

            if (!player.getStatusEffects().contains(water_breathing)) {
                player.addStatusEffect(new StatusEffectInstance(water_breathing));
            }
            if (!player.getStatusEffects().contains(haste)) {
                player.addStatusEffect(new StatusEffectInstance(haste));
            }
        }

        return TypedActionResult.pass(player.getStackInHand(hand));
    }
}
