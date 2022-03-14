package com.mystic.atlantis.config;

import com.mystic.atlantis.util.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Reference.MODID)
@Config.Gui.Background("atlantis:textures/block/chiseled_golden_aquamarine.png")
public final class AtlantisConfig implements ConfigData {
    public boolean islandsOn = false;
    public boolean volcanoesOn = false;
    public int minCrabSpawnHeight = 75;
    public int maxCrabSpawnHeight = 85;
    public double calciteAcceleration = 0.1d;
    public double calciteThreshold = 1.8d;

    public static AtlantisConfig get() {
        return AutoConfig.getConfigHolder (AtlantisConfig.class).getConfig();
    }
}