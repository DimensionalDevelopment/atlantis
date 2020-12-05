package com.nosiphus.atlantis.world.dimension.atlantis;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;
import net.minecraft.world.gen.structure.WoodlandMansion;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld;

public class CubicChunkGeneratorAtlantis implements IChunkGenerator {
    protected static final IBlockState STONE;
    private final Random rand;
    private NoiseGeneratorOctaves minLimitPerlinNoise;
    private NoiseGeneratorOctaves maxLimitPerlinNoise;
    private NoiseGeneratorOctaves mainPerlinNoise;
    private NoiseGeneratorPerlin surfaceNoise;
    public NoiseGeneratorOctaves scaleNoise;
    public NoiseGeneratorOctaves depthNoise;
    public NoiseGeneratorOctaves forestNoise;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private final WorldType terrainType;
    private final double[] heightMap;
    private final float[] biomeWeights;
    private ChunkGeneratorSettings settings;
    private IBlockState oceanBlock;
    private double[] depthBuffer;
    private MapGenBase caveGenerator;
    private MapGenStronghold strongholdGenerator;
    private MapGenVillage villageGenerator;
    private MapGenMineshaft mineshaftGenerator;
    private MapGenScatteredFeature scatteredFeatureGenerator;
    private MapGenBase ravineGenerator;
    private StructureOceanMonument oceanMonumentGenerator;
    private WoodlandMansion woodlandMansionGenerator;
    private Biome[] biomesForGeneration;
    double[] mainNoiseRegion;
    double[] minLimitRegion;
    double[] maxLimitRegion;
    double[] depthRegion;

