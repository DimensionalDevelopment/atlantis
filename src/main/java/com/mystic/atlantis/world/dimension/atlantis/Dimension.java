package com.mystic.atlantis.world.dimension.atlantis;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public final class Dimension {

    public static DimensionType dimensionType;

    public static final DimensionType ATLANTIS = DimensionType.register("Atlantis", "_atlantis", 324987, WorldProviderDimensionAtlantis.class, false);


    public static void registerDimensions()
    {
        DimensionManager.registerDimension(324987, ATLANTIS);
    }
}
