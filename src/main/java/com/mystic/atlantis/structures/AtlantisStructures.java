package com.mystic.atlantis.structures;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mystic.atlantis.util.Reference;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class AtlantisStructures {

    public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Reference.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> OYSTER_STRUCTURE = registerStructure("oyster_structure", () -> (new OysterStructure(NoFeatureConfig.field_236558_a_)));

    public static final RegistryObject<Structure<NoFeatureConfig>> ATLANTEAN_FOUNTAIN = registerStructure("atlantean_fountain", () -> (new AtlanteanFountain(NoFeatureConfig.field_236558_a_)));

    private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
        return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
    }

    public static void setupStructures() {
        setupMapSpacingAndLand(OYSTER_STRUCTURE.get(),new StructureSeparationSettings(10 , 5 , 482739847), true);

        setupMapSpacingAndLand(ATLANTEAN_FOUNTAIN.get(),new StructureSeparationSettings(8 , 4 , 584262839), true);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand){
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }

        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }
}
