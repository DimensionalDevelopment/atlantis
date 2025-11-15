package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.blocks.ancient_cuprum.WeatheringCuprumBulbBlock;
import com.mystic.atlantis.blocks.base.LinguisticGlyph;
import com.mystic.atlantis.blocks.shells.ColoredShellBlock;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

public class AtlantisBlockStateProvider extends AtlantisMainProvider.Proxied {
    public AtlantisBlockStateProvider(AtlantisMainProvider provider) {
        super(provider);
    }

    @Override
    public void registerStatesAndModels() {
        BlockType.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(this::registerBlockFamily);
        BlockInit.ANCIENT_CUPRUM.values().forEach(this::registerTrialGroup);
        BlockInit.COLORED_SHELL_BLOCKS.forEach((color, block) -> tintedCube(block.get(),
                color + "_shell_block", "block/colored_shell_faces",
                "block/colored_shell_faces", "block/colored_shell_front",
                "block/colored_shell_faces", "block/colored_shell_side",
                "block/colored_shell_side"));
        BlockInit.CRACKED_SHELL_BLOCKS.forEach((color, block) -> tintedCube(block.get(),
                "cracked_" + color + "_shell", "block/colored_shell_faces_cracked",
                "block/colored_shell_faces_cracked", "block/colored_shell_front_cracked",
                "block/colored_shell_faces_cracked", "block/colored_shell_side_cracked",
                "block/colored_shell_side_cracked"
        ));
        BlockInit.MOSSY_SHELL_BLOCKS.forEach((color, block) -> tintedOverlayBlock(block.get(),
                "mossy_" + color + "_shell_block", "block/colored_shell_faces",
                "block/colored_shell_faces", "block/colored_shell_front",
                "block/colored_shell_faces", "block/colored_shell_side",
                "block/colored_shell_side", "block/mossy_shell_faces_overlay",
                "block/mossy_shell_faces_overlay", "block/mossy_shell_front_overlay",
                "block/mossy_shell_faces_overlay", "block/mossy_shell_side_overlay",
                "block/mossy_shell_side_overlay"
        ));
        BlockInit.CRACKED_MOSSY_SHELL_BLOCKS.forEach((color, block) -> tintedOverlayBlock(block.get(),
                "cracked_mossy_" + color + "_shell", "block/colored_shell_faces_cracked",
                "block/colored_shell_faces_cracked", "block/colored_shell_front_cracked",
                "block/colored_shell_faces_cracked", "block/colored_shell_side_cracked",
                "block/colored_shell_side_cracked", "block/mossy_shell_faces_overlay",
                "block/mossy_shell_faces_overlay", "block/mossy_shell_front_overlay",
                "block/mossy_shell_faces_overlay", "block/mossy_shell_side_overlay",
                "block/mossy_shell_side_overlay"
        ));
        BlockInit.DYED_LINGUISTICS.forEach((glyph, registryObjectMap) -> registryObjectMap.forEach(
                ((color, block) -> {
                    if (glyph.equals(LinguisticGlyph.BLANK)) {
                        tintedCube(block.get(), color + "_linguistic_glyph", "block/blank/blank_top",
                                "block/blank/blank_bottom", "block/blank/blank_side", "block/blank/blank_side",
                                "block/blank/blank_side", "block/blank/blank_side"
                        );
                    } else if (glyph.equals(LinguisticGlyph.S) || glyph.equals(LinguisticGlyph.N)) {
                        tintedCube(block.get(), color + "_linguistic_glyph" + glyph, "block/blank/blank" + glyph + "_top",
                                "block/blank/blank_bottom", "block/blank/blank" + glyph + "_side",
                                "block/blank/blank" + glyph + "_side", "block/blank/blank" + glyph + "_side",
                                "block/blank/blank" + glyph + "_side"
                        );
                    } else {
                        tintedCube(block.get(), color + "_linguistic_glyph" + glyph, "block/blank/blank_top" + glyph,
                                "block/blank/blank_bottom", "block/blank/blank_side" + glyph,
                                "block/blank/blank_side" + glyph, "block/blank/blank_side" + glyph,
                                "block/blank/blank_side" + glyph
                        );
                    }
                })));

        BlockInit.NON_LINGUISTICS.forEach(((glyph, block) -> {
            if (glyph.equals(LinguisticGlyph.BLANK)) {
                tintedCube(block.get(), "linguistic_glyph" + glyph, "block/blank/blank_top",
                        "block/blank/blank_bottom", "block/blank/blank_side", "block/blank/blank_side",
                        "block/blank/blank_side", "block/blank/blank_side"
                );
            } else if (glyph.equals(LinguisticGlyph.S) || glyph.equals(LinguisticGlyph.N)) {
                tintedCube(block.get(), "linguistic_glyph" + glyph, "block/blank/blank" + glyph + "_top",
                        "block/blank/blank_bottom", "block/blank/blank" + glyph + "_side",
                        "block/blank/blank" + glyph + "_side", "block/blank/blank" + glyph + "_side",
                        "block/blank/blank" + glyph + "_side"
                );
            } else {
                tintedCube(block.get(), "linguistic_glyph" + glyph, "block/blank/blank_top" + glyph,
                        "block/blank/blank_bottom", "block/blank/blank_side" + glyph,
                        "block/blank/blank_side" + glyph, "block/blank/blank_side" + glyph,
                        "block/blank/blank_side" + glyph
                );
            }
        }));
        tintedCube(BlockInit.WATERFALL_BLOCK.get(), "waterfall_block", "block/waterfall_block_top", "block/waterfall_block_bottom",
        "block/waterfall_block_front", "block/waterfall_block_side", "block/waterfall_block_side", "block/waterfall_block_side");
        tintedCube(BlockInit.WAVE_BLOCK.get(), "wave_block", "block/wave_block_top", "block/wave_block_top", "block/wave_block_side",
                "block/wave_block_side", "block/wave_block_side", "block/wave_block_side");
        registerBlockItem(BlockInit.ALGAE_BLOCK.get());
        tintedCube(BlockInit.CRYSTAL_TRANSFERENCE_BLOCK.get(), "crystal_transference_block",
                "block/crystal_transference_block_top", "block/crystal_transference_block_bottom",
                "block/crystal_transference_block_side", "block/crystal_transference_block_side",
                "block/crystal_transference_block_side", "block/crystal_transference_block_side");
        tintedCube(BlockInit.COCONUT.get(), "coconut", "block/coconut_side", "block/coconut_side",
                "block/coconut", "block/coconut_side", "block/coconut_side", "block/coconut_side");
        tintedCube(BlockInit.CARVED_COCONUT.get(), "carved_coconut", "block/coconut_side", "block/coconut_side",
                "block/carved_coconut", "block/coconut_side", "block/coconut_side", "block/coconut_side");
        tintedCube(BlockInit.SATIRE_LANTERN.get(), "satire_lantern", "block/coconut_side", "block/coconut_side",
                "block/satire_lantern", "block/coconut_side", "block/coconut_side", "block/coconut_side");
        tintedCube(BlockInit.PALM_LOG.get(), "palm_log", "block/palm_log_top", "block/palm_log_top",
                "block/palm_log", "block/palm_log", "block/palm_log", "block/palm_log");
        tintedCube(BlockInit.STRIPPED_PALM_LOG.get(), "stripped_palm_log", "block/stripped_palm_log_top", "block/stripped_palm_log_top",
                "block/stripped_palm_log", "block/stripped_palm_log", "block/stripped_palm_log", "block/stripped_palm_log");
        tintedCube(BlockInit.NYMPH_LOG.get(), "nymph_log", "block/nymph_log_top", "block/nymph_log_top",
                "block/nymph_log", "block/nymph_log", "block/nymph_log", "block/nymph_log"
                );
        tintedCube(BlockInit.STRIPPED_NYMPH_LOG.get(), "stripped_nymph_log", "block/stripped_nymph_log_top", "block/stripped_nymph_log_top",
                "block/stripped_nymph_log", "block/stripped_nymph_log", "block/stripped_nymph_log", "block/stripped_nymph_log");
        registerSign(BlockInit.PALM_SIGN.get(), BlockInit.PALM_WALL_SIGN.get(), BlockInit.PALM_PLANKS.block().get());
        registerSign(BlockInit.NYMPH_SIGN.get(), BlockInit.NYMPH_WALL_SIGN.get(), BlockInit.NYMPH_PLANKS.block().get());
        tintedCube(BlockInit.OYSTER_SHELL_BLOCK.get(), "oyster_shell_block", "block/oyster_shell_faces", "block/oyster_shell_faces",
                "block/oyster_shell_side", "block/oyster_shell_side","block/oyster_shell_side","block/oyster_shell_side"
        );
        tintedCube(BlockInit.MOSSY_NAUTILUS_SHELL.get(), "mossy_nautilus_shell", "block/mossy_nautilus_shell_faces", "block/mossy_nautilus_shell_faces",
                "block/mossy_nautilus_shell_front", "block/mossy_nautilus_shell_faces", "block/mossy_nautilus_shell_side",
                "block/mossy_nautilus_shell_side"
        );
        tintedCube(BlockInit.MOSSY_OYSTER_SHELL.get(),"mossy_oyster_shell", "block/mossy_oyster_shell_faces", "block/mossy_oyster_shell_faces",
                "block/mossy_oyster_shell_side", "block/mossy_oyster_shell_side", "block/mossy_oyster_shell_side",
                "block/mossy_oyster_shell_side"
        );
        tintedCube(BlockInit.CRACKED_MOSSY_NAUTILUS_SHELL.get(), "cracked_mossy_nautilus_shell", "block/cracked_mossy_nautilus_shell_faces",
                "block/cracked_mossy_nautilus_shell_faces", "block/cracked_mossy_nautilus_shell_front", "block/cracked_mossy_nautilus_shell_faces",
                "block/cracked_mossy_nautilus_shell_side","block/cracked_mossy_nautilus_shell_side"
        );
        tintedCube(BlockInit.NAUTILUS_SHELL_BLOCK.get(), "nautilus_shell_block", "block/nautilus_shell_faces", "block/nautilus_shell_faces", "block/nautilus_shell_front",
                "block/nautilus_shell_faces", "block/nautilus_shell_faces", "block/nautilus_shell_side"
        );
        tintedCube(BlockInit.CRACKED_NAUTILUS_SHELL.get(), "cracked_nautilus_shell", "block/cracked_nautilus_shell_faces",
                "block/cracked_nautilus_shell_faces", "block/cracked_nautilus_shell_front",
                "block/cracked_nautilus_shell_faces", "block/cracked_nautilus_shell_faces", "block/cracked_nautilus_shell_side"
        );
        tintedCube(BlockInit.CRACKED_OYSTER_SHELL.get(), "cracked_oyster_shell", "block/cracked_oyster_shell_faces",
                "block/cracked_oyster_shell_faces", "block/cracked_oyster_shell_side",
                "block/cracked_oyster_shell_side", "block/cracked_oyster_shell_side", "block/cracked_oyster_shell_side"
        );
        tintedCube(BlockInit.CRACKED_MOSSY_OYSTER_SHELL.get(),"cracked_mossy_oyster_shell", "block/cracked_mossy_oyster_shell_faces",
                "block/cracked_mossy_oyster_shell_faces", "block/cracked_mossy_oyster_shell_side", "block/cracked_mossy_oyster_shell_side",
                "block/cracked_mossy_oyster_shell_side","block/cracked_mossy_oyster_shell_side"
        );
        tintedCube(BlockInit.SODIUM_BOMB.get(), "sodium_bomb", "block/sodium_bomb_top", "block/sodium_bomb_bottom",
                "block/sodium_bomb_side", "block/sodium_bomb_side", "block/sodium_bomb_side", "block/sodium_bomb_side"
        );
        registerBlockItem(BlockInit.SEASALT_CHUNK.get());
        registerBlockItem(BlockInit.SUNKEN_GRAVEL.get());
        registerBlockItem(BlockInit.CRACKED_GLOWSTONE.get());
        registerBlockItem(BlockInit.DEAD_GLOWSTONE.get());
        registerBlockItem(BlockInit.ALGAE_DETRITUS_STONE.get());
        registerBlockItem(BlockInit.DETRITUS_SANDSTONE.get());
        registerBlockItem(BlockInit.LUMINESCENT_PRISMARINE.get());
        registerBlockItem(BlockInit.BUBBLE_MAGMA.get());
        this.simpleBlock(BlockInit.PALM_LEAVES.get(), new ModelFile.ExistingModelFile(Atlantis.id("block/palm_leaves"), itemModels().existingFileHelper));
        registerBlockItem(BlockInit.NYMPH_LEAVES.get());
        registerBlockItem(BlockInit.AQUAMARINE_ORE.get());
        registerBlockItem(BlockInit.DEEPSLATE_AQUAMARINE_ORE.get());
        registerBlockItem(BlockInit.SEABED.get());
        registerBlockItem(BlockInit.OCEAN_LANTERN.get());
        registerBlockItem(BlockInit.SURGE_LANTERN.get());
        registerBlockItem(BlockInit.ATLANTEAN_CORE.get());
        registerBlockItem(BlockInit.BLOCK_OF_AQUAMARINE.get());
        registerBlockItem(BlockInit.CHISELED_GOLDEN_BLOCK.get());
        registerBlockItem(BlockInit.CHISELED_GOLDEN_AQUAMARINE.get());
        registerBlockItem(BlockInit.BLACK_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.BLUE_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.BROWN_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.CYAN_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.GRAY_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.GREEN_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.LIGHT_BLUE_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.LIGHT_GRAY_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.LIME_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.MAGENTA_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.ORANGE_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.PINK_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.PURPLE_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.RED_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.WHITE_PEARL_BLOCK.get());
        registerBlockItem(BlockInit.YELLOW_PEARL_BLOCK.get());
        simpleCross(BlockInit.SEABLOOM.get(), "seabloom", "block/seabloom");
        simpleCross(BlockInit.RED_SEABLOOM.get(), "red_seabloom", "block/red_seabloom");
        simpleCross(BlockInit.YELLOW_SEABLOOM.get(), "yellow_seabloom", "block/yellow_seabloom");
        simpleCross(BlockInit.PURPLE_SEASHROOM.get(), "purple_seashroom", "block/purple_seashroom");
        simpleCross(BlockInit.YELLOW_SEASHROOM.get(), "yellow_seashroom", "block/yellow_seashroom");
        registerBlockItem(BlockInit.AQUATIC_POWER_STONE.get());
        registerBlockItem(BlockInit.HARDENED_CALCITE_BLOCK.get());
        this.simpleBlock(BlockInit.PUSH_BUBBLE_COLUMN.get(), models().getExistingFile(mcLoc("block/air")));
        registerBlockItem(BlockInit.CHISELED_AQUAMARINE_BLOCK.get());
        registerBlockItem(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get());
        registerBlockItem(BlockInit.ANCIENT_CUPRUM_ORE.get());
        registerBlockItem(BlockInit.DEEPSLATE_ANCIENT_CUPRUM_ORE.get());
        simpleCross(BlockInit.NYMPH_SAPLING.get(), "nymph_sapling", "block/nymph_sapling");
        simpleCross(BlockInit.PALM_SAPLING.get(), "palm_sapling", "block/palm_sapling");
        tintedCube(BlockInit.COQUINA.get(), "coquina", "block/coquina_top", "block/coquina_top",
                "block/coquina", "block/coquina", "block/coquina", "block/coquina"
        );
        tintedCube(BlockInit.LINGUISTIC_TABLE.get(), "linguistic_table", "block/linguistic_table_top",
                "block/linguistic_table_bottom", "block/linguistic_table_side_1", "block/linguistic_table_side",
                "block/linguistic_table_side_0", "block/linguistic_table_side"
        );
        tintedCube(BlockInit.WRITING_TABLE.get(),"writing_table", "block/writing_table_top",
                "block/nymph_planks", "block/writing_table_side", "block/writing_table_side_2",
                "block/writing_table_side_0", "block/writing_table_side_1");
        this.simpleBlock(BlockInit.ORICHALCUM_BLOCK.get());
    }

