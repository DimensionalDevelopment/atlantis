package com.mystic.atlantis.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AtlantisConfig {

    public static final ModConfigSpec CONFIG_SPEC;
    public static final AtlantisConfig CONFIG;

    public ModConfigSpec.BooleanValue islandsOn;
    public ModConfigSpec.BooleanValue volcanoesOn;
    public ModConfigSpec.BooleanValue glowstoneCrystsOn;
    public ModConfigSpec.IntValue minCrabSpawnHeight;
    public ModConfigSpec.IntValue maxCrabSpawnHeight;
    public ModConfigSpec.IntValue maxDistanceOfPushBubbleColumn;
    public ModConfigSpec.DoubleValue magmaAcceleration;
    public ModConfigSpec.DoubleValue magmaThreshold;
    public ModConfigSpec.BooleanValue startInAtlantis;
    public ModConfigSpec.BooleanValue turnOnDimensionalWaterBreathing;
    public ModConfigSpec.BooleanValue turnOnDimensionalHaste;
    public ModConfigSpec.DoubleValue waterVisibility;
    public ModConfigSpec.BooleanValue shouldHavePerBiomeLighting;

    static {
        Pair<AtlantisConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(AtlantisConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }


    private AtlantisConfig(ModConfigSpec.Builder builder) {
        this.islandsOn = builder.comment("Should Islands Generate?").define("islandsOn", true);
        this.volcanoesOn = builder.comment("Should Volcanoes Generate?").define("volcanoesOn", true);
        this.glowstoneCrystsOn = builder.comment("Should Glowstone Crysts Generate?").define("glowstoneCrystsOn", true);
        this.minCrabSpawnHeight = builder.comment("Minimum Crab Spawn Height").defineInRange("minCrabSpawnHeight", 100,-64,320);
        this.maxCrabSpawnHeight = builder.comment("Maximum Crab Spawn Height").defineInRange("maxCrabSpawnHeight", 125,-64,320);
        this.magmaAcceleration = builder.comment("Rate at which Magma Accelerates you").defineInRange("magmaAcceleration", 0.1d,0.0d,100.0d);
        this.maxDistanceOfPushBubbleColumn = builder.comment("Max Distance that the Push Bubble Columns from Bubble Magma Blocks can go").defineInRange("maxDistanceOfPushBubbleColumn", 15, 0, 30);
        this.magmaThreshold = builder.comment("Max Magma Velocity ").defineInRange("magmaThreshold", 1.8d,0.0d,100.0d);
        this.startInAtlantis = builder.comment("Start In Atlantis?").define("startInAtlantis", false);
        this.turnOnDimensionalWaterBreathing = builder.comment("Should Dimension Wide Water Breathing Be On?").define("turnOnDimensionalWaterBreathing", true);
        this.turnOnDimensionalHaste = builder.comment("Should Dimension Wide Haste Be On?").define("turnOnDimensionalHaste", true);
        this.waterVisibility = builder.comment("How far is visibility in Water?").defineInRange("waterVisibility", 120.0d, 1.0d, 200.0d);
        this.shouldHavePerBiomeLighting = builder.comment("Should Per Biome Lighting for Atlantis be on? (Currently doesn't work!!!)").define("shouldHavePerBiomeLighting", false);
    }
}