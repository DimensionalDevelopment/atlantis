package com.mystic.dimensionatlantis.init;

import com.mystic.dimensionatlantis.blocks.fluids.FluidDenseWater;
import com.mystic.dimensionatlantis.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids
{
	public static final Fluid DENSE_WATER_FLUID = new FluidDenseWater("dense_water", new ResourceLocation(Reference.MOD_ID + ":blocks/dense_water_still"), new ResourceLocation(Reference.MOD_ID + ":blocks/dense_water_flow"), new ResourceLocation(Reference.MOD_ID + ":blocks/dense_water_overlay"));

	public static void registerFluids() 
	{
		registerFluid(DENSE_WATER_FLUID);
		
	}

	private static void registerFluid(Fluid fluid)
	{
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		
	}
	
	
}
