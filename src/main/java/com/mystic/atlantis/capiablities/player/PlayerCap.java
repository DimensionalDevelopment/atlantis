package com.mystic.atlantis.capiablities.player;

import com.mystic.atlantis.capiablities.player.IPlayerCap;

public class PlayerCap implements IPlayerCap {

    private int lightValue;

    public PlayerCap() {
        this.lightValue = 0;
    }

    @Override
    public int getLightValue() {
        return this.lightValue;
    }

    @Override
    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }
}