package com.mystic.atlantis.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {

    public static final ResourceKey<Level> ATLANTIS_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation("atlantis", "atlantis"));
}
