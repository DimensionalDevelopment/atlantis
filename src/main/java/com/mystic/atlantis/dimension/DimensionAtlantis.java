package com.mystic.atlantis.dimension;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DimensionAtlantis
{
    public static final ResourceLocation ATLANTIS_ID = new ResourceLocation(Reference.MODID,  "atlantis");

    public static final RegistryKey<World> ATLANTIS_WORLD_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ATLANTIS_ID);



    public static void registerBiomeSources() {
        Registry.register(Registry.BIOME_PROVIDER_CODEC, new ResourceLocation(Reference.MODID, "atlantis"), AtlantisBiomeSource.CODEC);
    }

   /*@OnlyIn(Dist.CLIENT)
    public static void registerDimensionRenderens() {
        DimensionRenderInfo.field_239208_a_.put(ATLANTIS_ID, new AtlantisDimensionRenderer());
    }*/
}