    public CubicChunkGeneratorAtlantis(World p_i46668_1_, long p_i46668_2_, boolean p_i46668_4_, String p_i46668_5_) {
        this.oceanBlock = Blocks.WATER.getDefaultState();
        this.depthBuffer = new double[256];
        this.caveGenerator = new MapGenCaves();
        this.strongholdGenerator = new MapGenStronghold();
        this.villageGenerator = new MapGenVillage();
        this.mineshaftGenerator = new MapGenMineshaft();
        this.scatteredFeatureGenerator = new MapGenScatteredFeature();
        this.ravineGenerator = new MapGenRavine();
        this.oceanMonumentGenerator = new StructureOceanMonument();
        this.caveGenerator = TerrainGen.getModdedMapGen(this.caveGenerator, EventType.CAVE);
        this.strongholdGenerator = (MapGenStronghold)TerrainGen.getModdedMapGen(this.strongholdGenerator, EventType.STRONGHOLD);
        this.villageGenerator = (MapGenVillage)TerrainGen.getModdedMapGen(this.villageGenerator, EventType.VILLAGE);
        this.mineshaftGenerator = (MapGenMineshaft)TerrainGen.getModdedMapGen(this.mineshaftGenerator, EventType.MINESHAFT);
        this.scatteredFeatureGenerator = (MapGenScatteredFeature)TerrainGen.getModdedMapGen(this.scatteredFeatureGenerator, EventType.SCATTERED_FEATURE);
        this.ravineGenerator = TerrainGen.getModdedMapGen(this.ravineGenerator, EventType.RAVINE);
        this.oceanMonumentGenerator = (StructureOceanMonument)TerrainGen.getModdedMapGen(this.oceanMonumentGenerator, EventType.OCEAN_MONUMENT);
        this.woodlandMansionGenerator = (WoodlandMansion)TerrainGen.getModdedMapGen(this.woodlandMansionGenerator, EventType.WOODLAND_MANSION);
        this.world = p_i46668_1_;
        this.mapFeaturesEnabled = p_i46668_4_;
        this.terrainType = p_i46668_1_.getWorldInfo().getTerrainType();
        this.rand = new Random(p_i46668_2_);
        this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
        this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.forestNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.heightMap = new double[825];
        this.biomeWeights = new float[25];

        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                float f = 10.0F / MathHelper.sqrt((float)(i * i + j * j) + 0.2F);
                this.biomeWeights[i + 2 + (j + 2) * 5] = f;
            }
        }

        if (p_i46668_5_ != null) {
            this.settings = Factory.jsonToFactory(p_i46668_5_).build();
            this.oceanBlock = this.settings.useLavaOceans ? Blocks.LAVA.getDefaultState() : Blocks.WATER.getDefaultState();
            p_i46668_1_.setSeaLevel(this.settings.seaLevel);
        }

        ContextOverworld ctx = new ContextOverworld(this.minLimitPerlinNoise, this.maxLimitPerlinNoise, this.mainPerlinNoise, this.surfaceNoise, this.scaleNoise, this.depthNoise, this.forestNoise);
        ctx = (ContextOverworld)TerrainGen.getModdedNoiseGenerators(p_i46668_1_, this.rand, ctx);
        this.minLimitPerlinNoise = ctx.getLPerlin1();
        this.maxLimitPerlinNoise = ctx.getLPerlin2();
        this.mainPerlinNoise = ctx.getPerlin();
        this.surfaceNoise = ctx.getHeight();
        this.scaleNoise = ctx.getScale();
        this.depthNoise = ctx.getDepth();
        this.forestNoise = ctx.getForest();
    }

    public void setBlocksInChunk(int p_setBlocksInChunk_1_, int p_setBlocksInChunk_2_, ChunkPrimer p_setBlocksInChunk_3_) {
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, p_setBlocksInChunk_1_ * 4 - 2, p_setBlocksInChunk_2_ * 4 - 2, 10, 10);
        this.generateHeightmap(p_setBlocksInChunk_1_ * 4, 0, p_setBlocksInChunk_2_ * 4);

        for(int i = 0; i < 4; ++i) {
            int j = i * 5;
            int k = (i + 1) * 5;

            for(int l = 0; l < 4; ++l) {
                int i1 = (j + l) * 33;
                int j1 = (j + l + 1) * 33;
                int k1 = (k + l) * 33;
                int l1 = (k + l + 1) * 33;

                for(int i2 = 0; i2 < 32; ++i2) {
                    double d0 = 0.125D;
                    double d1 = this.heightMap[i1 + i2];
                    double d2 = this.heightMap[j1 + i2];
                    double d3 = this.heightMap[k1 + i2];
                    double d4 = this.heightMap[l1 + i2];
                    double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125D;
                    double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125D;
                    double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125D;
                    double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125D;

                    for(int j2 = 0; j2 < 8; ++j2) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for(int k2 = 0; k2 < 4; ++k2) {
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * 0.25D;
                            double lvt_45_1_ = d10 - d16;

                            for(int l2 = 0; l2 < 4; ++l2) {
                                if ((lvt_45_1_ += d16) > 0.0D) {
                                    p_setBlocksInChunk_3_.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, STONE);
                                } else if (i2 * 8 + j2 < this.settings.seaLevel) {
                                    p_setBlocksInChunk_3_.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, this.oceanBlock);
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }

    }

    public void replaceBiomeBlocks(int p_replaceBiomeBlocks_1_, int p_replaceBiomeBlocks_2_, ChunkPrimer p_replaceBiomeBlocks_3_, Biome[] p_replaceBiomeBlocks_4_) {
        if (ForgeEventFactory.onReplaceBiomeBlocks(this, p_replaceBiomeBlocks_1_, p_replaceBiomeBlocks_2_, p_replaceBiomeBlocks_3_, this.world)) {
            double d0 = 0.03125D;
            this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double)(p_replaceBiomeBlocks_1_ * 16), (double)(p_replaceBiomeBlocks_2_ * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

            for(int i = 0; i < 16; ++i) {
                for(int j = 0; j < 16; ++j) {
                    Biome biome = p_replaceBiomeBlocks_4_[j + i * 16];
                    biome.genTerrainBlocks(this.world, this.rand, p_replaceBiomeBlocks_3_, p_replaceBiomeBlocks_1_ * 16 + i, p_replaceBiomeBlocks_2_ * 16 + j, this.depthBuffer[j + i * 16]);
                }
            }

        }
    }

    public Chunk generateChunk(int p_generateChunk_1_, int p_generateChunk_2_) {
        this.rand.setSeed((long)p_generateChunk_1_ * 341873128712L + (long)p_generateChunk_2_ * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.setBlocksInChunk(p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, p_generateChunk_1_ * 16, p_generateChunk_2_ * 16, 16, 16);
        this.replaceBiomeBlocks(p_generateChunk_1_, p_generateChunk_2_, chunkprimer, this.biomesForGeneration);
        if (this.settings.useCaves) {
            this.caveGenerator.generate(this.world, p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
        }

        if (this.settings.useRavines) {
            this.ravineGenerator.generate(this.world, p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
        }

        if (this.mapFeaturesEnabled) {
            if (this.settings.useMineShafts) {
                this.mineshaftGenerator.generate(this.world, p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
            }

            if (this.settings.useVillages) {
                this.villageGenerator.generate(this.world, p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
            }

            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generate(this.world, p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
            }

            if (this.settings.useTemples) {
                this.scatteredFeatureGenerator.generate(this.world, p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
            }

            if (this.settings.useMonuments) {
                this.oceanMonumentGenerator.generate(this.world, p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
            }

            if (this.settings.useMansions) {
                this.woodlandMansionGenerator.generate(this.world, p_generateChunk_1_, p_generateChunk_2_, chunkprimer);
            }
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, p_generateChunk_1_, p_generateChunk_2_);
        byte[] abyte = chunk.getBiomeArray();

        for(int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void generateHeightmap(int p_generateHeightmap_1_, int p_generateHeightmap_2_, int p_generateHeightmap_3_) {
        this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, p_generateHeightmap_1_, p_generateHeightmap_3_, 5, 5, (double)this.settings.depthNoiseScaleX, (double)this.settings.depthNoiseScaleZ, (double)this.settings.depthNoiseScaleExponent);
        float f = this.settings.coordinateScale;
        float f1 = this.settings.heightScale;
        this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, p_generateHeightmap_1_, p_generateHeightmap_2_, p_generateHeightmap_3_, 5, 33, 5, (double)(f / this.settings.mainNoiseScaleX), (double)(f1 / this.settings.mainNoiseScaleY), (double)(f / this.settings.mainNoiseScaleZ));
        this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, p_generateHeightmap_1_, p_generateHeightmap_2_, p_generateHeightmap_3_, 5, 33, 5, (double)f, (double)f1, (double)f);
        this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, p_generateHeightmap_1_, p_generateHeightmap_2_, p_generateHeightmap_3_, 5, 33, 5, (double)f, (double)f1, (double)f);
        int i = 0;
        int j = 0;

        for(int k = 0; k < 5; ++k) {
            for(int l = 0; l < 5; ++l) {
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                boolean i1 = true;
                Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];

                for(int j1 = -2; j1 <= 2; ++j1) {
                    for(int k1 = -2; k1 <= 2; ++k1) {
                        Biome biome1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
                        float f5 = this.settings.biomeDepthOffSet + biome1.getBaseHeight() * this.settings.biomeDepthWeight;
                        float f6 = this.settings.biomeScaleOffset + biome1.getHeightVariation() * this.settings.biomeScaleWeight;
                        if (this.terrainType == WorldType.AMPLIFIED && f5 > 0.0F) {
                            f5 = 1.0F + f5 * 2.0F;
                            f6 = 1.0F + f6 * 4.0F;
                        }

                        float f7 = this.biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
                        if (biome1.getBaseHeight() > biome.getBaseHeight()) {
                            f7 /= 2.0F;
                        }

                        f2 += f6 * f7;
                        f3 += f5 * f7;
                        f4 += f7;
                    }
                }

                f2 /= f4;
                f3 /= f4;
                f2 = f2 * 0.9F + 0.1F;
                f3 = (f3 * 4.0F - 1.0F) / 8.0F;
                double d7 = this.depthRegion[j] / 8000.0D;
                if (d7 < 0.0D) {
                    d7 = -d7 * 0.3D;
                }

                d7 = d7 * 3.0D - 2.0D;
                if (d7 < 0.0D) {
                    d7 /= 2.0D;
                    if (d7 < -1.0D) {
                        d7 = -1.0D;
                    }

                    d7 /= 1.4D;
                    d7 /= 2.0D;
                } else {
                    if (d7 > 1.0D) {
                        d7 = 1.0D;
                    }

                    d7 /= 8.0D;
                }

                ++j;
                double d8 = (double)f3;
                double d9 = (double)f2;
                d8 += d7 * 0.2D;
                d8 = d8 * (double)this.settings.baseSize / 8.0D;
                double d0 = (double)this.settings.baseSize + d8 * 4.0D;

                for(int l1 = 0; l1 < 33; ++l1) {
                    double d1 = ((double)l1 - d0) * (double)this.settings.stretchY * 128.0D / 256.0D / d9;
                    if (d1 < 0.0D) {
                        d1 *= 4.0D;
                    }

                    double d2 = this.minLimitRegion[i] / (double)this.settings.lowerLimitScale;
                    double d3 = this.maxLimitRegion[i] / (double)this.settings.upperLimitScale;
                    double d4 = (this.mainNoiseRegion[i] / 10.0D + 1.0D) / 2.0D;
                    double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;
                    if (l1 > 29) {
                        double d6 = (double)((float)(l1 - 29) / 3.0F);
                        d5 = d5 * (1.0D - d6) + -10.0D * d6;
                    }

                    this.heightMap[i] = d5;
                    ++i;
                }
            }
        }

    }

    public void populate(int p_populate_1_, int p_populate_2_) {
        BlockFalling.fallInstantly = true;
        int i = p_populate_1_ * 16;
        int j = p_populate_2_ * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        this.rand.setSeed(this.world.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)p_populate_1_ * k + (long)p_populate_2_ * l ^ this.world.getSeed());
        boolean flag = false;
        ChunkPos chunkpos = new ChunkPos(p_populate_1_, p_populate_2_);
        ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, p_populate_1_, p_populate_2_, flag);
        if (this.mapFeaturesEnabled) {
            if (this.settings.useMineShafts) {
                this.mineshaftGenerator.generateStructure(this.world, this.rand, chunkpos);
            }

            if (this.settings.useVillages) {
                flag = this.villageGenerator.generateStructure(this.world, this.rand, chunkpos);
            }

            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generateStructure(this.world, this.rand, chunkpos);
            }

            if (this.settings.useTemples) {
                this.scatteredFeatureGenerator.generateStructure(this.world, this.rand, chunkpos);
            }

            if (this.settings.useMonuments) {
                this.oceanMonumentGenerator.generateStructure(this.world, this.rand, chunkpos);
            }

            if (this.settings.useMansions) {
                this.woodlandMansionGenerator.generateStructure(this.world, this.rand, chunkpos);
            }
        }

        int k2;
        int j3;
        int l3;
        if (biome != Biomes.DESERT && biome != Biomes.DESERT_HILLS && this.settings.useWaterLakes && !flag && this.rand.nextInt(this.settings.waterLakeChance) == 0 && TerrainGen.populate(this, this.world, this.rand, p_populate_1_, p_populate_2_, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE)) {
            k2 = this.rand.nextInt(16) + 8;
            j3 = this.rand.nextInt(256);
            l3 = this.rand.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.WATER)).generate(this.world, this.rand, blockpos.add(k2, j3, l3));
        }

        if (!flag && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0 && this.settings.useLavaLakes && TerrainGen.populate(this, this.world, this.rand, p_populate_1_, p_populate_2_, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA)) {
            k2 = this.rand.nextInt(16) + 8;
            j3 = this.rand.nextInt(this.rand.nextInt(248) + 8);
            l3 = this.rand.nextInt(16) + 8;
            if (j3 < this.world.getSeaLevel() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0) {
                (new WorldGenLakes(Blocks.LAVA)).generate(this.world, this.rand, blockpos.add(k2, j3, l3));
            }
        }

        if (this.settings.useDungeons && TerrainGen.populate(this, this.world, this.rand, p_populate_1_, p_populate_2_, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON)) {
            for(k2 = 0; k2 < this.settings.dungeonChance; ++k2) {
                j3 = this.rand.nextInt(16) + 8;
                l3 = this.rand.nextInt(256);
                int l1 = this.rand.nextInt(16) + 8;
                (new WorldGenDungeons()).generate(this.world, this.rand, blockpos.add(j3, l3, l1));
            }
        }

        biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
        if (TerrainGen.populate(this, this.world, this.rand, p_populate_1_, p_populate_2_, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS)) {
            WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
        }

        blockpos = blockpos.add(8, 0, 8);
        if (TerrainGen.populate(this, this.world, this.rand, p_populate_1_, p_populate_2_, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE)) {
            for(k2 = 0; k2 < 16; ++k2) {
                for(j3 = 0; j3 < 16; ++j3) {
                    BlockPos blockpos1 = this.world.getPrecipitationHeight(blockpos.add(k2, 0, j3));
                    BlockPos blockpos2 = blockpos1.down();
                    if (this.world.canBlockFreezeWater(blockpos2)) {
                        this.world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
                    }

                    if (this.world.canSnowAt(blockpos1, true)) {
                        this.world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
                    }
                }
            }
        }

        ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, p_populate_1_, p_populate_2_, flag);
        BlockFalling.fallInstantly = false;
    }

    public boolean generateStructures(Chunk p_generateStructures_1_, int p_generateStructures_2_, int p_generateStructures_3_) {
        boolean flag = false;
        if (this.settings.useMonuments && this.mapFeaturesEnabled && p_generateStructures_1_.getInhabitedTime() < 3600L) {
            flag |= this.oceanMonumentGenerator.generateStructure(this.world, this.rand, new ChunkPos(p_generateStructures_2_, p_generateStructures_3_));
        }

        return flag;
    }

    public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType p_getPossibleCreatures_1_, BlockPos p_getPossibleCreatures_2_) {
        Biome biome = this.world.getBiome(p_getPossibleCreatures_2_);
        if (this.mapFeaturesEnabled) {
            if (p_getPossibleCreatures_1_ == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.isSwampHut(p_getPossibleCreatures_2_)) {
                return this.scatteredFeatureGenerator.getMonsters();
            }

            if (p_getPossibleCreatures_1_ == EnumCreatureType.MONSTER && this.settings.useMonuments && this.oceanMonumentGenerator.isPositionInStructure(this.world, p_getPossibleCreatures_2_)) {
                return this.oceanMonumentGenerator.getMonsters();
            }
        }

        return biome.getSpawnableList(p_getPossibleCreatures_1_);
    }

    public boolean isInsideStructure(World p_isInsideStructure_1_, String p_isInsideStructure_2_, BlockPos p_isInsideStructure_3_) {
        if (!this.mapFeaturesEnabled) {
            return false;
        } else if ("Stronghold".equals(p_isInsideStructure_2_) && this.strongholdGenerator != null) {
            return this.strongholdGenerator.isInsideStructure(p_isInsideStructure_3_);
        } else if ("Mansion".equals(p_isInsideStructure_2_) && this.woodlandMansionGenerator != null) {
            return this.woodlandMansionGenerator.isInsideStructure(p_isInsideStructure_3_);
        } else if ("Monument".equals(p_isInsideStructure_2_) && this.oceanMonumentGenerator != null) {
            return this.oceanMonumentGenerator.isInsideStructure(p_isInsideStructure_3_);
        } else if ("Village".equals(p_isInsideStructure_2_) && this.villageGenerator != null) {
            return this.villageGenerator.isInsideStructure(p_isInsideStructure_3_);
        } else if ("Mineshaft".equals(p_isInsideStructure_2_) && this.mineshaftGenerator != null) {
            return this.mineshaftGenerator.isInsideStructure(p_isInsideStructure_3_);
        } else {
            return "Temple".equals(p_isInsideStructure_2_) && this.scatteredFeatureGenerator != null ? this.scatteredFeatureGenerator.isInsideStructure(p_isInsideStructure_3_) : false;
        }
    }

    @Nullable
    public BlockPos getNearestStructurePos(World p_getNearestStructurePos_1_, String p_getNearestStructurePos_2_, BlockPos p_getNearestStructurePos_3_, boolean p_getNearestStructurePos_4_) {
        if (!this.mapFeaturesEnabled) {
            return null;
        } else if ("Stronghold".equals(p_getNearestStructurePos_2_) && this.strongholdGenerator != null) {
            return this.strongholdGenerator.getNearestStructurePos(p_getNearestStructurePos_1_, p_getNearestStructurePos_3_, p_getNearestStructurePos_4_);
        } else if ("Mansion".equals(p_getNearestStructurePos_2_) && this.woodlandMansionGenerator != null) {
            return this.woodlandMansionGenerator.getNearestStructurePos(p_getNearestStructurePos_1_, p_getNearestStructurePos_3_, p_getNearestStructurePos_4_);
        } else if ("Monument".equals(p_getNearestStructurePos_2_) && this.oceanMonumentGenerator != null) {
            return this.oceanMonumentGenerator.getNearestStructurePos(p_getNearestStructurePos_1_, p_getNearestStructurePos_3_, p_getNearestStructurePos_4_);
        } else if ("Village".equals(p_getNearestStructurePos_2_) && this.villageGenerator != null) {
            return this.villageGenerator.getNearestStructurePos(p_getNearestStructurePos_1_, p_getNearestStructurePos_3_, p_getNearestStructurePos_4_);
        } else if ("Mineshaft".equals(p_getNearestStructurePos_2_) && this.mineshaftGenerator != null) {
            return this.mineshaftGenerator.getNearestStructurePos(p_getNearestStructurePos_1_, p_getNearestStructurePos_3_, p_getNearestStructurePos_4_);
        } else {
            return "Temple".equals(p_getNearestStructurePos_2_) && this.scatteredFeatureGenerator != null ? this.scatteredFeatureGenerator.getNearestStructurePos(p_getNearestStructurePos_1_, p_getNearestStructurePos_3_, p_getNearestStructurePos_4_) : null;
        }
    }

    public void recreateStructures(Chunk p_recreateStructures_1_, int p_recreateStructures_2_, int p_recreateStructures_3_) {
        if (this.mapFeaturesEnabled) {
            if (this.settings.useMineShafts) {
                this.mineshaftGenerator.generate(this.world, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkPrimer)null);
            }

            if (this.settings.useVillages) {
                this.villageGenerator.generate(this.world, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkPrimer)null);
            }

            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generate(this.world, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkPrimer)null);
            }

            if (this.settings.useTemples) {
                this.scatteredFeatureGenerator.generate(this.world, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkPrimer)null);
            }

            if (this.settings.useMonuments) {
                this.oceanMonumentGenerator.generate(this.world, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkPrimer)null);
            }

            if (this.settings.useMansions) {
                this.woodlandMansionGenerator.generate(this.world, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkPrimer)null);
            }
        }

    }

    static {
        STONE = Blocks.STONE.getDefaultState();
    }
}

