package com.mystic.atlantis.lighting;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

import java.util.concurrent.atomic.AtomicInteger;

public class AtlantisChunkSkylightProvider extends SkyLightEngine {

	public AtlantisChunkSkylightProvider(LightChunkGetter chunkProvider) {
		super(chunkProvider);
	}

	@Override
	protected int computeLevelFromNeighbor(long sourceId, long targetId, int level) {
		int propagatedLevel = super.computeLevelFromNeighbor(sourceId, targetId, level);

		int[] lightLevel = new int[1];
		Level world = ((Level) chunkSource.getLevel());
		MinecraftServer server = world.getServer();
		if(server != null) {
			server.execute(() -> {
				Registry<Biome> biomes = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);

				BlockPos blockPos = BlockPos.of(targetId);

				Biome biome = world.getBiomeManager().getBiome(blockPos);

				if (biome == biomes.get(AtlantisBiomeSource.VOLCANIC_DARKSEA)) {
					lightLevel[0] = 11;
				} else if (biome == biomes.get(AtlantisBiomeSource.JELLYFISH_FIELDS)) {
					lightLevel[0] = 8;
				} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTEAN_ISLANDS)) {
					lightLevel[0] = 3;
				} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTIS_BIOME)) {
					lightLevel[0] = 3;
				} else if (biome == biomes.get(AtlantisBiomeSource.ATLANTEAN_GARDEN)) {
					lightLevel[0] = 0;
				}
			});
			return lightLevel[0];
		}
		return propagatedLevel;
	}
}