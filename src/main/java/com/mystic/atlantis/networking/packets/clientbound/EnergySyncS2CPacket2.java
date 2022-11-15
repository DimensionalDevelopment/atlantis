package com.mystic.atlantis.networking.packets.clientbound;

import com.mystic.atlantis.inventory.CrystalGeneratorMenu;
import com.mystic.atlantis.items.item.energybased.AtlanteanAmuletItem;
import com.mystic.atlantis.items.item.energybased.AtlanteanSpearItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket2 {
    private int energy;
    private ItemStack stack;

    public EnergySyncS2CPacket2(int energy, ItemStack itemStack) {
        this.energy = energy;
        this.stack = itemStack;
    }

    public static EnergySyncS2CPacket2 decode(FriendlyByteBuf buf) {
        return new EnergySyncS2CPacket2(buf.readInt(), buf.readItem());
    }

    public static void encode(EnergySyncS2CPacket2 energySyncS2CPacket, FriendlyByteBuf buf) {
        buf.writeInt(energySyncS2CPacket.energy);
        buf.writeItem(energySyncS2CPacket.stack);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                if (Minecraft.getInstance().player != null) {
                    if (stack.getItem() instanceof AtlanteanSpearItem) {
                        AtlanteanSpearItem.getEnergyStorage(stack).setEnergy(energy);
                        if(Minecraft.getInstance().player.containerMenu instanceof CrystalGeneratorMenu) {
                            AtlanteanSpearItem.getEnergyStorage(stack).setEnergy(energy);
                        }
                    } else if (stack.getItem() instanceof AtlanteanAmuletItem) {
                        AtlanteanAmuletItem.getEnergyStorage(stack).setEnergy(energy);
                        if(Minecraft.getInstance().player.containerMenu instanceof CrystalGeneratorMenu) {
                            AtlanteanAmuletItem.getEnergyStorage(stack).setEnergy(energy);
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}