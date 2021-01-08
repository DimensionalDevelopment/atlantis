package com.mystic.atlantis.event;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DimensionFoodEvent {

    static StatusEffectInstance water_breathing = new StatusEffectInstance(StatusEffects.WATER_BREATHING, 99999, 4, false, false).getEffectInstance();
    static StatusEffectInstance haste = new StatusEffectInstance(StatusEffects.HASTE, 99999, 3, false, false).getEffectInstance();

    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent event) {

        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)event.getEntity();
            World world = player.world;

            if(world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD_KEY)
            {

                if(player.getStatusEffects().contains(water_breathing)) {}
                else
                {
                    player.addStatusEffect(new StatusEffectInstance(water_breathing));
                }
                if(player.getStatusEffects().contains(haste)) {}
                else
                {
                    player.addStatusEffect(new StatusEffectInstance(haste));
                }
            }
        }
    }
}
