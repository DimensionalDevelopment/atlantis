package com.mystic.atlantis.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mystic.atlantis.util.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
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
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.List;
import java.util.Optional;

public class AtlantisHouse1 extends StructureFeature<StructurePoolFeatureConfig> {

    public AtlantisHouse1(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, AtlantisHouse1::createPiecesGenerator, PostPlacementProcessor.EMPTY);
    }

    @Override
    public GenerationStep.Feature getGenerationStep() {
        return GenerationStep.Feature.SURFACE_STRUCTURES;
    }

    public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = Pool.of();

    public static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_CREATURES = Pool.of();

    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {

        StructurePoolFeatureConfig newConfig = new StructurePoolFeatureConfig(
                () -> context.registryManager().get(Registry.STRUCTURE_POOL_KEY)
                        .get(new Identifier(Reference.MODID, "atlantean_house_1/start_pool")),
                10
        );

        StructureGeneratorFactory.Context<StructurePoolFeatureConfig> newContext = new StructureGeneratorFactory.Context<>(
                context.chunkGenerator(),
                context.biomeSource(),
                context.seed(),
                context.chunkPos(),
                newConfig,
                context.world(),
                context.validBiome(),
                context.structureManager(),
                context.registryManager()
        );

        BlockPos spawnXZPosition = context.chunkPos().getCenterAtY(0);
        BlockPos blockpos = context.chunkPos().getCenterAtY(context.chunkGenerator().getHeightInGround(spawnXZPosition.getX(), spawnXZPosition.getZ(), Heightmap.Type.OCEAN_FLOOR_WG, context.world()));

        Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator =
                StructurePoolBasedGenerator.generate(
                        newContext,
                        PoolStructurePiece::new,
                        blockpos,
                        false,
                        false
                );

        return structurePiecesGenerator;
    }
}
