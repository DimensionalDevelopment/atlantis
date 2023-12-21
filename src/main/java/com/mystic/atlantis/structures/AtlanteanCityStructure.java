package com.mystic.atlantis.structures;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.config.AtlantisConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class AtlanteanCityStructure extends Structure {

    public static final Codec<AtlanteanCityStructure> CODEC = RecordCodecBuilder.<AtlanteanCityStructure>mapCodec(instance ->
            instance.group(AtlanteanCityStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 256).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(instance, AtlanteanCityStructure::new)).codec();


    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public AtlanteanCityStructure(Structure.StructureSettings config,
                            Holder<StructureTemplatePool> startPool,
                            Optional<ResourceLocation> startJigsawName,
                            int size,
                            HeightProvider startHeight,
                            Optional<Heightmap.Types> projectStartToHeightmap,
                            int maxDistanceFromCenter) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.@NotNull GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), context.heightAccessor().getHeight() - context.chunkGenerator().getSeaLevel(), chunkPos.getMinBlockZ());

        //Disabled until I can figure out too spilt the structure up! Don't touch!!!

        if (false) {
            Optional<Structure.GenerationStub> structurePiecesGenerator =
                    JigsawPlacement.addPieces(
                            context,
                            this.startPool, this.startJigsawName, this.size, blockPos,
                            false, this.projectStartToHeightmap, this.maxDistanceFromCenter);
            return structurePiecesGenerator;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public @NotNull StructureType<?> type() {
        return AtlantisStructures.ATLANTEAN_CITY.get();
    }
}