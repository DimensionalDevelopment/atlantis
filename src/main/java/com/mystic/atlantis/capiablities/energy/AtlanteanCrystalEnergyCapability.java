package com.mystic.atlantis.capiablities.energy;

import com.mystic.atlantis.util.ModEnergyStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class AtlanteanCrystalEnergyCapability {

	private static ModEnergyStorage energyCap = null;

	public static final Capability<ModEnergyStorage> ATLANTEAN_CRYSTAL_ENERGY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static void register(final RegisterCapabilitiesEvent event) {
		event.register(ModEnergyStorage.class);
	}
}