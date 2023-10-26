package com.mystic.atlantis.event;

import com.mystic.atlantis.overlay.OverlayEventHandler;
import com.mystic.atlantis.util.Reference;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Reference.MODID)
public class AClientFEvents {
    @SubscribeEvent
    public static void onOverlayRender(RegisterGuiOverlaysEvent event) {
        event.registerBelow(VanillaGuiOverlay.VIGNETTE.id(), Reference.MODID, new OverlayEventHandler());
    }
}
