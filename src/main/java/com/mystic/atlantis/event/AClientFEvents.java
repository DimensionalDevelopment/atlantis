package com.mystic.atlantis.event;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.overlay.OverlayEventHandler;
import com.mystic.atlantis.util.Reference;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Reference.MODID)
public class AClientFEvents {
    @SubscribeEvent
    public static void onOverlayRender(RegisterGuiOverlaysEvent event) {
        event.registerBelow(VanillaGuiOverlay.VIGNETTE.id(), Atlantis.id("coconut"), new OverlayEventHandler());
    }
}
