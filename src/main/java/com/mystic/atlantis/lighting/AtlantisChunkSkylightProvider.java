package com.mystic.atlantis.lighting;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

public class AtlantisChunkSkylightProvider extends SkyLightEngine {

	public AtlantisChunkSkylightProvider(LightChunkGetter chunkProvider) {
		super(chunkProvider);
	}

	@Override
	protected int computeLevelFromNeighbor(long sourceId, long targetId, int level) {
		int propagatedLevel = super.computeLevelFromNeighbor(sourceId, targetId, level);

		Level world = (Level) chunkSource.getLevel();
		Registry<Biome> biomes = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);
		BlockPos blockPos = BlockPos.of(targetId);
		ChunkPos chunkPos = new ChunkPos(blockPos);

		BlockGetter blockGetter = chunkSource.getChunkForLighting(chunkPos.x, chunkPos.z);
		if (blockGetter instanceof ChunkAccess chunkAccess) {
			Biome biome = chunkAccess.getNoiseBiome(blockPos.getX(), blockPos.getY(), blockPos.getZ());

			if (biome == biomes.get(AtlantisBiomeSource.VOLCANIC_DARKSEA)) {
				return Math.max(11, propagatedLevel);
			} else if (biome == biomes.get(AtlantisBiomeSource.JELLYFISH_FIELDS)) {
				return Math.max(8, propagatedLevel);
			} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTEAN_ISLANDS)) {
				return Math.max(3, propagatedLevel);
			} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTIS_BIOME)) {
				return Math.max(3, propagatedLevel);
			} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTEAN_GARDEN)) {
				return propagatedLevel; //Math.max(0, propagatedLevel) == propagatedLevel
			}
		}
		return propagatedLevel;
	}
}