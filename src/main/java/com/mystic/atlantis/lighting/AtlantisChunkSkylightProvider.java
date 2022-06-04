package com.mystic.atlantis.lighting;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.event.ACommonFEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
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

		BlockPos blockPos = BlockPos.of(targetId);
		ChunkPos chunkPos = new ChunkPos(blockPos);

		BlockGetter blockGetter = chunkSource.getChunkForLighting(chunkPos.x, chunkPos.z);
		if (blockGetter instanceof ChunkAccess chunkAccess) {
			Biome biome = chunkAccess.getNoiseBiome(
					QuartPos.fromBlock(blockPos.getX()),
					QuartPos.fromBlock(blockPos.getY()),
					QuartPos.fromBlock(blockPos.getZ())
			);

			if(ACommonFEvents.map.containsKey(biome.getRegistryName())) {
				return Math.min(ACommonFEvents.map.get(biome.getRegistryName()), propagatedLevel);
			}
		}
		return propagatedLevel;
	}
}