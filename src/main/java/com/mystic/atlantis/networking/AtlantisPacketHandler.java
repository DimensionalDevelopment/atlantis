package com.mystic.atlantis.networking;

import com.mystic.atlantis.networking.packets.clientbound.AtlantisLightClientBoundPacket;
import com.mystic.atlantis.networking.packets.clientbound.EnergySyncS2CPacket;
import com.mystic.atlantis.networking.packets.serverbound.AtlantisLightServerBoundPacket;
import com.mystic.atlantis.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AtlantisPacketHandler {
    private static int index = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Reference.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SubscribeEvent
    public static void registryNetworkPackets (FMLCommonSetupEvent event) {
        INSTANCE.registerMessage(index++, AtlantisLightServerBoundPacket.class, AtlantisLightServerBoundPacket::encode, AtlantisLightServerBoundPacket::decode, AtlantisLightServerBoundPacket::handle);
        INSTANCE.registerMessage(index++, AtlantisLightClientBoundPacket.class, AtlantisLightClientBoundPacket::encode, AtlantisLightClientBoundPacket::decode, AtlantisLightClientBoundPacket::handle);
        INSTANCE.registerMessage(index++, EnergySyncS2CPacket.class, EnergySyncS2CPacket::encode, EnergySyncS2CPacket::decode, EnergySyncS2CPacket::handle);
    }
}
