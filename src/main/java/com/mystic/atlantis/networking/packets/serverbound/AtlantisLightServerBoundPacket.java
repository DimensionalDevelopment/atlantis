package com.mystic.atlantis.networking.packets.serverbound;

import com.mystic.atlantis.capiablities.player.PlayerCapProvider;
import com.mystic.atlantis.lighting.AtlantisChunkSkylightProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AtlantisLightServerBoundPacket {

    private final BlockPos pos;

    public AtlantisLightServerBoundPacket(BlockPos blockPos) {
        this.pos = blockPos;
    }

    public static void encode(AtlantisLightServerBoundPacket atlantisLightServerBoundPacket, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(atlantisLightServerBoundPacket.pos);
    }

    public static AtlantisLightServerBoundPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new AtlantisLightServerBoundPacket(friendlyByteBuf.readBlockPos());
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayer player = contextSupplier.get().getSender();
            if(player != null) {
                player.getCapability(PlayerCapProvider.PLAYER_CAP).ifPresent(cap -> {
                    AtlantisChunkSkylightProvider.lightValue = cap.getLightValue();
                });
            }
        });
    }
}
