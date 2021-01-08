package com.mystic.atlantis.dimension;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DimensionAtlantis
{
    public static final Identifier ATLANTIS_ID = new Identifier(Reference.MODID,  "atlantis");

    public static final RegistryKey<World> ATLANTIS_WORLD_KEY = RegistryKey.of(Registry.DIMENSION, ATLANTIS_ID);



    public static void registerBiomeSources() {
        Registry.register(Registry.BIOME_SOURCE, new Identifier(Reference.MODID, "atlantis"), AtlantisBiomeSource.CODEC);
    }
}
