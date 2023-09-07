package com.mystic.atlantis.dimension;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.util.Reference;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DimensionAtlantis
{
    //public static final Identifier ATLANTIS_ID = new Identifier(Reference.MODID,  "atlantis");
    //public static final RegistryKey<DimensionType> ATLANTIS_DIMENSION_TYPE_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, ATLANTIS_ID);
    //public static final RegistryKey<World> ATLANTIS_WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, ATLANTIS_ID);

    public static ResourceKey<Level> ATLANTIS_WORLD = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("atlantis:atlantis"));
    public static final ResourceKey<DimensionType> ATLANTIS_DIMENSION_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation("atlantis:atlantis"));

    public static final ResourceLocation ATLANTIS_DIMENSION_EFFECT = new ResourceLocation("atlantis","skyeffect");

    public static DimensionType ATLANTIS_TYPE;

    public static ServerLevel ATLANTIS_DIMENSION;

    public static boolean isAtlantisDimension(Level world) {
        return world != null && world.dimension().equals(ATLANTIS_WORLD);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        DimensionAtlantis.ATLANTIS_TYPE = event.getServer().registryAccess().registryOrThrow(Registries.DIMENSION_TYPE).get(ATLANTIS_DIMENSION_TYPE_KEY);
        DimensionAtlantis.ATLANTIS_DIMENSION = event.getServer().getLevel(ATLANTIS_WORLD);
    }

    public static void registerBiomeSources() {
        Registry.register(BuiltInRegistries.BIOME_SOURCE, new ResourceLocation(Reference.MODID, "atlantis_biome_source"), AtlantisBiomeSource.CODEC);
    }
}
