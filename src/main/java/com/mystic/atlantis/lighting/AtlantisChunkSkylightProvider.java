package com.mystic.atlantis.lighting;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

public class AtlantisChunkSkylightProvider extends SkyLightEngine {

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
			return getLightLevel(world.getBiome(new ChunkPos(BlockPos.getX(targetId), BlockPos.getZ(targetId)).getWorldPosition()), world, propagatedLevel);
		} else {
			return propagatedLevel;
		}
	}

	public static int getLightLevel(Biome biome, Level level, int propagationLevel) {

		Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);

		if (DimensionAtlantis.isAtlantisDimension(level)) {
			if (biome.equals(biomes.get(AtlantisBiomeSource.VOLCANIC_DARKSEA))) {
				return 11;
			} else if (biome.equals(biomes.get(AtlantisBiomeSource.JELLYFISH_FIELDS))) {
				return 8;
			} else if (biome.equals(biomes.get(AtlantisBiomeSource.ATLANTEAN_ISLANDS))) {
				return 3;
			} else if (biome.equals(biomes.get(AtlantisBiomeSource.ATLANTIS_BIOME))) {
				return 3;
			} else { // else it is an Atlantean Garden!
				return 0;
			}
		}

		return propagationLevel;
	}
}