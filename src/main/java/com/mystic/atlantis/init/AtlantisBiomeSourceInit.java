package com.mystic.atlantis.init;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class AtlantisBiomeSourceInit {
    public static void register() {
        Registry.register(BuiltInRegistries.BIOME_SOURCE, 
            new ResourceLocation(Reference.MODID, "atlantis_biome_source"),
            AtlantisBiomeSource.CODEC);
    }
}
