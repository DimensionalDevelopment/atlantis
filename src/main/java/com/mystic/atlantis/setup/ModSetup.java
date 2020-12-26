package com.mystic.atlantis.setup;

import com.mystic.atlantis.util.reference;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = reference.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup
{
    @SubscribeEvent
    public static void serverLoad(FMLServerStartingEvent event) {

    }

    public static void init(final FMLCommonSetupEvent event) {

    }
}

