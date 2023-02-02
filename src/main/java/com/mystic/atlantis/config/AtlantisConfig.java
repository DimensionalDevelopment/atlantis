package com.mystic.atlantis.config;

import com.mystic.atlantis.blocks.PushBubbleColumn;
import me.shedaniel.autoconfig.ConfigData;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AtlantisConfig implements ConfigData {

    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final AtlantisConfig INSTANCE;

    static {
        Pair<AtlantisConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(AtlantisConfig::new);
        CONFIG_SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public ForgeConfigSpec.BooleanValue islandsOn;
    public ForgeConfigSpec.BooleanValue volcanoesOn;
    public ForgeConfigSpec.BooleanValue glowstoneCrystsOn;
    public ForgeConfigSpec.IntValue minCrabSpawnHeight;
    public ForgeConfigSpec.IntValue maxCrabSpawnHeight;
    public ForgeConfigSpec.DoubleValue magmaAcceleration;
    public ForgeConfigSpec.DoubleValue magmaThreshold;
    public ForgeConfigSpec.BooleanValue startInAtlantis;
    public ForgeConfigSpec.BooleanValue turnOnDimensionalWaterBreathing;
    public ForgeConfigSpec.BooleanValue turnOnDimensionalHaste;
    public ForgeConfigSpec.DoubleValue waterVisibility;
    public ForgeConfigSpec.BooleanValue shouldCitiesGenerate;



    private AtlantisConfig(ForgeConfigSpec.Builder builder) {

        this.islandsOn = builder.comment("Should Islands Generate?").define("islandsOn", true);
        this.volcanoesOn = builder.comment("Should Volcanoes Generate?").define("volcanoesOn", true);
        this.glowstoneCrystsOn = builder.comment("Should Glowstone Crysts Generate?").define("glowstoneCrystsOn", true);
        this.minCrabSpawnHeight = builder.comment("Minimum Crab Spawn Height").defineInRange("minCrabSpawnHeight", 100,-64,320);
        this.maxCrabSpawnHeight = builder.comment("Maximum Crab Spawn Height").defineInRange("maxCrabSpawnHeight", 125,-64,320);
        this.magmaAcceleration = builder.comment("Rate at which Magma Accelerates you").defineInRange("magmaAcceleration", 0.1d,0.0d,100.0d);
        this.magmaThreshold = builder.comment("Max Magma Velocity ").defineInRange("magmaThreshold", 1.8d,0.0d,100.0d);
        this.startInAtlantis = builder.comment("Start In Atlantis?").define("startInAtlantis", false);
        this.turnOnDimensionalWaterBreathing = builder.comment("Should Dimension Wide Water Breathing Be On?").define("turnOnDimensionalWaterBreathing", true);
        this.turnOnDimensionalHaste = builder.comment("Should Dimension Wide Haste Be On?").define("turnOnDimensionalHaste", true);
        this.waterVisibility = builder.comment("How far is visibility in Water?").defineInRange("waterVisibility", 120.0d, 1.0d, 200.0d);
        this.shouldCitiesGenerate = builder.comment("Can Atlantean Cities generate? (Warning may cause slight lag when generating!)").define("shouldCitiesGenerate", true);
    }
}