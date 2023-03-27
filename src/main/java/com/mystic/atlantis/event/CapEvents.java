package com.mystic.atlantis.event;

import static com.mystic.atlantis.util.Reference.MODID;

import com.mystic.atlantis.capiablities.player.PlayerCapProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MODID)
public class CapEvents {

    @SubscribeEvent
    public static void onAttachPlayerCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !event.getObject().getCapability(PlayerCapProvider.PLAYER_CAP).isPresent()) {
            event.addCapability(new ResourceLocation(MODID, "playereffectcap"), new PlayerCapProvider());
        }
    }
}
