package com.mystic.atlantis.config;

import com.mystic.atlantis.util.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AtlantisConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final AtlantisConfig INSTANCE;

    static {
        Pair<AtlantisConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(AtlantisConfig::new);
        CONFIG_SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public ForgeConfigSpec.BooleanValue islandsOn;
    public ForgeConfigSpec.BooleanValue volcanoesOn;
    public ForgeConfigSpec.IntValue minCrabSpawnHeight;
    public ForgeConfigSpec.IntValue maxCrabSpawnHeight;
    public ForgeConfigSpec.DoubleValue calciteAcceleration;
    public ForgeConfigSpec.DoubleValue calciteThreshold;



    private AtlantisConfig(ForgeConfigSpec.Builder builder) {

        this.islandsOn = builder.comment("Should Islands Generate?").define("islandsOn", false);
        this.volcanoesOn = builder.comment("Should Volcanoes Generate?").define("volcanoesOn", false);
        this.minCrabSpawnHeight = builder.comment("Minimum Crab Spawn Height").defineInRange("minCrabSpawnHeight", 75,0,128);
        this.maxCrabSpawnHeight = builder.comment("Maximum Crab Spawn Height").defineInRange("maxCrabSpawnHeight", 85,0,128);
        this.calciteAcceleration = builder.comment("Rate at which Calcite Accelerates you").defineInRange("calciteAcceleration", 0.1d,0.0d,100.0d);
        this.calciteThreshold = builder.comment("Max Calcite Velocity ").defineInRange("calciteThreshold", 1.8d,0.0d,100.0d);
    }
}