package com.mystic.atlantis.lighting;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.event.ACommonFEvents;
import com.mystic.atlantis.networking.AtlantisPacketHandler;
import com.mystic.atlantis.networking.packets.serverbound.AtlantisLightServerBoundPacket;
import com.mystic.atlantis.networking.proxy.ClientProxy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class AtlantisChunkSkylightProvider extends SkyLightEngine {

	public static int lightValue;

	public AtlantisChunkSkylightProvider(LightChunkGetter chunkProvider) {
		super(chunkProvider);
	}

	@Override
	protected int computeLevelFromNeighbor(long sourceId, long targetId, int level) {
		int propagatedLevel = super.computeLevelFromNeighbor(sourceId, targetId, level);

		if(FMLEnvironment.dist == Dist.CLIENT) {
			AtlantisPacketHandler.INSTANCE.sendToServer(new AtlantisLightServerBoundPacket(targetId)); //sent to server side
		}

		return lightValue; //receive from server side
	}
}