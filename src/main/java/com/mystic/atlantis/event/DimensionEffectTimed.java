package com.mystic.atlantis.event;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import com.mystic.atlantis.dimension.DimensionAtlantis;

public class DimensionEffectTimed
{
    static StatusEffectInstance water_breathing = new StatusEffectInstance(StatusEffects.WATER_BREATHING, 99999, 4, false, false);
    static StatusEffectInstance haste = new StatusEffectInstance(StatusEffects.HASTE, 99999, 3,false, false);

    public static void playerTick(ServerPlayerEntity player){
        World world = player.world;
        if(world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD) {
            if (!player.getStatusEffects().contains(water_breathing)) {
                player.addStatusEffect(water_breathing);
            }
            if (!player.getStatusEffects().contains(haste)) {
                player.addStatusEffect(new StatusEffectInstance(haste));
            }
        }

        if(world.getRegistryKey() != DimensionAtlantis.ATLANTIS_WORLD){
            player.removeStatusEffect(StatusEffects.WATER_BREATHING);
            player.removeStatusEffect(StatusEffects.HASTE);
        }
    }
}
