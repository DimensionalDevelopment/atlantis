package com.mystic.atlantis.event;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DimensionEffectTimed
{
    static StatusEffectInstance water_breathing = new StatusEffectInstance(StatusEffects.WATER_BREATHING, 99999, 4, false, false).getEffectInstance();
    static StatusEffectInstance haste = new StatusEffectInstance(StatusEffects.HASTE, 99999, 3,false, false).getEffectInstance();

    @SubscribeEvent
    public static void  playerTick(TickEvent.PlayerTickEvent event){

        if(event.player != null) {
            PlayerEntity player = event.player;
            World world = player.world;

            if(world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD_KEY)
            {
                if(player.getStatusEffects().contains(water_breathing)) {}
                else
                {
                    player.addStatusEffect(water_breathing);
                }
                if(player.getStatusEffects().contains(haste)) {}
                else
                {
                    player.addStatusEffect(new StatusEffectInstance(haste));
                }
            }
            if(world.getRegistryKey() != DimensionAtlantis.ATLANTIS_WORLD_KEY){
                player.removeStatusEffect(StatusEffects.WATER_BREATHING);
                player.removeStatusEffect(StatusEffects.HASTE);

            }
        }
    }
}
