package com.mystic.atlantis.event;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DimensionFoodEvent {

    static EffectInstance water_breathing = new EffectInstance(Effects.WATER_BREATHING, 99999, 4).getEffectInstance();
    static EffectInstance haste = new EffectInstance(Effects.HASTE, 99999, 3).getEffectInstance();

    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent event) {

        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)event.getEntity();
            World world = player.world;

            if(world.getDimensionKey() == DimensionAtlantis.ATLANTIS_WORLD_KEY)
            {

                if(player.getActivePotionEffects().contains(water_breathing)) {}
                else
                {
                    player.addPotionEffect(new EffectInstance(water_breathing));
                }
                if(player.getActivePotionEffects().contains(haste)) {}
                else
                {
                    player.addPotionEffect(new EffectInstance(haste));
                }
            }
        }
    }
}
