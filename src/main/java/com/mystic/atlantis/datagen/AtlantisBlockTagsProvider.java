package com.mystic.atlantis.datagen;

import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.mystic.atlantis.init.BlockInit.*;

public class AtlantisBlockTagsProvider extends BlockTagsProvider {
    public AtlantisBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, String modid) {
        super(output, lookup, modid, null);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        tag(BlockTags.ANIMALS_SPAWNABLE_ON).add(BlockInit.SEABED.get());

        for (TrailsGroup group : BlockInit.ANCIENT_CUPRUM.values()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                    group.block().get(), group.bulb().get(), group.grate().get(), group.cut().get(),
                    group.cut_slab().get(), group.cut_stairs().get(), group.chiseled().get(),
                    group.door().get(), group.trapdoor().get(),
                    group.waxed_block().get(), group.waxed_bulb().get(), group.waxed_grate().get(),
                    group.waxed_cut().get(), group.waxed_cut_slab().get(), group.waxed_cut_stairs().get(),
                    group.waxed_chiseled().get(), group.waxed_door().get(), group.waxed_trapdoor().get()
            );
            tag(BlockTags.NEEDS_IRON_TOOL).add(
                    group.block().get(), group.bulb().get(), group.grate().get(), group.cut().get(),
                    group.cut_slab().get(), group.cut_stairs().get(), group.chiseled().get(),
                    group.door().get(), group.trapdoor().get(),
                    group.waxed_block().get(), group.waxed_bulb().get(), group.waxed_grate().get(),
                    group.waxed_cut().get(), group.waxed_cut_slab().get(), group.waxed_cut_stairs().get(),
                    group.waxed_chiseled().get(), group.waxed_door().get(), group.waxed_trapdoor().get()
            );
        }

        for (DyeColor color : DyeColor.values()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                    MOSSY_SHELL_BLOCKS.get(color).get(),
                    CRACKED_MOSSY_SHELL_BLOCKS.get(color).get(),
                    CRACKED_SHELL_BLOCKS.get(color).get(),
                    COLORED_SHELL_BLOCKS.get(color).get()
            );
        }

        for (DyeColor color : DyeColor.values()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                    BlockInit.SEA_GLASS_PATTERNS.get(color).block().get(),
                    BlockInit.SEA_GLASS_PATTERNS.get(color).slab().get(),
                    BlockInit.SEA_GLASS_PATTERNS.get(color).stairs().get(),
                    BlockInit.SEA_GLASS_PATTERNS.get(color).pressurePlate().get(),
                    BlockInit.SEA_GLASS_PATTERNS.get(color).button().get(),
                    BlockInit.SEA_GLASS_PATTERNS.get(color).wall().get()
            );
        }

        for (DyeColor color : DyeColor.values()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                    BlockInit.SEA_GLASS_LIST.get(color).block().get(),
                    BlockInit.SEA_GLASS_LIST.get(color).slab().get(),
                    BlockInit.SEA_GLASS_LIST.get(color).stairs().get(),
                    BlockInit.SEA_GLASS_LIST.get(color).pressurePlate().get(),
                    BlockInit.SEA_GLASS_LIST.get(color).button().get(),
                    BlockInit.SEA_GLASS_LIST.get(color).wall().get()
            );
        }

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                RAW_ANCIENT_CUPRUM_BLOCK.get(), ANCIENT_CUPRUM_ORE.get(), DEEPSLATE_ANCIENT_CUPRUM_ORE.get(),
                ORICHALCUM_BLOCK.get(), WATERFALL_BLOCK.get(), WAVE_BLOCK.get(), CRYSTAL_TRANSFERENCE_BLOCK.get(),
                ATLANTEAN_PORTAL_FRAME.get(), TUBEN_POT.get(), BELEN_POT.get(), TOPER_POT.get(), SNOWN_POT.get(),
                HORPEN_POT.get(), CELEN_POT.get(), OBEMO_POT.get(), OYSTER_SHELL_BLOCK.get(), NAUTILUS_SHELL_BLOCK.get(),
                CRACKED_OYSTER_SHELL.get(), CRACKED_NAUTILUS_SHELL.get(), CRACKED_MOSSY_OYSTER_SHELL.get(), CRACKED_MOSSY_NAUTILUS_SHELL.get(),
                MOSSY_OYSTER_SHELL.get(), MOSSY_NAUTILUS_SHELL.get(), SEASALT_CHUNK.get(), CRACKED_GLOWSTONE.get(), DEAD_GLOWSTONE.get(),
                ALGAE_DETRITUS_STONE.get(), DETRITUS_SANDSTONE.get(), LUMINESCENT_PRISMARINE.get(), BUBBLE_MAGMA.get(),
                AQUAMARINE_ORE.get(), DEEPSLATE_AQUAMARINE_ORE.get(), OCEAN_LANTERN.get(), SURGE_LANTERN.get(),
                ATLANTEAN_CORE.get(), BLOCK_OF_AQUAMARINE.get(), CHISELED_GOLDEN_BLOCK.get(), CHISELED_GOLDEN_AQUAMARINE.get(),
                BLACK_PEARL_BLOCK.get(), BLUE_PEARL_BLOCK.get(), BROWN_PEARL_BLOCK.get(), CYAN_PEARL_BLOCK.get(),
                GRAY_PEARL_BLOCK.get(), GREEN_PEARL_BLOCK.get(), LIGHT_BLUE_PEARL_BLOCK.get(), LIGHT_GRAY_PEARL_BLOCK.get(),
                LIME_PEARL_BLOCK.get(), MAGENTA_PEARL_BLOCK.get(), ORANGE_PEARL_BLOCK.get(), PINK_PEARL_BLOCK.get(),
                PURPLE_PEARL_BLOCK.get(), RED_PEARL_BLOCK.get(), WHITE_PEARL_BLOCK.get(), YELLOW_PEARL_BLOCK.get()
        );

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(SUNKEN_GRAVEL.get(), SEABED.get());

        tag(BlockTags.NEEDS_IRON_TOOL).add(
                RAW_ANCIENT_CUPRUM_BLOCK.get(), ANCIENT_CUPRUM_ORE.get(), DEEPSLATE_ANCIENT_CUPRUM_ORE.get(),
                ORICHALCUM_BLOCK.get(), ATLANTEAN_PORTAL_FRAME.get(), AQUAMARINE_ORE.get(), DEEPSLATE_AQUAMARINE_ORE.get()
        );

        tag(BlockTags.MINEABLE_WITH_AXE).add(
                NYMPH_PLANKS.block().get(), NYMPH_PLANKS.slab().get(), NYMPH_PLANKS.fence().get(), NYMPH_PLANKS.fenceGate().get(),
                NYMPH_PLANKS.stairs().get(), NYMPH_PLANKS.door().get(), NYMPH_PLANKS.trapDoor().get(), NYMPH_PLANKS.button().get(),
                NYMPH_PLANKS.pressurePlate().get(),
                PALM_PLANKS.block().get(), PALM_PLANKS.slab().get(), PALM_PLANKS.fence().get(), PALM_PLANKS.fenceGate().get(),
                PALM_PLANKS.stairs().get(), PALM_PLANKS.door().get(), PALM_PLANKS.trapDoor().get(),
                ANCIENT_CHERRY.button().get(), ANCIENT_CHERRY.pressurePlate().get(), ANCIENT_CHERRY.block().get(), ANCIENT_CHERRY.slab().get(),
                ANCIENT_CHERRY.fence().get(), ANCIENT_CHERRY.fenceGate().get(), ANCIENT_CHERRY.stairs().get(), ANCIENT_CHERRY.door().get(),
                ANCIENT_CHERRY.trapDoor().get(), ANCIENT_CHERRY.button().get(), ANCIENT_CHERRY.pressurePlate().get(),
                ANCIENT_OAK.button().get(), ANCIENT_OAK.pressurePlate().get(), ANCIENT_OAK.block().get(), ANCIENT_OAK.slab().get(),
                ANCIENT_OAK.fence().get(), ANCIENT_OAK.fenceGate().get(), ANCIENT_OAK.stairs().get(), ANCIENT_OAK.door().get(),
                ANCIENT_OAK.trapDoor().get(), ANCIENT_OAK.button().get(), ANCIENT_OAK.pressurePlate().get(),
                ANCIENT_DARK_OAK.button().get(), ANCIENT_DARK_OAK.pressurePlate().get(), ANCIENT_DARK_OAK.block().get(), ANCIENT_DARK_OAK.slab().get(),
                ANCIENT_DARK_OAK.fence().get(), ANCIENT_DARK_OAK.fenceGate().get(), ANCIENT_DARK_OAK.stairs().get(), ANCIENT_DARK_OAK.door().get(),
                ANCIENT_DARK_OAK.trapDoor().get(), ANCIENT_DARK_OAK.button().get(), ANCIENT_DARK_OAK.pressurePlate().get(),
                ANCIENT_SPRUCE.button().get(), ANCIENT_SPRUCE.pressurePlate().get(), ANCIENT_SPRUCE.block().get(), ANCIENT_SPRUCE.slab().get(),
                ANCIENT_SPRUCE.fence().get(), ANCIENT_SPRUCE.fenceGate().get(), ANCIENT_SPRUCE.stairs().get(), ANCIENT_SPRUCE.door().get(),
                ANCIENT_SPRUCE.trapDoor().get(), ANCIENT_SPRUCE.button().get(), ANCIENT_SPRUCE.pressurePlate().get(),
                ANCIENT_ACACIA.button().get(), ANCIENT_ACACIA.pressurePlate().get(), ANCIENT_ACACIA.block().get(), ANCIENT_ACACIA.slab().get(),
                ANCIENT_ACACIA.fence().get(), ANCIENT_ACACIA.fenceGate().get(), ANCIENT_ACACIA.stairs().get(), ANCIENT_ACACIA.door().get(),
                ANCIENT_ACACIA.trapDoor().get(), ANCIENT_ACACIA.button().get(), ANCIENT_ACACIA.pressurePlate().get(),
                ANCIENT_BIRCH.button().get(), ANCIENT_BIRCH.pressurePlate().get(), ANCIENT_BIRCH.block().get(), ANCIENT_BIRCH.slab().get(),
                ANCIENT_BIRCH.fence().get(), ANCIENT_BIRCH.fenceGate().get(), ANCIENT_BIRCH.stairs().get(), ANCIENT_BIRCH.door().get(),
                ANCIENT_BIRCH.trapDoor().get(), ANCIENT_BIRCH.button().get(), ANCIENT_BIRCH.pressurePlate().get(),
                ANCIENT_JUNGLE.button().get(), ANCIENT_JUNGLE.pressurePlate().get(), ANCIENT_JUNGLE.block().get(), ANCIENT_JUNGLE.slab().get(),
                ANCIENT_JUNGLE.fence().get(), ANCIENT_JUNGLE.fenceGate().get(), ANCIENT_JUNGLE.stairs().get(), ANCIENT_JUNGLE.door().get(),
                ANCIENT_JUNGLE.trapDoor().get(), ANCIENT_JUNGLE.button().get(), ANCIENT_JUNGLE.pressurePlate().get(),
                ANCIENT_BAMBOO.button().get(), ANCIENT_BAMBOO.pressurePlate().get(), ANCIENT_BAMBOO.block().get(), ANCIENT_BAMBOO.slab().get(),
                ANCIENT_BAMBOO.fence().get(), ANCIENT_BAMBOO.fenceGate().get(), ANCIENT_BAMBOO.stairs().get(), ANCIENT_BAMBOO.door().get(),
                ANCIENT_BAMBOO.trapDoor().get(), ANCIENT_BAMBOO.button().get(), ANCIENT_BAMBOO.pressurePlate().get(),
                ANCIENT_MANGROVE.button().get(), ANCIENT_MANGROVE.pressurePlate().get(), ANCIENT_MANGROVE.block().get(), ANCIENT_MANGROVE.slab().get(),
                ANCIENT_MANGROVE.fence().get(), ANCIENT_MANGROVE.fenceGate().get(), ANCIENT_MANGROVE.stairs().get(), ANCIENT_MANGROVE.door().get(),
                ANCIENT_MANGROVE.trapDoor().get(), ANCIENT_MANGROVE.button().get(), ANCIENT_MANGROVE.pressurePlate().get(),
                ANCIENT_CRIMSON.button().get(), ANCIENT_CRIMSON.pressurePlate().get(), ANCIENT_CRIMSON.block().get(), ANCIENT_CRIMSON.slab().get(),
                ANCIENT_CRIMSON.fence().get(), ANCIENT_CRIMSON.fenceGate().get(), ANCIENT_CRIMSON.stairs().get(), ANCIENT_CRIMSON.door().get(),
                ANCIENT_CRIMSON.trapDoor().get(), ANCIENT_CRIMSON.button().get(), ANCIENT_CRIMSON.pressurePlate().get(),
                ANCIENT_WARPED.button().get(), ANCIENT_WARPED.pressurePlate().get(), ANCIENT_WARPED.block().get(), ANCIENT_WARPED.slab().get(),
                ANCIENT_WARPED.fence().get(), ANCIENT_WARPED.fenceGate().get(), ANCIENT_WARPED.stairs().get(), ANCIENT_WARPED.door().get(),
                ANCIENT_WARPED.trapDoor().get(), ANCIENT_WARPED.button().get(), ANCIENT_WARPED.pressurePlate().get(),
                PALM_SIGN.get(), PALM_WALL_SIGN.get(), STRIPPED_PALM_LOG.get(), STRIPPED_NYMPH_LOG.get(),
                COCONUT_SLICE.get(), SATIRE_LANTERN.get(), CARVED_COCONUT.get(), COCONUT.get(),
                PALM_LOG.get(), NYMPH_SIGN.get(), NYMPH_WALL_SIGN.get(), NYMPH_LOG.get(),
                LINGUISTIC_TABLE.get(), WRITING_TABLE.get()
        );

        tag(BlockTags.WOODEN_STAIRS).add(
                ANCIENT_BIRCH.stairs().get(), ANCIENT_ACACIA.stairs().get(), ANCIENT_JUNGLE.stairs().get(),
                ANCIENT_OAK.stairs().get(), ANCIENT_DARK_OAK.stairs().get(), ANCIENT_SPRUCE.stairs().get(),
                ANCIENT_MANGROVE.stairs().get(), ANCIENT_BAMBOO.stairs().get(), ANCIENT_CHERRY.stairs().get(),
                ANCIENT_CRIMSON.stairs().get(), ANCIENT_WARPED.stairs().get(), NYMPH_PLANKS.stairs().get(),
                PALM_PLANKS.stairs().get()
        );
        tag(BlockTags.WOODEN_SLABS).add(
                ANCIENT_BIRCH.slab().get(), ANCIENT_ACACIA.slab().get(), ANCIENT_JUNGLE.slab().get(),
                ANCIENT_OAK.slab().get(), ANCIENT_DARK_OAK.slab().get(), ANCIENT_SPRUCE.slab().get(),
                ANCIENT_MANGROVE.slab().get(), ANCIENT_BAMBOO.slab().get(), ANCIENT_CHERRY.slab().get(),
                ANCIENT_CRIMSON.slab().get(), ANCIENT_WARPED.slab().get(), NYMPH_PLANKS.slab().get(),
                PALM_PLANKS.slab().get()
        );
        tag(BlockTags.WOODEN_TRAPDOORS).add(
                ANCIENT_BIRCH.trapDoor().get(), ANCIENT_ACACIA.trapDoor().get(), ANCIENT_JUNGLE.trapDoor().get(),
                ANCIENT_OAK.trapDoor().get(), ANCIENT_DARK_OAK.trapDoor().get(), ANCIENT_SPRUCE.trapDoor().get(),
                ANCIENT_MANGROVE.trapDoor().get(), ANCIENT_BAMBOO.trapDoor().get(), ANCIENT_CHERRY.trapDoor().get(),
                ANCIENT_CRIMSON.trapDoor().get(), ANCIENT_WARPED.trapDoor().get(), NYMPH_PLANKS.trapDoor().get(),
                PALM_PLANKS.trapDoor().get()
        );
        tag(BlockTags.SAPLINGS).add(NYMPH_SAPLING.get(), PALM_SAPLING.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                ANCIENT_BIRCH.pressurePlate().get(), ANCIENT_ACACIA.pressurePlate().get(), ANCIENT_JUNGLE.pressurePlate().get(),
                ANCIENT_OAK.pressurePlate().get(), ANCIENT_DARK_OAK.pressurePlate().get(), ANCIENT_SPRUCE.pressurePlate().get(),
                ANCIENT_MANGROVE.pressurePlate().get(), ANCIENT_BAMBOO.pressurePlate().get(), ANCIENT_CHERRY.pressurePlate().get(),
                ANCIENT_CRIMSON.pressurePlate().get(), ANCIENT_WARPED.pressurePlate().get(), NYMPH_PLANKS.pressurePlate().get(),
                PALM_PLANKS.pressurePlate().get()
        );
        tag(BlockTags.WOODEN_DOORS).add(
                ANCIENT_BIRCH.door().get(), ANCIENT_ACACIA.door().get(), ANCIENT_JUNGLE.door().get(),
                ANCIENT_OAK.door().get(), ANCIENT_DARK_OAK.door().get(), ANCIENT_SPRUCE.door().get(),
                ANCIENT_MANGROVE.door().get(), ANCIENT_BAMBOO.door().get(), ANCIENT_CHERRY.door().get(),
                ANCIENT_CRIMSON.door().get(), ANCIENT_WARPED.door().get(), NYMPH_PLANKS.door().get(),
                PALM_PLANKS.door().get()
        );
        tag(BlockTags.MINEABLE_WITH_HOE).add(
                NYMPH_LEAVES.get(), PALM_LEAVES.get(), SEABLOOM.get(), RED_SEABLOOM.get(),
                PURPLE_SEASHROOM.get(), YELLOW_SEASHROOM.get(), YELLOW_SEABLOOM.get(), ALGAE.get(),
                FIRE_MELON_FRUIT_SPIKED.get(), FIRE_MELON_FRUIT.get(), FIRE_MELON_STEM.get(), FIRE_MELON_TOP.get(),
                NYMPH_SAPLING.get(), SEASHROOM.get(), TUBER_UP.get(), BLUE_LILY.get(), BURNT_DEEP.get(), ANEMONE.get(),
                ALGAE_BLOCK.get()
        );
        tag(BlockTags.BUTTONS).add(
                ANCIENT_BIRCH.button().get(), ANCIENT_ACACIA.button().get(), ANCIENT_JUNGLE.button().get(),
                ANCIENT_OAK.button().get(), ANCIENT_DARK_OAK.button().get(), ANCIENT_SPRUCE.button().get(),
                ANCIENT_MANGROVE.button().get(), ANCIENT_BAMBOO.button().get(), ANCIENT_CHERRY.button().get(),
                ANCIENT_CRIMSON.button().get(), ANCIENT_WARPED.button().get(), NYMPH_PLANKS.button().get(),
                PALM_PLANKS.button().get()
        );

        tag(BlockTags.CLIMBABLE).add(BlockInit.ALGAE.get());
        tag(BlockTags.PLANKS).add(
                ANCIENT_BIRCH.block().get(), ANCIENT_ACACIA.block().get(), ANCIENT_JUNGLE.block().get(),
                ANCIENT_OAK.block().get(), ANCIENT_DARK_OAK.block().get(), ANCIENT_SPRUCE.block().get(),
                ANCIENT_MANGROVE.block().get(), ANCIENT_BAMBOO.block().get(), ANCIENT_CHERRY.block().get(),
                ANCIENT_CRIMSON.block().get(), ANCIENT_WARPED.block().get(), NYMPH_PLANKS.block().get(),
                PALM_PLANKS.block().get()
        );
        tag(BlockTags.LEAVES).add(PALM_LEAVES.get(), NYMPH_LEAVES.get());
        tag(BlockTags.WALL_SIGNS).add(NYMPH_WALL_SIGN.get(), PALM_WALL_SIGN.get());
        tag(BlockTags.LOGS).add(NYMPH_LOG.get(), PALM_LOG.get(), STRIPPED_PALM_LOG.get(), STRIPPED_NYMPH_LOG.get());
        tag(BlockTags.SIGNS).add(NYMPH_SIGN.get(), PALM_SIGN.get());

        for (BlockType blockType : SEA_GLASS_LIST.values()) tag(BlockTags.WALLS).add(blockType.wall().get());
        for (BlockType blockType : SEA_GLASS_PATTERNS.values()) tag(BlockTags.WALLS).add(blockType.wall().get());

        tag(BlockTags.WOODEN_FENCES).add(
                ANCIENT_BIRCH.fence().get(), ANCIENT_ACACIA.fence().get(), ANCIENT_JUNGLE.fence().get(),
                ANCIENT_OAK.fence().get(), ANCIENT_DARK_OAK.fence().get(), ANCIENT_SPRUCE.fence().get(),
                ANCIENT_MANGROVE.fence().get(), ANCIENT_BAMBOO.fence().get(), ANCIENT_CHERRY.fence().get(),
                ANCIENT_CRIMSON.fence().get(), ANCIENT_WARPED.fence().get(), NYMPH_PLANKS.fence().get(),
                PALM_PLANKS.fence().get()
        );
    }
}
