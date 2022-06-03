package com.mystic.atlantis.capiablities.player;

import net.minecraft.core.BlockPos;

public interface IPlayerCap {

    int getLightValue();

    void setLightValue(int lightValue);

    Long getLong();

    void setLong(long blockPos);
}