package com.mystic.dimensionatlantis.world.util;

import net.minecraft.util.math.BlockPos;

public class AtlantisPortalPosition extends BlockPos
{

	public long lastUpdateTime;

	public AtlantisPortalPosition(BlockPos pos, long lastUpdateTime)
	{
		super(pos.getX(), pos.getY(), pos.getZ());

		this.lastUpdateTime = lastUpdateTime;
	}

}

