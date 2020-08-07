package com.nosiphus.atlantis.world.dimension.atlantis;

import io.github.opencubicchunks.cubicchunks.api.util.Box;
import io.github.opencubicchunks.cubicchunks.api.util.Coords;
import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubeGeneratorsRegistry;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubePrimer;
import io.github.opencubicchunks.cubicchunks.api.worldgen.ICubeGenerator;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.CubePopulatorEvent;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.ICubicPopulator;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.event.PopulateCubeEvent;
import io.github.opencubicchunks.cubicchunks.api.worldgen.structure.ICubicStructureGenerator;
import io.github.opencubicchunks.cubicchunks.cubicgen.common.biome.CubicBiome;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.BiomeSource;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.IBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.github.opencubicchunks.cubicchunks.api.util.Coords.blockToLocal;

public class CubicChunkGeneratorAtlantis implements ICubeGenerator
{
    private IBlockState oceanBlock = Blocks.WATER.getDefaultState();
    protected World world;
    private Biome[] columnBiomes;
    private final Map<CustomGeneratorSettings.IntAABB, CubicChunkGeneratorAtlantis> areaGenerators = new HashMap<>();
    // Number of octaves for the noise function
    private final Map<Biome, ICubicPopulator> populators = new HashMap<>();

    private boolean fillCubeBiomes;

    @Nonnull
<<<<<<< Updated upstream
    private ICubicStructureGenerator caveGenerator;
=======
>>>>>>> Stashed changes
    private IBuilder terrainBuilder;
    private BiomeSource biomeSource;

    public CubicChunkGeneratorAtlantis(World world, boolean fillCubeBiomes, @Nonnull ICubicStructureGenerator caveGenerator, IBuilder terrainBuilder, BiomeSource biomeSource) {
        this.world = world;
        this.fillCubeBiomes = fillCubeBiomes;
<<<<<<< Updated upstream
        this.caveGenerator = caveGenerator;
=======
>>>>>>> Stashed changes
        this.terrainBuilder = terrainBuilder;
        this.biomeSource = biomeSource;
    }

    @Override
    public CubePrimer generateCube(int cubeX, int cubeY, int cubeZ) {
        if (!areaGenerators.isEmpty()) {
            for (CustomGeneratorSettings.IntAABB aabb : areaGenerators.keySet()) {
                if (!aabb.contains(cubeX, cubeY, cubeZ)) {
                    continue;
                }
                return areaGenerators.get(aabb).generateCube(cubeX, cubeY, cubeZ);
            }
        }
        CubePrimer primer = new CubePrimer();
        generate(primer, cubeX, cubeY, cubeZ);
<<<<<<< Updated upstream
        generateStructures(primer, new CubePos(cubeX, cubeY, cubeZ));
=======
>>>>>>> Stashed changes
        if (fillCubeBiomes) {
            fill3dBiomes(cubeX, cubeY, cubeZ, primer);
        }
        return primer;
    }

    private void fill3dBiomes(int cubeX, int cubeY, int cubeZ, CubePrimer primer) {
        int minX = cubeX * 4;
        int minY = cubeY * 4;
        int minZ = cubeZ * 4;
        for (int dx = 0; dx < 4; dx++) {
            for (int dy = 0; dy < 4; dy++) {
                for (int dz = 0; dz < 4; dz++) {
                    primer.setBiome(dx, dy, dz,
                            biomeSource.getBiome(minX + dx * 4, minY + dy * 4, minZ + dz * 4).getBiome());
                }
            }
        }
    }

    private void generate(CubePrimer cubePrimer, int cubeX, int cubeY, int cubeZ){
        BlockPos start = new BlockPos(cubeX * 4, cubeY * 2, cubeZ * 4);
        BlockPos end = start.add(4, 2, 4);
        terrainBuilder.forEachScaled(start, end, new Vec3i(4, 8, 4),
                (x, y, z, dx, dy, dz, v) ->
                        cubePrimer.setBlockState(
                                blockToLocal(x), blockToLocal(y), blockToLocal(z),
                                this.oceanBlock)
        );
    }

    @Override
    public void generateColumn(Chunk column) {
        this.columnBiomes = this.world.getBiomeProvider()
                .getBiomes(this.columnBiomes,
                        Coords.cubeToMinBlock(column.x),
                        Coords.cubeToMinBlock(column.z),
                        ICube.SIZE, ICube.SIZE);

        // Copy ids to column internal biome array
        byte[] columnBiomeArray = column.getBiomeArray();
        for (int i = 0; i < columnBiomeArray.length; ++i) {
            columnBiomeArray[i] = (byte) Biome.getIdForBiome(this.columnBiomes[i]);
        }
    }

    @Override
    public void populate(ICube cube) {
            if (!areaGenerators.isEmpty()) {
                for (CustomGeneratorSettings.IntAABB aabb : areaGenerators.keySet()) {
                    if (!aabb.contains(cube.getX(), cube.getY(), cube.getZ())) {
                        continue;
                    }
                    areaGenerators.get(aabb).populate(cube);
                    return;
                }
            }

            /**
             * If event is not canceled we will use default biome decorators and
             * cube populators from registry.
             **/
            if (!MinecraftForge.EVENT_BUS.post(new CubePopulatorEvent(world, cube))) {
                CubicBiome cubicBiome = CubicBiome.getCubic(cube.getWorld().getBiome(Coords.getCubeCenter(cube)));

                CubePos pos = cube.getCoords();
                // For surface generators we should actually use special RNG with
                // seed
                // that depends only in world seed and cube X/Z
                // but using this for surface generation doesn't cause any
                // noticeable issues
                Random rand = Coords.coordsSeedRandom(cube.getWorld().getSeed(), cube.getX(), cube.getY(), cube.getZ());
                populators.get(cubicBiome.getBiome()).generate(world, rand, pos, cubicBiome.getBiome());
                MinecraftForge.EVENT_BUS.post(new PopulateCubeEvent.Post(world, rand, pos.getX(), pos.getY(), pos.getZ(), false));
                CubeGeneratorsRegistry.generateWorld(world, rand, pos, cubicBiome.getBiome()); }
        }

    @Override
    public Box getFullPopulationRequirements(ICube iCube) {
        return RECOMMENDED_FULL_POPULATOR_REQUIREMENT;
    }

    @Override
    public Box getPopulationPregenerationRequirements(ICube iCube) {
        return RECOMMENDED_GENERATE_POPULATOR_REQUIREMENT;
    }

    @Override
    public void recreateStructures(ICube iCube) {}

    @Override
    public void recreateStructures(Chunk chunk) {}

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        return world.getBiome(blockPos).getSpawnableList(enumCreatureType);
    }

    @Nullable
    @Override
    public BlockPos getClosestStructure(String s, BlockPos blockPos, boolean b) {
        return null;
    }

<<<<<<< Updated upstream
    private void generateStructures(CubePrimer cube, CubePos cubePos) {
        this.caveGenerator.generate(world, cube, cubePos);
    }

=======
>>>>>>> Stashed changes
}