    private void registerTrialGroup(TrailsGroup group) {
        registerBlockItem(group.block().get());
        registerBlockItem(group.chiseled().get());
        registerBlockItem(group.cut().get());
        registerSlab(group.cut_slab().get(), group.cut().get());
        registerStairs(group.cut_stairs().get(), group.cut().get());
        registerTrapDoor(group.trapdoor().get());
        registerDoor(group.door().get());
        registerBlockItem(group.grate().get());
        registerBulb(group.bulb().get());
        registerBlockItem(group.waxed_block().get(), group.block().get());
        registerBlockItem(group.waxed_chiseled().get(), group.chiseled().get());
        registerBlockItem(group.waxed_cut().get(), group.cut().get());
        registerSlab(group.waxed_cut_slab().get(), group.cut().get());
        registerStairs(group.waxed_cut_stairs().get(), group.cut().get());
        registerTrapDoor(group.waxed_trapdoor().get(), group.trapdoor().get());
        registerDoor(group.waxed_door().get(), group.door().get());
        registerBlockItem(group.waxed_grate().get(), group.grate().get());
        registerBulb(group.waxed_bulb().get(), group.bulb().get());
    }

    private void registerBulb(Block block) {
        registerBulb(block, block);
    }

    private void registerBulb(Block bulb, Block texture) {
        var unlit = this.blockTexture(texture).withSuffix("_unlit");
        var lit = this.blockTexture(texture).withSuffix("_lit");
        var unlit_powered = this.blockTexture(texture).withSuffix("_unlit_powered");
        var lit_powered = this.blockTexture(texture).withSuffix("_lit_powered");


        this.getVariantBuilder(bulb)
                .partialState().with(WeatheringCuprumBulbBlock.LIT, false).with(WeatheringCuprumBulbBlock.POWERED, false).addModels(new ConfiguredModel(new ModelFile.UncheckedModelFile(unlit)))
                .partialState().with(WeatheringCuprumBulbBlock.LIT, false).with(WeatheringCuprumBulbBlock.POWERED, true).addModels(new ConfiguredModel(new ModelFile.UncheckedModelFile(unlit_powered)))
                .partialState().with(WeatheringCuprumBulbBlock.LIT, true).with(WeatheringCuprumBulbBlock.POWERED, false).addModels(new ConfiguredModel(new ModelFile.UncheckedModelFile(lit)))
                .partialState().with(WeatheringCuprumBulbBlock.LIT, true).with(WeatheringCuprumBulbBlock.POWERED, true).addModels(new ConfiguredModel(new ModelFile.UncheckedModelFile(lit_powered)));
        simpleBlockItem(bulb, new ModelFile.UncheckedModelFile(unlit));
    }

