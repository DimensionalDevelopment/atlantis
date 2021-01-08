package com.mystic.atlantis.structures;

import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;

public class AtlantisStructures {
    public static final StructureFeature<DefaultFeatureConfig> OYSTER_STRUCTURE = new OysterStructure(DefaultFeatureConfig.CODEC);

    public static final StructureFeature<DefaultFeatureConfig> ATLANTEAN_FOUNTAIN = new AtlanteanFountain(DefaultFeatureConfig.CODEC);

    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(new Identifier(Reference.MODID, "oyster_structure"), OYSTER_STRUCTURE)
                .defaultConfig(10 , 5 , 482739847).superflatFeature(OYSTER_STRUCTURE.configure(FeatureConfig.DEFAULT))
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new Identifier(Reference.MODID, "atlantean_fountain"), ATLANTEAN_FOUNTAIN)
                .defaultConfig(8 , 4 , 584262839)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();
    }
}
