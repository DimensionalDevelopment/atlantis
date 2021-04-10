package com.mystic.atlantis.structures;

import com.google.common.collect.ImmutableList;

import java.util.List;

import net.minecraft.class_6130;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePoolElementType;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import com.mojang.serialization.Codec;

import com.mystic.atlantis.util.Reference;

public class AtlanteanFountain extends StructureFeature<DefaultFeatureConfig> {

    public AtlanteanFountain(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return AtlanteanFountain.Start::new;
    }


    @Override
    public GenerationStep.Feature getGenerationStep() {
        return GenerationStep.Feature.SURFACE_STRUCTURES;
    }

    private static final List<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = ImmutableList.of();

    @Override
    public Pool<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return (Pool<SpawnSettings.SpawnEntry>) STRUCTURE_MONSTERS;
    }

    private static final List<SpawnSettings.SpawnEntry> STRUCTURE_CREATURES = ImmutableList.of();

    @Override
    public Pool<SpawnSettings.SpawnEntry> getCreatureSpawns() {
        return (Pool<SpawnSettings.SpawnEntry>) STRUCTURE_CREATURES;
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureIn, ChunkPos pos, int referenceIn, long seedIn) {
            super(structureIn, pos, referenceIn, seedIn);

        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManagerIn, ChunkPos pos, Biome biomeIn, DefaultFeatureConfig config, HeightLimitView world) {

            int x = (pos.x  << 4) + 7;
            int z = (pos.z << 4) + 7;

            BlockPos blockpos = new BlockPos(x, (chunkGenerator.getHeight((pos.x*16), (pos.z*16), Heightmap.Type.OCEAN_FLOOR, world)), z);

            StructurePoolBasedGenerator.method_30419(
                    dynamicRegistryManager,
                    new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.STRUCTURE_POOL_KEY).get(new Identifier(Reference.MODID, "atlantean_fountain/start_pool")), 10), PoolStructurePiece::new, chunkGenerator, templateManagerIn ,blockpos, (this) , this.random, false, false, world);

            this.children.forEach(piece -> piece.translate(0, 1, 0));
            this.children.forEach(piece -> piece.getBoundingBox().getMinY());

            this.setBoundingBoxFromChildren();

        }
    }
}
