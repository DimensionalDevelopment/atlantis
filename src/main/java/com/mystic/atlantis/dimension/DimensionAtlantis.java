package com.mystic.atlantis.dimension;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.util.Reference;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class DimensionAtlantis
{
    //public static final Identifier ATLANTIS_ID = new Identifier(Reference.MODID,  "atlantis");
    //public static final RegistryKey<DimensionType> ATLANTIS_DIMENSION_TYPE_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, ATLANTIS_ID);
    //public static final RegistryKey<World> ATLANTIS_WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, ATLANTIS_ID);

    public static final RegistryKey<World> ATLANTIS_WORLD = RegistryKey.of(Registry.WORLD_KEY, new Identifier("atlantis:atlantis"));
    public static final RegistryKey<DimensionType> ATLANTIS_DIMENSION_TYPE_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier("atlantis:atlantis"));

    public static final Identifier ATLANTIS_DIMENSION_EFFECT = new Identifier("atlantis:atlantis");

    public static DimensionType ATLANTIS_TYPE;

    public static ServerWorld ATLANTIS_DIMENSION;

    public static boolean isAtlantisDimension(World world) {
        return world != null && world.getRegistryKey().equals(ATLANTIS_WORLD);
    }

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            DimensionAtlantis.ATLANTIS_TYPE = server.getRegistryManager().get(Registry.DIMENSION_TYPE_KEY).get(ATLANTIS_DIMENSION_TYPE_KEY);
            DimensionAtlantis.ATLANTIS_DIMENSION = server.getWorld(ATLANTIS_WORLD);
        });
    }

    public static void registerBiomeSources() {
        Registry.register(Registry.BIOME_SOURCE, new Identifier(Reference.MODID, "atlantis_biome_source"), AtlantisBiomeSource.CODEC);
    }
}
