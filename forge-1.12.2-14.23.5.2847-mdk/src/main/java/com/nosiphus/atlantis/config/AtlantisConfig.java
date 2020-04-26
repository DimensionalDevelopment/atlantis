package com.nosiphus.atlantis.config;

import com.nosiphus.atlantis.init.ModDimension;
import com.nosiphus.atlantis.util.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
@Config(modid = Reference.MODID)
public class AtlantisConfig {
	
    @Config.Name("Atlantis ID")
    @Config.RangeInt(min = -1000, max = 1000)
    @Config.Comment("Atlantis ID")
    public static int dimensionId = ModDimension.ATLANTIS.getId();
    @Config.Name("Overworld dim ID")
    @Config.RangeInt(min = -1000, max = 1000)
    @Config.Comment("Overworld dim ID")
    public static int overworldId = 0;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Reference.MODID))
        {
            ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
        }
    }
}