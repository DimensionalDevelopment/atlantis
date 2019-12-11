package com.mystic.dimensionatlantis.blocks.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidDenseWater extends Fluid{

	public FluidDenseWater(String name, ResourceLocation still, ResourceLocation flow, ResourceLocation overlay) {
		super(name, still, flow, overlay);
		this.setUnlocalizedName(name);
		this.color = 0x9300FE;
		this.density = 1500;
		this.temperature = 270;
		
	}

}
