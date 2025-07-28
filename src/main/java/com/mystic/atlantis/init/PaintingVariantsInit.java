package com.mystic.atlantis.init;

import com.mystic.atlantis.Atlantis;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class PaintingVariantsInit {
    public static final ResourceKey<PaintingVariant> SPLASH = ResourceKey.create(Registries.PAINTING_VARIANT, Atlantis.id("splash"));
    public static final ResourceKey<PaintingVariant> DRAGON = ResourceKey.create(Registries.PAINTING_VARIANT, Atlantis.id("dragon"));
    public static final ResourceKey<PaintingVariant> SUNRISE = ResourceKey.create(Registries.PAINTING_VARIANT, Atlantis.id("sunrise"));
    public static final ResourceKey<PaintingVariant> KRAKEN = ResourceKey.create(Registries.PAINTING_VARIANT, Atlantis.id("kraken"));

    public static void init(BootstrapContext<PaintingVariant> context) {
        register(context, SPLASH, 2, 2);
        register(context, DRAGON, 2, 2);
        register(context, SUNRISE, 1, 1);
        register(context, KRAKEN, 2, 4);
    }

    private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int width, int height) {
        context.register(key, new PaintingVariant(width, height, key.location()));
    }
}
