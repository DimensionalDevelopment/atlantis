package com.mystic.atlantis.event;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FeatureLoadingEvent
{
    @SubscribeEvent(priority= EventPriority.HIGH)
    public static void onBiomeLoading(BiomeLoadingEvent event)
    {
        if(event.getCategory() == Biome.Category.OCEAN)
        {
            AtlantisFeature.ConfiguredFeaturesAtlantis.generateUnderwaterFlowerFeature(event);
        }
    }
}
