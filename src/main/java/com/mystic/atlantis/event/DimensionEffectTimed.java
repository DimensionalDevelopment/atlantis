package com.mystic.atlantis.event;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DimensionEffectTimed
{
    static EffectInstance water_breathing = new EffectInstance(Effects.WATER_BREATHING, 99999, 4, false, false).getEffectInstance();
    static EffectInstance haste = new EffectInstance(Effects.HASTE, 99999, 3,false, false).getEffectInstance();

    @SubscribeEvent
    public static void  playerTick(TickEvent.PlayerTickEvent event){

        if(event.player != null) {
            PlayerEntity player = event.player;
            World world = player.world;

            if(world.getDimensionKey() == DimensionAtlantis.ATLANTIS_WORLD_KEY)
            {
                if(player.getActivePotionEffects().contains(water_breathing)) {}
                else
                {
                    player.addPotionEffect(water_breathing);
                }
                if(player.getActivePotionEffects().contains(haste)) {}
                else
                {
                    player.addPotionEffect(new EffectInstance(haste));
                }
            }
            if(world.getDimensionKey() != DimensionAtlantis.ATLANTIS_WORLD_KEY){
                player.removePotionEffect(Effects.WATER_BREATHING);
                player.removePotionEffect(Effects.HASTE);

            }
        }
    }
}
