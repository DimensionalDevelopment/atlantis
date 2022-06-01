package com.mystic.atlantis.lighting;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

import java.util.Objects;
import java.util.Set;

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
			return getLightLevel(world, new ChunkPos(targetId * 15), propagatedLevel);
		} else {
			return propagatedLevel;
		}
	}

	public static int getLightLevel(Level level, ChunkPos chunkPos, int propagatedLevel) {


			if (level.getServer() != null && !level.isClientSide) {
				Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);

				Set<Biome> biomeSet = Objects.requireNonNull(level.getServer().getLevel(DimensionAtlantis.ATLANTIS_WORLD)).getChunkSource().getGenerator().getBiomeSource().possibleBiomes();

				Biome biome = Objects.requireNonNull(level.getChunk(chunkPos.x, chunkPos.z).getNoiseBiome(chunkPos.x, 70, chunkPos.z)); //biomes are the same throughout the whole column until 3d biomes!

				if (DimensionAtlantis.isAtlantisDimension(level)) {
					if (biome == Objects.requireNonNull(biomes.get(AtlantisBiomeSource.VOLCANIC_DARKSEA))) {
						return 11;
					} else if (biome == Objects.requireNonNull(biomes.get(AtlantisBiomeSource.JELLYFISH_FIELDS))) {
						return 8;
					} else if (biome == Objects.requireNonNull(biomes.get(AtlantisBiomeSource.ATLANTEAN_ISLANDS))) {
						return 3;
					} else if (biome == Objects.requireNonNull(biomes.get(AtlantisBiomeSource.ATLANTIS_BIOME))) {
						return 3;
					} else if (biome == Objects.requireNonNull(biomes.get(AtlantisBiomeSource.ATLANTEAN_GARDEN))) {
						return 0;
					}
				}
			}
		return propagatedLevel;
	}
}