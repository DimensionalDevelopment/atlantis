package com.mystic.atlantis.structures;

import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class AtlantisStructures {
    public static final StructureFeature<JigsawConfiguration> OYSTER_STRUCTURE = new OysterStructure(JigsawConfiguration.CODEC);
    public static final StructureFeature<JigsawConfiguration> ATLANTEAN_FOUNTAIN = new AtlanteanFountain(JigsawConfiguration.CODEC);
    public static final StructureFeature<JigsawConfiguration> ATLANTEAN_HOUSE_1 = new AtlantisHouse1(JigsawConfiguration.CODEC);
    public static final StructureFeature<JigsawConfiguration> ATLANTEAN_HOUSE_3 = new AtlantisHouse3(JigsawConfiguration.CODEC);
    public static final StructureFeature<JigsawConfiguration> ATLANTEAN_TOWER = new AtlantisTower(JigsawConfiguration.CODEC);


    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(new ResourceLocation(Reference.MODID, "oyster_structure"), OYSTER_STRUCTURE)
                .defaultConfig(10 , 5 , 482739847)
                .enableSuperflat()
                .step(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new ResourceLocation(Reference.MODID, "atlantean_fountain"), ATLANTEAN_FOUNTAIN)
                .defaultConfig(8 , 4 , 584262839)
                .step(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new ResourceLocation(Reference.MODID, "atlantean_house_1"), ATLANTEAN_HOUSE_1)
                .defaultConfig(6 , 5 , 1293091209)
                .enableSuperflat()
                .step(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new ResourceLocation(Reference.MODID, "atlantean_house_3"), ATLANTEAN_HOUSE_3)
                .defaultConfig(7 , 4 , 965487684)
                .enableSuperflat()
                .step(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();

        FabricStructureBuilder.create(new ResourceLocation(Reference.MODID, "atlantean_tower"), ATLANTEAN_TOWER)
                .defaultConfig(9 , 7 , 763754364)
                .enableSuperflat()
                .step(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .adjustsSurface()
                .register();
    }
}
