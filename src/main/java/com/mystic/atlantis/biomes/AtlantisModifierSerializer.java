package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AtlantisModifierSerializer {
    static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, "atlantis");

    static Supplier<Codec<AtlantisBiomeModifier>> ATLANTIS_SERIALIZED_CODEC = BIOME_MODIFIER_SERIALIZERS.register("atlantis", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    // declare fields
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(AtlantisBiomeModifier::biomes),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(AtlantisBiomeModifier::feature)
                    // declare constructor
            ).apply(builder, AtlantisBiomeModifier::new)));

}