    private void registerBlockFamily(BlockFamily family) {
        registerBlockItem(family.getBaseBlock());
        family.getVariants().keySet().forEach(variant -> processVariant(variant, family));
    }

    private void registerBlockItem(Block block) {
        registerBlockItem(block, block);
    }

    private void simpleCross(Block block, String name, String texture) {
        simpleBlock(block,
                models().cross(name, modLoc(texture)));
    }

    private void registerBlockItem(Block block, Block texture) {
        simpleBlockWithItem(block, cubeAll(texture));
    }

    private void processVariant(BlockFamily.Variant variant, BlockFamily family) {
        Block original = family.getBaseBlock();
        Block variantTarget = family.getVariants().get(variant);
        switch (variant) {
            case BUTTON -> registerButton(variantTarget, original);
            case CHISELED, CRACKED, CUT -> simpleBlockWithItem(variantTarget, cubeAll(variantTarget));
            case DOOR -> registerDoor((DoorBlock) variantTarget);
            case FENCE_GATE ->
                    registerGate((FenceGateBlock) variantTarget, (FenceBlock) family.get(BlockFamily.Variant.FENCE), original);
            case FENCE ->
                    registerFence((FenceBlock) variantTarget, original);
            case SIGN ->
                    registerSign((StandingSignBlock) variantTarget, (WallSignBlock) family.get(BlockFamily.Variant.WALL_SIGN), original);
            case SLAB -> registerSlab((SlabBlock) variantTarget, original);
            case STAIRS -> registerStairs((StairBlock) variantTarget, original);
            case PRESSURE_PLATE -> registerPressurePlate((PressurePlateBlock) variantTarget, original);
            case TRAPDOOR -> registerTrapDoor((TrapDoorBlock) variantTarget, original);
            case WALL -> registerWall((WallBlock) variantTarget, original);
        }
    }


