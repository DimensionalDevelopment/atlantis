package com.mystic.atlantis.world.biomes;

import com.mystic.atlantis.world.dimension.atlantis.ChunkGeneratorAtlantis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class WorldTypeAtlantis extends WorldType
{

    public WorldTypeAtlantis() {
        super("atlantis");
    }

    @Override
    public BiomeProvider getBiomeProvider(World world)
    {
        return new BiomeProviderAtlantis(world.getWorldInfo());
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
        return new ChunkGeneratorAtlantis(world, false, world.getSeed());
    }

    @Override
    public int getMinimumSpawnHeight(World world)
    {
        return world.getSeaLevel() + 1;
    }

    @Override
    public double getHorizon(World world)
    {
        return 63.0D;
    }

    @Override
    public double voidFadeMagnitude()
    {
        return 0.03125D;
    }

    @Override
    public boolean handleSlimeSpawnReduction(Random random, World world)
    {
        return false;
    }

    @Override
    public void onGUICreateWorldPress(){}

    @Override
    public int getSpawnFuzz(WorldServer world, net.minecraft.server.MinecraftServer server)
    {
        return Math.max(0, server.getSpawnRadius(world));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onCustomizeButton(Minecraft mc, GuiCreateWorld guiCreateWorld){}

    @Override
    public boolean isCustomizable()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasInfoNotice()
    {
        return false;
    }
}
