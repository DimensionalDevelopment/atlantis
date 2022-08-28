package com.mystic.atlantis.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.Atlantis;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AtlantisModifierSerializer {
    static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, "atlantis");

    static RegistryObject<Codec<AtlantisBiomeModifier>> ATLANTIS_SERIALIZED_CODEC = BIOME_MODIFIER_SERIALIZERS.register("example", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    // declare fields
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(AtlantisBiomeModifier::biomes),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(AtlantisBiomeModifier::feature)
                    // declare constructor
            ).apply(builder, AtlantisBiomeModifier::new)));

}