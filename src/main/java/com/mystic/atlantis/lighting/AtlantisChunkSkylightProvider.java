package com.mystic.atlantis.lighting;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

public class AtlantisChunkSkylightProvider extends SkyLightEngine {

	public static int lightValue;
	public static BlockPos blockPos;

	public AtlantisChunkSkylightProvider(LightChunkGetter chunkProvider) {
		super(chunkProvider);
	}

	@Override
	protected int computeLevelFromNeighbor(long sourceId, long targetId, int level) {
		blockPos = BlockPos.of(targetId);
		return lightValue;
	}
}