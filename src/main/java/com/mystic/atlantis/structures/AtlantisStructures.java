package com.mystic.atlantis.structures;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mystic.atlantis.util.Reference;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class AtlantisStructures {
    public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Reference.MODID);

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> OYSTER_STRUCTURE = DEFERRED_REGISTRY_STRUCTURE.register("oyster_structure", () -> (new OysterStructure(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATLANTEAN_FOUNTAIN = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_fountain", () -> (new AtlanteanFountain(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATLANTEAN_HOUSE_1 = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_house_1", () -> (new AtlantisHouse1(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATLANTEAN_HOUSE_3 = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_house_3", () -> (new AtlantisHouse3(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATLANTEAN_TOWER = DEFERRED_REGISTRY_STRUCTURE.register("atlantean_tower", () -> (new AtlantisTower(JigsawConfiguration.CODEC)));

    public static void setupStructures() {
        setupMapSpacingAndLand(OYSTER_STRUCTURE.get(), new StructureFeatureConfiguration(10, 5, 482739847), true);
        setupMapSpacingAndLand(ATLANTEAN_FOUNTAIN.get(), new StructureFeatureConfiguration(8, 4, 584262839), true);
        setupMapSpacingAndLand(ATLANTEAN_HOUSE_1.get(), new StructureFeatureConfiguration(6, 5, 1293091209), true);
        setupMapSpacingAndLand(ATLANTEAN_HOUSE_3.get(), new StructureFeatureConfiguration(7, 4, 965487684), true);
        setupMapSpacingAndLand(ATLANTEAN_TOWER.get(), new StructureFeatureConfiguration(9, 7, 763754364), true);
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(
            F structure,
            StructureFeatureConfiguration structureFeatureConfiguration,
            boolean transformSurroundingLand) {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
        if(transformSurroundingLand){
            StructureFeature.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<StructureFeature<?>>builder()
                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }
        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, structureFeatureConfiguration)
                        .build();

        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();
            if(structureMap instanceof ImmutableMap){
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureFeatureConfiguration);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureFeatureConfiguration);
            }
        });
    }
}
