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
		int propagatedLevel = super.computeLevelFromNeighbor(sourceId, targetId, level);

		if (propagatedLevel == 15) {
			return propagatedLevel;
		}

		if (chunkSource.getLevel() instanceof Level world && DimensionAtlantis.isAtlantisDimension(world)) {
			try {
				blockPos = BlockPos.of(targetId);
				return getLightLevel(world);
			} catch (InterruptedException e) {
				return propagatedLevel;
			}
		}
		return propagatedLevel;
	}

	public static int getLightLevel(Level level) throws InterruptedException {
			if (level.getServer() != null && !level.isClientSide) {
				return lightValue;
			}
		return 15;
	}
}