    private void registerStairs(StairBlock stairs, Block texturedBlock) {
        ResourceLocation texture = ModelLocationUtils.getModelLocation(texturedBlock);
        stairsBlock(stairs, texture);
        simpleBlockItem(stairs, itemModels().stairs("block/" + key(stairs).getPath(), texture, texture, texture));
    }

    private void registerSlab(SlabBlock slab, Block texturedBlock) {
        ResourceLocation texture = ModelLocationUtils.getModelLocation(texturedBlock);
        slabBlock(slab, texture, texture);
        simpleBlockItem(slab, itemModels().slab("block/" + key(slab).getPath(), texture, texture, texture));
    }

    private void registerWall(WallBlock wall, Block texturedBlock) {
        ResourceLocation texture = ModelLocationUtils.getModelLocation(texturedBlock);
        wallBlock(wall, texture);
        simpleBlockItem(wall, itemModels().wallInventory("block/" + key(wall).getPath(), texture));
    }

    private void registerFence(FenceBlock fence, Block block) {
        ResourceLocation texture;
        if (block == null) texture = ModelLocationUtils.getModelLocation(fence);
        else texture = ModelLocationUtils.getModelLocation(block);

        fenceBlock(fence, texture);
        simpleBlockItem(fence, itemModels().fenceInventory("block/" + key(fence).getPath(), texture));
    }

