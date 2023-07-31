package com.mystic.atlantis.mixin;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.event.ACommonFEvents;
import net.caffeinemc.phosphor.mixin.chunk.light.MixinChunkSkyLightProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.lighting.SkyLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = {SkyLightEngine.class}, priority = 2000)
public abstract class SkyLightEngineRadonFix {

    /**
     * @author Mysticpasta1
     * @reason fix radon messing with my per biome lighting code!
     */
    @Overwrite
    public int computeLevelFromNeighbor(long fromId, long toId, int currentLevel) {
        if(chunkSource.getLevel() instanceof Level level) {
            if (DimensionAtlantis.isAtlantisDimension(level)) {
                int propagatedLevel = getPropagatedLevel(fromId, null, toId, currentLevel);

                BlockPos blockPos = BlockPos.of(toId);
                ChunkPos chunkPos = new ChunkPos(blockPos);

                BlockGetter blockGetter = chunkSource.getChunkForLighting(chunkPos.x, chunkPos.z);
                if (blockGetter instanceof ChunkAccess chunkAccess) {
                    Holder<Biome> biome = chunkAccess.getNoiseBiome(
                            QuartPos.fromBlock(blockPos.getX()),
                            QuartPos.fromBlock(blockPos.getY()),
                            QuartPos.fromBlock(blockPos.getZ())
                    );
                    if (biome.unwrapKey().isPresent()) {
                        if (ACommonFEvents.map != null) {
                            if (ACommonFEvents.map.containsKey(biome.unwrapKey().get().location())) {
                                return Math.min(ACommonFEvents.map.get(biome.unwrapKey().get().location()), propagatedLevel);
                            }
                        }
                    }
                }
            }
        }
        return this.getPropagatedLevel(fromId, null, toId, currentLevel);
    }
}
