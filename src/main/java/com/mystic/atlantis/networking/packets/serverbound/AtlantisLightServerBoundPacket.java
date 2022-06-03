package com.mystic.atlantis.networking.packets.serverbound;

import com.mystic.atlantis.capiablities.player.PlayerCapProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AtlantisLightServerBoundPacket {

    private final long pos;

    public AtlantisLightServerBoundPacket(long blockPos) {
        this.pos = blockPos;
    }

    public static void encode(AtlantisLightServerBoundPacket atlantisLightServerBoundPacket, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeLong(atlantisLightServerBoundPacket.pos);
    }

    public static AtlantisLightServerBoundPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new AtlantisLightServerBoundPacket(friendlyByteBuf.readLong());
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayer player = contextSupplier.get().getSender();
            if (player != null) {
                player.getCapability(PlayerCapProvider.PLAYER_CAP).ifPresent(cap -> {
                    cap.setLong(this.pos);
                });
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
