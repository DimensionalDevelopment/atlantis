package com.mystic.atlantis.biomes;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Map;
import java.util.function.Supplier;

public class AtlantisModifierSerializer {
    static DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, "atlantis");

    static Supplier<MapCodec<AtlantisBiomeModifier>> ATLANTIS_SERIALIZED_CODEC = BIOME_MODIFIER_SERIALIZERS.register("atlantis", () ->
            RecordCodecBuilder.mapCodec(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(AtlantisBiomeModifier::biomes),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(AtlantisBiomeModifier::feature)
            ).apply(builder, AtlantisBiomeModifier::new)));
}