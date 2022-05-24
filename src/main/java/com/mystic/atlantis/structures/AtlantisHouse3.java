package com.mystic.atlantis.structures;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

import java.util.Optional;

public class AtlantisHouse3 extends StructureFeature<JigsawConfiguration> {

    public AtlantisHouse3(Codec<JigsawConfiguration> codec) {
        super(codec, AtlantisHouse3::createPiecesGenerator, PostPlacementProcessor.NONE);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    public static final WeightedRandomList<MobSpawnSettings.SpawnerData> STRUCTURE_MONSTERS = WeightedRandomList.create();

    public static final WeightedRandomList<MobSpawnSettings.SpawnerData> STRUCTURE_CREATURES = WeightedRandomList.create();

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

        JigsawConfiguration newConfig = new JigsawConfiguration(
                context.registryAccess().registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        .getHolderOrThrow(ResourceKey.create(Registry.TEMPLATE_POOL_REGISTRY, new ResourceLocation(Reference.MODID, "atlantean_house_3/start_pool"))),
                10
        );

        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
                context.chunkGenerator(),
                context.biomeSource(),
                context.seed(),
                context.chunkPos(),
                newConfig,
                context.heightAccessor(),
                context.validBiome(),
                context.structureManager(),
                context.registryAccess()
        );

        BlockPos spawnXZPosition = context.chunkPos().getMiddleBlockPosition(0);
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(context.chunkGenerator().getFirstOccupiedHeight(spawnXZPosition.getX(), spawnXZPosition.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor()));

        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        newContext,
                        PoolElementStructurePiece::new,
                        blockpos,
                        false,
                        false
                );

        return structurePiecesGenerator;
    }
}
