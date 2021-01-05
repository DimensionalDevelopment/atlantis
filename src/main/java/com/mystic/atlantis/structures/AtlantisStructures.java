package com.mystic.atlantis.structures;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mystic.atlantis.Main;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent.Register;

    public class AtlantisStructures {

        public static Structure<NoFeatureConfig> OYSTER_STRUCTURE = new OysterStructure(NoFeatureConfig.field_236558_a_);
        public static IStructurePieceType OYSTER_PIECE = OysterPieces.Piece::new;


        public static void registerStructures(Register<Structure<?>> event) {

            Main.register(event.getRegistry(), OYSTER_STRUCTURE, "oyster_structure");

            registerStructure(
                    OYSTER_STRUCTURE, /* The instance of the structure */
                    new StructureSeparationSettings(10 /* maximum distance apart in chunks between spawn attempts */,
                            5 /* minimum distance apart in chunks between spawn attempts */,
                            35629823 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                    true);


            AtlantisStructures.registerAllPieces();
        }

        public static <F extends Structure<?>> void registerStructure(
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

        public static void registerAllPieces() {
            registerStructurePiece(OYSTER_PIECE, new ResourceLocation(Reference.MODID, "structures/oyster_structure"));
        }

        static void registerStructurePiece(IStructurePieceType structurePiece, ResourceLocation rl) {
            Registry.register(Registry.STRUCTURE_PIECE, rl, structurePiece);
        }
    }
