package com.mystic.atlantis.networking.packets.clientbound;

import java.util.function.Supplier;

import com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator;
import com.mystic.atlantis.inventory.CrystalGeneratorMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;

public class EnergySyncS2CPacket {
    private int energy;
    private final BlockPos pos;

    public EnergySyncS2CPacket(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public static EnergySyncS2CPacket decode(FriendlyByteBuf buf) {
        return new EnergySyncS2CPacket(buf.readInt(), buf.readBlockPos());
    }

    public static void encode(EnergySyncS2CPacket energySyncS2CPacket, FriendlyByteBuf buf) {
        buf.writeInt(energySyncS2CPacket.energy);
        buf.writeBlockPos(energySyncS2CPacket.pos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                if (Minecraft.getInstance().level != null) {
                    if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof CrystalGenerator blockEntity) {
                        blockEntity.ENERGY_STORAGE.setEnergy(energy);

                        if (Minecraft.getInstance().player.containerMenu instanceof CrystalGeneratorMenu menu &&
                                menu.getBlockEntity().getBlockPos().equals(pos)) {
                            blockEntity.ENERGY_STORAGE.setEnergy(energy);
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}