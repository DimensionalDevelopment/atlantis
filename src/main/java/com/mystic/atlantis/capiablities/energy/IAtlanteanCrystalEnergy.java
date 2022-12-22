package com.mystic.atlantis.capiablities.energy;

import net.minecraftforge.energy.IEnergyStorage;

public interface IAtlanteanCrystalEnergy extends IEnergyStorage {

    default boolean isFullyCharged() {
        return this.getEnergyStored() == this.getMaxEnergyStored();
    }

    default boolean isFullyDepleted() {
        return this.getEnergyStored() == 0;
    }
}