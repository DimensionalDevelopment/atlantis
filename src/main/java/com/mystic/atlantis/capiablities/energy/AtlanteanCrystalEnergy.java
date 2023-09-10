package com.mystic.atlantis.capiablities.energy;

import com.mystic.atlantis.util.ModEnergyStorage;

public class AtlanteanCrystalEnergy extends ModEnergyStorage {
    public AtlanteanCrystalEnergy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public void onEnergyChanged() {}
}