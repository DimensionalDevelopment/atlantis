package com.mystic.atlantis.lighting;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

import java.util.Objects;

public class AtlantisChunkSkylightProvider extends SkyLightEngine {

	public static int lightValue;

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