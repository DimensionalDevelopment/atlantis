package com.mystic.atlantis.event;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class OreGenerationEvent
{
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event)
    {
        if(event.getCategory() != Biome.Category.THEEND || event.getCategory() != Biome.Category.NETHER)
        {
            AtlantisFeature.ConfiguredFeaturesAtlantis.generateAquamarineOreFeature(event);
        }
    }
}
