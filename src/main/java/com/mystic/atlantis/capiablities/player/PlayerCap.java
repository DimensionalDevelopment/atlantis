package com.mystic.atlantis.capiablities.player;

import com.mystic.atlantis.capiablities.player.IPlayerCap;
import net.minecraft.core.BlockPos;

public class PlayerCap implements IPlayerCap {

    private int lightValue;

    private Long pos;

    public PlayerCap() {
        this.lightValue = 0;
        this.pos = 0L;
    }

    @Override
    public int getLightValue() {
        return this.lightValue;
    }

    @Override
    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }

    @Override
    public Long getLong() {
        return this.pos;
    }

    @Override
    public void setLong(long blockPos) {
        this.pos = blockPos;
    }
}