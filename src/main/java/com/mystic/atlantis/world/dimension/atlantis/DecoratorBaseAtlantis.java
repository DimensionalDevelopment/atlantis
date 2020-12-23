package com.mystic.atlantis.world.dimension.atlantis;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class DecoratorBaseAtlantis extends BiomeDecorator {

    @Override
    @ParametersAreNonnullByDefault
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
        if (this.decorating) {
            throw new RuntimeException("Already decorating");
        } else {
            this.chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;

            if (worldIn.provider instanceof WorldProviderDimensionAtlantis) {
                initDimensionWorldGens();

            } else {
                initOverworldWorldGens();
            }
            this.genDecorations(biome, worldIn, random);
            this.decorating = false;
        }
    }

    protected void initDimensionWorldGens() {
        this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 0);
        this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 0);
        this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 0);
        this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 0);
        this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 0);
        this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 0);
    }

    protected void initOverworldWorldGens() {
        this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
        this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
        this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
        this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize);
        this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
        this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void genDecorations(Biome biome, World worldIn, Random rand) {

        super.genDecorations(biome, worldIn, rand);

        if (TerrainGen.decorate(worldIn, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.CUSTOM)) {
            genBiomeDecorations(worldIn, rand);
        }
    }

    @ParametersAreNonnullByDefault
    protected void genBiomeDecorations(World worldIn, Random rand) {}
}


