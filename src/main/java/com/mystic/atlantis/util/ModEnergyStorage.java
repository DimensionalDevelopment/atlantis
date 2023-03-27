package com.mystic.atlantis.util;

public abstract class ModEnergyStorage extends net.minecraftforge.energy.EnergyStorage {
    public boolean comesFromCrystal = false;
    private int receiveEnergyFromCrystal;
    private boolean energyGaining;
    private boolean energyLosing;
    private final int capacity;
    private final int maxReceive;
    private final int maxExtract;

    public ModEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.receiveEnergyFromCrystal = 0;
    }

    public boolean receivedEnergyFromCrystal() {
        return receiveEnergyFromCrystal / energyPerCrystal() % 1 == 0;
    }

    public boolean receivingEnergyFromCrystal() {
        return receiveEnergyFromCrystal > 0 && receivingEnergy();
    }

    private boolean receivingEnergy() {
        if(isReceivingEnergy()) {
            return true;
        } else if (isLosingEnergy()) {
            return false;
        } else if (isEnergyNotChanging()) {
            return false;
        } else { // do nothing and return false if energy is being in a paradoxical state!!!
            return false;
        }
    }

    private boolean extractingEnergy() {
        if(isLosingEnergy()) {
            return true;
        } else if (isReceivingEnergy()) {
            return false;
        } else if (isEnergyNotChanging()) {
            return false;
        } else { // do nothing and return false if energy is being in a paradoxical state!!!
            return false;
        }
    }

    private boolean isLosingEnergy() {
        return energyLosing;
    }

    public boolean isEnergyNotChanging() {
        return !energyGaining && !energyLosing;
    }

    public boolean isReceivingEnergy() {
        return energyGaining;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            energy += energyReceived;
            if (energyReceived != 0) {
                if(comesFromCrystal) {
                    for (int i = 0; i <= 32; i++) {
                        receiveEnergyFromCrystal++;
                        onEnergyChanged();
                        energyGaining = true;
                    }
                } else {
                    onEnergyChanged();
                    energyGaining = true;
                }
            }
        } else {
            if (isLosingEnergy()) {
                energyGaining = false;
            } else {
                energyGaining = false;
                energyLosing = false;
            }
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            if (energyExtracted != 0) {
                onEnergyChanged();
                energyLosing = true;
            } else {
                if (isReceivingEnergy()) {
                    energyLosing = false;
                } else {
                    energyGaining = false;
                    energyLosing = false;
                }
            }
        }
        return energyExtracted;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    public int energyPerCrystal() {
        return 32;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    public abstract void onEnergyChanged();

    public int getReceivedEnergy() {
        return this.maxReceive;
    }

    public int getExtractedEnergy() {
        return this.maxExtract;
    }
}
