package com.mystic.atlantis.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AtlantisConfig {

    public AtlantisConfig() {}

    public static final ModConfigSpec CONFIG_SPEC;
    public static final AtlantisConfig INSTANCE;

    static {
        Pair<AtlantisConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(AtlantisConfig::new);
        CONFIG_SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public static ModConfigSpec.BooleanValue islandsOn;
    public static ModConfigSpec.BooleanValue volcanoesOn;
    public static ModConfigSpec.BooleanValue glowstoneCrystsOn;
    public static ModConfigSpec.IntValue minCrabSpawnHeight;
    public static ModConfigSpec.IntValue maxCrabSpawnHeight;
    public static ModConfigSpec.IntValue maxDistanceOfPushBubbleColumn;
    public static ModConfigSpec.DoubleValue magmaAcceleration;
    public static ModConfigSpec.DoubleValue magmaThreshold;
    public static ModConfigSpec.BooleanValue startInAtlantis;
    public static ModConfigSpec.BooleanValue turnOnDimensionalWaterBreathing;
    public static ModConfigSpec.BooleanValue turnOnDimensionalHaste;
    public static ModConfigSpec.DoubleValue waterVisibility;
    public static ModConfigSpec.BooleanValue shouldCitiesGenerate;
    public static ModConfigSpec.BooleanValue shouldHavePerBiomeLighting;



    private AtlantisConfig(ModConfigSpec.Builder builder) {

        islandsOn = builder.comment("Should Islands Generate?").define("islandsOn", true);
        volcanoesOn = builder.comment("Should Volcanoes Generate?").define("volcanoesOn", true);
        glowstoneCrystsOn = builder.comment("Should Glowstone Crysts Generate?").define("glowstoneCrystsOn", true);
        minCrabSpawnHeight = builder.comment("Minimum Crab Spawn Height").defineInRange("minCrabSpawnHeight", 100,-64,320);
        maxCrabSpawnHeight = builder.comment("Maximum Crab Spawn Height").defineInRange("maxCrabSpawnHeight", 125,-64,320);
        magmaAcceleration = builder.comment("Rate at which Magma Accelerates you").defineInRange("magmaAcceleration", 0.1d,0.0d,100.0d);
        maxDistanceOfPushBubbleColumn = builder.comment("Max Distance that the Push Bubble Columns from Bubble Magma Blocks can go").defineInRange("maxDistanceOfPushBubbleColumn", 15, 0, 30);
        magmaThreshold = builder.comment("Max Magma Velocity ").defineInRange("magmaThreshold", 1.8d,0.0d,100.0d);
        startInAtlantis = builder.comment("Start In Atlantis?").define("startInAtlantis", false);
        turnOnDimensionalWaterBreathing = builder.comment("Should Dimension Wide Water Breathing Be On?").define("turnOnDimensionalWaterBreathing", true);
        turnOnDimensionalHaste = builder.comment("Should Dimension Wide Haste Be On?").define("turnOnDimensionalHaste", true);
        waterVisibility = builder.comment("How far is visibility in Water?").defineInRange("waterVisibility", 120.0d, 1.0d, 200.0d);
        shouldCitiesGenerate = builder.comment("Can Atlantean Cities generate? (Cities are disable for now without config - this does nothing!)").define("shouldCitiesGenerate", false);
        shouldHavePerBiomeLighting = builder.comment("Should Per Biome Lighting for Atlantis be on? (Currently doesn't work!!!)").define("shouldHavePerBiomeLighting", false);
    }
}