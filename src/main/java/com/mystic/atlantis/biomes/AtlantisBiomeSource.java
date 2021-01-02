package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.mixin.LayerAccessor;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

import java.util.Map;
import java.util.function.LongFunction;
import java.util.stream.Collectors;


public class AtlantisBiomeSource extends BiomeProvider {
    public static final Codec<AtlantisBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance.group(RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((biomeSource) -> biomeSource.BIOME_REGISTRY), Codec.LONG.fieldOf("seed").stable().forGetter((biomeSource) -> biomeSource.seed)).apply(instance, instance.stable(AtlantisBiomeSource::new)));

    public static ResourceLocation ATLANTIS_BIOME = new ResourceLocation(Reference.MODID, "atlantis_biome");
    private final Layer BIOME_SAMPLER;
    private final Registry<Biome> BIOME_REGISTRY;
    public static Registry<Biome> LAYERS_BIOME_REGISTRY;
    private final long seed;

    protected AtlantisBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        super(biomeRegistry.getEntries().stream()
                .filter(entry -> entry.getKey().getLocation().getNamespace().equals(Reference.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
        this.BIOME_REGISTRY = biomeRegistry;
        AtlantisBiomeSource.LAYERS_BIOME_REGISTRY = biomeRegistry;
        this.BIOME_SAMPLER = buildWorldProcedure(seed);
        this.seed = seed;
        AtlantisBiomeLayer.setSeed(seed);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    public static Layer buildWorldProcedure(long seed) {
        IAreaFactory<LazyArea> layerFactory = build((sand) ->
                new LazyAreaLayerContext(25, seed, sand));
        return new Layer(layerFactory);
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> stack(long seed, IAreaTransformer1 parent, IAreaFactory<T> incomingArea, int count, LongFunction<C> contextFactory) {
        IAreaFactory<T> IAreaFactory = incomingArea;

        for (int i = 0; i < count; ++i) {
            IAreaFactory = parent.apply(contextFactory.apply(seed + (long) i), IAreaFactory);
        }

        return IAreaFactory;
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> build(LongFunction<C> contextFactory) {
        IAreaFactory<T> layer = AtlantisBiomeLayer.INSTANCE.apply(contextFactory.apply(200L));
        return layer;
    }

    @Override
    public BiomeProvider getBiomeProvider(long seed) {
        return new AtlantisBiomeSource(this.BIOME_REGISTRY, seed);
    }


    public Biome sample(Registry<Biome> registry, int i, int j) {
        int k = ((LayerAccessor) this.BIOME_SAMPLER).getSampler().getValue(i, j);
        Biome biome = registry.getByValue(k);
        if (biome == null) {
            if (SharedConstants.developmentMode) {
                throw Util.pauseDevMode(new IllegalStateException("Unknown biome id: " + k));
            } else {
                return registry.getValueForKey(BiomeRegistry.getKeyFromID(0));
            }
        } else {
            return biome;
        }
    }


    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.sample(this.BIOME_REGISTRY, x, z);
    }
}

