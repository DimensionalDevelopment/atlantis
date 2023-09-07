package com.mystic.atlantis.lighting;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.event.ACommonFEvents;

import net.minecraft.core.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.lighting.SkyLightEngine;

public class AtlantisChunkSkylightProvider extends SkyLightEngine {

    public AtlantisChunkSkylightProvider(LightChunkGetter chunkProvider) {
        super(chunkProvider);
    }


    protected int lightLevelBiome(long packedPos, long lightLevel) {
        BlockPos blockPos = BlockPos.of(packedPos);
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
                        return Math.min(ACommonFEvents.map.get(biome.unwrapKey().get().location()), (int) lightLevel);
                    }
                }
            }
        }
        return 15;
    }

    @Override
    protected void propagateIncrease(long pPackedPos, long pQueueEntry, int pLightLevel) {
        BlockState blockstate = null;
        int i = this.countEmptySectionsBelowIfAtBorder(pPackedPos);

        for(Direction direction : PROPAGATION_DIRECTIONS) {
            if (LightEngine.QueueEntry.shouldPropagateInDirection(pQueueEntry, direction)) {
                long j = BlockPos.offset(pPackedPos, direction);
                if (this.storage.storingLightForSection(SectionPos.blockToSection(j))) {
                    int k = this.storage.getStoredLevel(j);
                    int l = lightLevelBiome(pPackedPos, pLightLevel) - 1;
                    if (l > k) {
                        this.mutablePos.set(j);
                        BlockState blockstate1 = this.getState(this.mutablePos);
                        int i1 = lightLevelBiome(pPackedPos, pLightLevel) - this.getOpacity(blockstate1, this.mutablePos);
                        if (i1 > k) {
                            if (blockstate == null) {
                                blockstate = LightEngine.QueueEntry.isFromEmptyShape(pQueueEntry) ? Blocks.AIR.defaultBlockState() : this.getState(this.mutablePos.set(pPackedPos));
                            }

                            if (!this.shapeOccludes(pPackedPos, blockstate, j, blockstate1, direction)) {
                                this.storage.setStoredLevel(j, i1);
                                if (i1 > 1) {
                                    this.enqueueIncrease(j, LightEngine.QueueEntry.increaseSkipOneDirection(i1, isEmptyShape(blockstate1), direction.getOpposite()));
                                }

                                this.propagateFromEmptySections(j, direction, i1, true, i);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    protected void propagateDecrease(long pPackedPos, long pLightLevel) {
        int i = this.countEmptySectionsBelowIfAtBorder(pPackedPos);
        int j = LightEngine.QueueEntry.getFromLevel(lightLevelBiome(pPackedPos, pLightLevel));

        for(Direction direction : PROPAGATION_DIRECTIONS) {
            if (LightEngine.QueueEntry.shouldPropagateInDirection(lightLevelBiome(pPackedPos, pLightLevel), direction)) {
                long k = BlockPos.offset(pPackedPos, direction);
                if (this.storage.storingLightForSection(SectionPos.blockToSection(k))) {
                    int l = this.storage.getStoredLevel(k);
                    if (l != 0) {
                        if (l <= j - 1) {
                            this.storage.setStoredLevel(k, 0);
                            this.enqueueDecrease(k, LightEngine.QueueEntry.decreaseSkipOneDirection(l, direction.getOpposite()));
                            this.propagateFromEmptySections(k, direction, l, false, i);
                        } else {
                            this.enqueueIncrease(k, LightEngine.QueueEntry.increaseOnlyOneDirection(l, false, direction.getOpposite()));
                        }
                    }
                }
            }
        }

    }
}