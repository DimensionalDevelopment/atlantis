package com.mystic.atlantis.networking.packets.clientbound;

import com.mystic.atlantis.networking.proxy.ClientProxy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record AtlantisLightClientBoundPacket(int lightValue) {

    public static void encode(AtlantisLightClientBoundPacket atlantisLightClientBoundPacket, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(atlantisLightClientBoundPacket.lightValue);
    }

    public static AtlantisLightClientBoundPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new AtlantisLightClientBoundPacket(friendlyByteBuf.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientProxy.setLightValue(this.lightValue);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
