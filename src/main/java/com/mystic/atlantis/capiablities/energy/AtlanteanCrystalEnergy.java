package com.mystic.atlantis.capiablities.energy;

import com.mystic.atlantis.networking.AtlantisPacketHandler;
import com.mystic.atlantis.networking.packets.clientbound.EnergySyncS2CPacket2;
import com.mystic.atlantis.util.ModEnergyStorage;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class AtlanteanCrystalEnergy extends ModEnergyStorage {
	public int maxReceive;
	public int maxExtract;
	private ItemStack stack;

	public AtlanteanCrystalEnergy(final int capacity, final int maxReceive, final int maxExtract, final ItemStack stack) {
		super(capacity, maxReceive, maxExtract);
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.stack = stack;
	}

	@Override
	public void onEnergyChanged() {

		AtlantisPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new EnergySyncS2CPacket2(getEnergyStored(), getItemStack()));
	}

	private ItemStack getItemStack() {
		return stack;
	}

	public boolean isFullyCharged() {
		return this.getEnergyStored() == this.getMaxEnergyStored();
	}

	public boolean isFullyDepleted() {
		return this.getEnergyStored() == 0;
	}

}