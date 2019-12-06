package com.mystic.dimensionatlantis.init;

import com.mystic.dimensionatlantis.world.dimension.atlantis.DimensionAtlantis;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimension 
{
	public static final DimensionType ATLANTIS = DimensionType.register("Atlantis", "_atlantis", 324987, DimensionAtlantis.class, false);
	 
	
	public static void registerDimensions() 
	{
		DimensionManager.registerDimension(324987, ATLANTIS);
	}
}
