package com.mystic.atlantis.config;

import com.mystic.atlantis.util.Reference;

import com.mystic.atlantis.world.dimension.atlantis.Dimension;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
@Config(modid = Reference.MODID)
public class AtlantisConfig {
	
    @Config.Name("Atlantis ID")
    @Config.Comment("Atlantis ID")
    public static int dimensionId = Dimension.ATLANTIS.getId();

    @Config.Name("Overworld dim ID")
    @Config.Comment("Overworld dim ID")
    public static int overworldId = 0;

    @Config.Name("Ore Spawn Chance")
    @Config.Comment("Changes the chance of ore spawn in a world, Requires a new world!!!")
    @Config.RangeInt(min = 0, max = 100)
    @Config.RequiresWorldRestart
    public static int oreSpawnChance = 65;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Reference.MODID))
        {
            ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
        }
    }
}