    private void registerGate(FenceGateBlock gate, FenceBlock fence, Block block) {
        ResourceLocation texture;
        if (block == null) {
            ResourceLocation fenceId = key(fence);
            texture = ModelLocationUtils.getModelLocation(fence);
            simpleBlockItem(gate, itemModels().fenceGate("block/" + fenceId.getPath(), texture));
        } else {
            ResourceLocation gateId = key(gate);
            texture = ModelLocationUtils.getModelLocation(block);
            simpleBlockItem(gate, itemModels().fenceGate("block/" + gateId.getPath(), texture));
        }

        fenceGateBlock(gate, texture);
    }

    private void registerDoor(DoorBlock door) {
        registerDoor(door, door);
    }

    private void registerDoor(DoorBlock door, DoorBlock texturedBlock) {
        ResourceLocation blockId = key(texturedBlock);
        doorBlockWithRenderType(door, new ResourceLocation(blockId.getNamespace(), "block/" + blockId.getPath() + "_bottom"), new ResourceLocation(blockId.getNamespace(), "block/" + blockId.getPath() + "_top"), "cutout");
    }

    private void registerPressurePlate(PressurePlateBlock pressurePlate, Block texturedBlock) {
        ResourceLocation texture = ModelLocationUtils.getModelLocation(texturedBlock);
        pressurePlateBlock(pressurePlate, texture);
        itemModels().pressurePlate(key(pressurePlate).getPath(), texture);
    }

