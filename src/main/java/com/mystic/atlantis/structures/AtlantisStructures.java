package com.mystic.atlantis.structures;

import com.mystic.atlantis.util.Reference;

import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class AtlantisStructures {
    public static final StructureFeature<StructurePoolFeatureConfig> OYSTER_STRUCTURE = new OysterStructure(StructurePoolFeatureConfig.CODEC);
    public static final StructureFeature<StructurePoolFeatureConfig> ATLANTEAN_FOUNTAIN = new AtlanteanFountain(StructurePoolFeatureConfig.CODEC);
    public static final StructureFeature<StructurePoolFeatureConfig> ATLANTEAN_HOUSE_1 = new AtlantisHouse1(StructurePoolFeatureConfig.CODEC);
    public static final StructureFeature<StructurePoolFeatureConfig> ATLANTEAN_HOUSE_3 = new AtlantisHouse3(StructurePoolFeatureConfig.CODEC);
    public static final StructureFeature<StructurePoolFeatureConfig> ATLANTEAN_TOWER = new AtlantisTower(StructurePoolFeatureConfig.CODEC);


    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(new Identifier(Reference.MODID, "oyster_structure"), OYSTER_STRUCTURE)
                .defaultConfig(10 , 5 , 482739847)
                .enableSuperflat()
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new Identifier(Reference.MODID, "atlantean_fountain"), ATLANTEAN_FOUNTAIN)
                .defaultConfig(8 , 4 , 584262839)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new Identifier(Reference.MODID, "atlantean_house_1"), ATLANTEAN_HOUSE_1)
                .defaultConfig(6 , 5 , 1293091209)
                .enableSuperflat()
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new Identifier(Reference.MODID, "atlantean_house_3"), ATLANTEAN_HOUSE_3)
                .defaultConfig(7 , 4 , 965487684)
                .enableSuperflat()
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new Identifier(Reference.MODID, "atlantean_tower"), ATLANTEAN_TOWER)
                .defaultConfig(9 , 7 , 763754364)
                .enableSuperflat()
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();
    }
}
