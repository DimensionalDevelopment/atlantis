package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.blocks.ancient_cuprum.WeatheringCuprumBulbBlock;
import com.mystic.atlantis.blocks.shells.ColoredShellBlock;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

public class AtlantisBlockStateProvider extends AtlantisMainProvider.Proxied {
    public AtlantisBlockStateProvider(AtlantisMainProvider provider) {
        super(provider);
    }

    @Override
    public void registerStatesAndModels() {
        BlockType.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(this::registerBlockFamily);
        BlockInit.ANCIENT_CUPRUM.values().forEach(this::registerTrialGroup);
        BlockInit.COLORED_SHELL_BLOCKS.forEach((color, block) -> this.simpleBlock(block.get()));
        BlockInit.CRACKED_SHELL_BLOCKS.forEach((color, block) -> this.simpleBlock(block.get()));
        BlockInit.MOSSY_SHELL_BLOCKS.forEach((color, block) -> this.simpleBlock(block.get()));
        BlockInit.CRACKED_MOSSY_SHELL_BLOCKS.forEach((color, block) -> this.simpleBlock(block.get()));
        BlockInit.DYED_LINGUISTICS.forEach((glyph, registryObjectMap) -> registryObjectMap.forEach(
                ((color, block) -> this.simpleBlock(block.get()))
        ));
        BlockInit.NON_LINGUISTICS.forEach(((glyph, block) -> this.simpleBlock(block.get())));
        this.simpleBlock(BlockInit.WATERFALL_BLOCK.get());
        this.simpleBlock(BlockInit.WAVE_BLOCK.get());
        this.simpleBlock(BlockInit.ALGAE_BLOCK.get());
        this.simpleBlock(BlockInit.CRYSTAL_TRANSFERENCE_BLOCK.get());
        this.simpleBlock(BlockInit.COCONUT.get());
        this.simpleBlock(BlockInit.CARVED_COCONUT.get());
        this.simpleBlock(BlockInit.SATIRE_LANTERN.get());
        this.simpleBlock(BlockInit.PALM_LOG.get());
        this.simpleBlock(BlockInit.STRIPPED_PALM_LOG.get());;
        registerSign(BlockInit.PALM_SIGN.get(), BlockInit.PALM_WALL_SIGN.get(), BlockInit.PALM_PLANKS.block().get());
        registerSign(BlockInit.NYMPH_SIGN.get(), BlockInit.NYMPH_WALL_SIGN.get(), BlockInit.NYMPH_PLANKS.block().get());
        this.simpleBlock(BlockInit.OYSTER_SHELL_BLOCK.get());
        this.simpleBlock(BlockInit.MOSSY_NAUTILUS_SHELL.get());
        this.simpleBlock(BlockInit.MOSSY_OYSTER_SHELL.get());
        this.simpleBlock(BlockInit.CRACKED_MOSSY_NAUTILUS_SHELL.get());
        this.simpleBlock(BlockInit.NAUTILUS_SHELL_BLOCK.get());
        this.simpleBlock(BlockInit.CRACKED_NAUTILUS_SHELL.get());
        this.simpleBlock(BlockInit.CRACKED_OYSTER_SHELL.get());
        this.simpleBlock(BlockInit.CRACKED_MOSSY_OYSTER_SHELL.get());
        this.simpleBlock(BlockInit.SODIUM_BOMB.get());
        this.simpleBlock(BlockInit.SEASALT_CHUNK.get());
        this.simpleBlock(BlockInit.SUNKEN_GRAVEL.get());
        this.simpleBlock(BlockInit.CRACKED_GLOWSTONE.get());
        this.simpleBlock(BlockInit.DEAD_GLOWSTONE.get());
        this.simpleBlock(BlockInit.ALGAE_DETRITUS_STONE.get());
        this.simpleBlock(BlockInit.DETRITUS_SANDSTONE.get());
        this.simpleBlock(BlockInit.LUMINESCENT_PRISMARINE.get());
        this.simpleBlock(BlockInit.BUBBLE_MAGMA.get());
        this.simpleBlock(BlockInit.PALM_LEAVES.get());
        this.simpleBlock(BlockInit.NYMPH_LEAVES.get());
        this.simpleBlock(BlockInit.AQUAMARINE_ORE.get());
        this.simpleBlock(BlockInit.DEEPSLATE_AQUAMARINE_ORE.get());
        this.simpleBlock(BlockInit.SEABED.get());
        this.simpleBlock(BlockInit.OCEAN_LANTERN.get());
        this.simpleBlock(BlockInit.SURGE_LANTERN.get());
        this.simpleBlock(BlockInit.ATLANTEAN_CORE.get());
        this.simpleBlock(BlockInit.BLOCK_OF_AQUAMARINE.get());
        this.simpleBlock(BlockInit.CHISELED_GOLDEN_BLOCK.get());
        this.simpleBlock(BlockInit.CHISELED_GOLDEN_AQUAMARINE.get());
        this.simpleBlock(BlockInit.BLACK_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.BLUE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.BROWN_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.CYAN_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.GRAY_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.GREEN_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.LIGHT_BLUE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.LIGHT_GRAY_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.LIME_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.MAGENTA_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.ORANGE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.PINK_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.PURPLE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.RED_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.WHITE_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.YELLOW_PEARL_BLOCK.get());
        this.simpleBlock(BlockInit.SEABLOOM.get(), models().cross("seabloom", modLoc("seabloom")));
        this.simpleBlock(BlockInit.RED_SEABLOOM.get(), models().cross("red_seabloom", modLoc("red_seabloom")));
        this.simpleBlock(BlockInit.YELLOW_SEABLOOM.get(), models().cross("yellow_seabloom", modLoc("yellow_seabloom")));
        this.simpleBlock(BlockInit.PURPLE_SEASHROOM.get(), models().cross("purple_seabloom", modLoc("purple_seashroom")));
        this.simpleBlock(BlockInit.YELLOW_SEASHROOM.get(), models().cross("yellow_seashroom", modLoc("yellow_seashroom")));
        this.simpleBlock(BlockInit.AQUATIC_POWER_STONE.get());
        this.simpleBlock(BlockInit.HARDENED_CALCITE_BLOCK.get());
        this.simpleBlock(BlockInit.PUSH_BUBBLE_COLUMN.get());
        this.simpleBlock(BlockInit.CHISELED_AQUAMARINE_BLOCK.get());
        this.simpleBlock(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get());
        this.simpleBlock(BlockInit.ANCIENT_CUPRUM_ORE.get());
        this.simpleBlock(BlockInit.DEEPSLATE_ANCIENT_CUPRUM_ORE.get());
        this.simpleBlock(BlockInit.NYMPH_SAPLING.get(), models().cross("nymph_sapling", modLoc("nymph_sapling")));
        this.simpleBlock(BlockInit.PALM_SAPLING.get(), models().cross("palm_sapling", modLoc("palm_sapling")));
        this.simpleBlock(BlockInit.COQUINA.get());
        this.simpleBlock(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get());
        this.horizontalBlock(BlockInit.WRITING_BLOCK.get(), new ModelFile.ExistingModelFile(Atlantis.id("block/writing_block"), itemModels().existingFileHelper));
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
            case FENCE_GATE -> registerGate((FenceGateBlock) variantTarget, (FenceBlock) family.get(BlockFamily.Variant.FENCE), original);
            case FENCE -> {
                //for some reason this comes out as a fence gate idk why!
                if(variantTarget instanceof FenceBlock fenceBlock)
                    registerFence(fenceBlock, original);
                }
            case SIGN -> registerSign((StandingSignBlock) variantTarget, (WallSignBlock) family.get(BlockFamily.Variant.WALL_SIGN), original);
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
        if (block == null)
        {
            ResourceLocation fenceId = key(fence);
            texture = ModelLocationUtils.getModelLocation(fence);
            simpleBlockItem(gate, itemModels().fenceGate("block/" + fenceId.getPath(), texture));
        }
        else
        {
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

    private void registerTrapDoor(TrapDoorBlock trapDoor, Block texturedBlock){
        ResourceLocation texture = ModelLocationUtils.getModelLocation(texturedBlock);
        ResourceLocation trapDoorId = key(trapDoor);
        trapdoorBlockWithRenderType(trapDoor, texture,true, "cutout");
        itemModels().trapdoorBottom(trapDoorId.getPath(), texture);
    }

    private void registerTrapDoor(TrapDoorBlock trapDoor) {
         registerTrapDoor(trapDoor, trapDoor);
    }

    private void registerSign(StandingSignBlock sign, WallSignBlock wallsign, Block plank){signBlock(sign, wallsign, blockTexture(plank));}

}