    private void registerButton(Block button, Block texturedBlock) {
        ResourceLocation buttonId = key(button);
        ResourceLocation textureBlockId = key(texturedBlock);

        ResourceLocation texture = new ResourceLocation(textureBlockId.getNamespace(), "block/" + textureBlockId.getPath());
        buttonBlock((ButtonBlock) button, texture);
        itemModels().buttonInventory(buttonId.getPath(), texture);
    }

    private void registerTrapDoor(TrapDoorBlock trapDoor, Block texturedBlock) {
        ResourceLocation texture = ModelLocationUtils.getModelLocation(texturedBlock);
        ResourceLocation trapDoorId = key(trapDoor);
        trapdoorBlockWithRenderType(trapDoor, texture, true, "cutout");
        itemModels().trapdoorBottom(trapDoorId.getPath(), texture);
    }

    private void registerTrapDoor(TrapDoorBlock trapDoor) {
        registerTrapDoor(trapDoor, trapDoor);
    }

    private void registerSign(StandingSignBlock sign, WallSignBlock wallsign, Block plank) {
        signBlock(sign, wallsign, blockTexture(plank));
    }

    private void tintedCube(Block block, String name,
                            String up, String down,
                            String north, String south,
                            String west, String east) {

        getVariantBuilder(block)
                .partialState()
                .modelForState()
                .modelFile(models().withExistingParent(name, modLoc("block/tinted_cube"))
                        .texture("up", modLoc(up))
                        .texture("down", modLoc(down))
                        .texture("north", modLoc(north))
                        .texture("south", modLoc(south))
                        .texture("west", modLoc(west))
                        .texture("east", modLoc(east)))
                .addModel();

        itemModels().withExistingParent(name, modLoc("block/" + name));
    }

    private void tintedOverlayBlock(Block block, String name,
                                    String baseUp, String baseDown,
                                    String baseNorth, String baseSouth,
                                    String baseWest, String baseEast,
                                    String overlayUp, String overlayDown,
                                    String overlayNorth, String overlaySouth,
                                    String overlayWest, String overlayEast
    ) {

        simpleBlockWithItem(block,
                models().withExistingParent(name, modLoc("block/tinted_overlay_cube"))
                        .texture("base_up", modLoc(baseUp))
                        .texture("base_down", modLoc(baseDown))
                        .texture("base_north", modLoc(baseNorth))
                        .texture("base_south", modLoc(baseSouth))
                        .texture("base_west", modLoc(baseWest))
                        .texture("base_east", modLoc(baseEast))
                        .texture("overlay_up", modLoc(overlayUp))
                        .texture("overlay_down", modLoc(overlayDown))
                        .texture("overlay_north", modLoc(overlayNorth))
                        .texture("overlay_south", modLoc(overlaySouth))
                        .texture("overlay_west", modLoc(overlayWest))
                        .texture("overlay_east", modLoc(overlayEast))
        );

        itemModels().withExistingParent(name, modLoc("block/" + name));
    }
}
