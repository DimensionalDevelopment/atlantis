package com.mystic.atlantis.surfacebuilder;

import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class AtlantisSurfacesBuilderRegistery {
    public static final SurfaceBuilder<TernarySurfaceConfig> ATLANTEAN_SURFACE_BUILDER = new AtlanteanSurfaceBuilder(TernarySurfaceConfig.CODEC);

    public static void registerSurfaceBuilders() {
        Registry.register(Registry.SURFACE_BUILDER, new Identifier(Reference.MODID, "atlantean_surface_builder"), ATLANTEAN_SURFACE_BUILDER);
    }
}
