package com.mystic.atlantis.lighting;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

import java.util.Objects;

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
			return getLightLevel(world, targetId, propagatedLevel);
		} else {
			return propagatedLevel;
		}
	}

	public static int getLightLevel(Level level, Long targetId, int propagatedLevel) {

			if (level.getServer() != null && BlockPos.getY(targetId) >= 50 && BlockPos.getY(targetId) < 384 && !level.isClientSide) {
				Biome biome = Objects.requireNonNull(level.getServer().getLevel(DimensionAtlantis.ATLANTIS_WORLD)).getUncachedNoiseBiome(BlockPos.getX(targetId), BlockPos.getY(targetId), BlockPos.getZ(targetId));

				Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);

				if (DimensionAtlantis.isAtlantisDimension(level)) {
					if (biome == biomes.get(AtlantisBiomeSource.VOLCANIC_DARKSEA)) {
						return 11;
					} else if (biome == biomes.get(AtlantisBiomeSource.JELLYFISH_FIELDS)) {
						return 8;
					} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTEAN_ISLANDS)) {
						return 3;
					} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTIS_BIOME)) {
						return 3;
					} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTEAN_GARDEN)) {
						return 0;
					}
				}
			}
		return propagatedLevel;
	}
}