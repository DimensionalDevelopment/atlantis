package com.mystic.atlantis.structures;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mystic.atlantis.util.Reference;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class AtlantisStructures {

    public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Reference.MODID);

    public static final RegistryObject<StructureFeature<DefaultFeatureConfig>> OYSTER_STRUCTURE = registerStructure("oyster_structure", () -> (new OysterStructure(DefaultFeatureConfig.CODEC)));

    public static final RegistryObject<StructureFeature<DefaultFeatureConfig>> ATLANTEAN_FOUNTAIN = registerStructure("atlantean_fountain", () -> (new AtlanteanFountain(DefaultFeatureConfig.CODEC)));

    private static <T extends StructureFeature<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
        return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
    }

    public static void setupStructures() {
        setupMapSpacingAndLand(OYSTER_STRUCTURE.get(),new StructureConfig(10 , 5 , 482739847), true);

        setupMapSpacingAndLand(ATLANTEAN_FOUNTAIN.get(),new StructureConfig(8 , 4 , 584262839), true);
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(
            F structure,
            StructureConfig structureSeparationSettings,
            boolean transformSurroundingLand)
    {
        StructureFeature.STRUCTURES.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand){
            StructureFeature.JIGSAW_STRUCTURES =
                    ImmutableList.<StructureFeature<?>>builder()
                            .addAll(StructureFeature.JIGSAW_STRUCTURES)
                            .add(structure)
                            .build();
        }

        StructuresConfig.DEFAULT_STRUCTURES =
                ImmutableMap.<StructureFeature<?>, StructureConfig>builder()
                        .putAll(StructuresConfig.DEFAULT_STRUCTURES)
                        .put(structure, structureSeparationSettings)
                        .build();
    }
}
