package com.mystic.atlantis.lighting;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.networking.AtlantisPacketHandler;
import com.mystic.atlantis.networking.packets.serverbound.AtlantisLightServerBoundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

public class AtlantisChunkSkylightProvider extends SkyLightEngine {

 	volatile public static int lightValue;
	volatile public static BlockPos blockPos;

	public AtlantisChunkSkylightProvider(LightChunkGetter chunkProvider) {
		super(chunkProvider);
	}

	@Override
	protected int computeLevelFromNeighbor(long sourceId, long targetId, int level) {
		int propagatedLevel = super.computeLevelFromNeighbor(sourceId, targetId, level);

		if(propagatedLevel == 15) {
			return propagatedLevel;
		}

		AtlantisPacketHandler.INSTANCE.sendToServer(new AtlantisLightServerBoundPacket(BlockPos.of(targetId))); //sent to server side
		return lightValue; //receive from server side
	}
}