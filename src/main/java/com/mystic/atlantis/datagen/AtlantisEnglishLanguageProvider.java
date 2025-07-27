package com.mystic.atlantis.datagen;

import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.init.AtlantisEntityInit;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;

//TODO Convert lang to pure data gen in the future. This will complain about duplicates if run without removing the existing en_us.json first
public class AtlantisEnglishLanguageProvider extends LanguageProvider {
    public AtlantisEnglishLanguageProvider(PackOutput generator) {
        super(generator, "atlantis", "en_us");
    }


    /* TODO: NEXT TIME YOU TOUCH THIS READ THIS WATERPICKER!!!
        make all of the translations equal to the ones in the current lang files of en_us.json
        add all of the translations for the items, blocks, etc
        make lang files for other languages that we include at the current moment!
        after this is done then and only then remove lang files from the normal resources folder
    */

    @Override
    protected void addTranslations() {
        this.addBlock(BlockInit.ORICHALCUM_BLOCK, "Block of Orichalcum");
        this.addBlock(BlockInit.WATERFALL_BLOCK, "Waterfall Block");
        this.addBlock(BlockInit.WAVE_BLOCK, "Wave Block");
        this.addBlock(BlockInit.CRYSTAL_TRANSFERENCE_BLOCK, "Crystal Transference Block");
        this.addBlock(BlockInit.ATLANTEAN_PORTAL_FRAME, "Atlantean Portal Frame");
        this.addBlock(BlockInit.ATLANTEAN_PORTAL, "Atlantean Portal");
        this.addBlock(BlockInit.JETSTREAM_WATER, "Jetstream Water");
        this.addBlock(BlockInit.SALTY_SEAWATER, "Salty Seawater");
        this.addBlock(BlockInit.TUBEN_POT, "Tuben Pot");
        this.addBlock(BlockInit.BELEN_POT , "Belen Pot");
        this.addBlock(BlockInit.TOPER_POT , "Toper Pot");
        this.addBlock(BlockInit.SNOWN_POT , "Snown Pot");
        this.addBlock(BlockInit.HORPEN_POT, "Horpen Pot");
        this.addBlock(BlockInit.CELEN_POT , "Celen Pot");
        this.addBlock(BlockInit.OBEMO_POT , "Obemo Pot");
        this.addBlock(BlockInit.COCONUT_SLICE, "Coconut Slice");
        this.addBlock(BlockInit.COCONUT, "Coconut");
        this.addBlock(BlockInit.CARVED_COCONUT, "Carved Coconut");
        this.addBlock(BlockInit.SATIRE_LANTERN, "Satire Lantern");
        this.addBlock(BlockInit.PALM_LOG, "Palm Log");
        this.addBlock(BlockInit.STRIPPED_PALM_LOG, "Stripped Palm Log");
        this.addBlocksFromType(BlockInit.PALM_PLANKS, "Palm", true);
        this.addBlock(BlockInit.STRIPPED_NYMPH_LOG, "Stripped Nymph Log");
        this.addBlocksFromType(BlockInit.NYMPH_PLANKS, "Nymph", true);

        this.addBlock(BlockInit.SEASHROOM, "Seashroom");
        this.addBlock(BlockInit.TUBER_UP, "Tuber Up");
        this.addBlock(BlockInit.BLUE_LILY, "Blue Lily");
        this.addBlock(BlockInit.BURNT_DEEP, "Burnt Deep");
        this.addBlock(BlockInit.ANEMONE, "Anemone Block");
        this.addBlock(BlockInit.OYSTER_SHELL_BLOCK, "Oyster Shell Block");
        this.addBlock(BlockInit.NAUTILUS_SHELL_BLOCK, "Nautilus Shell Block");
        this.addBlock(BlockInit.CRACKED_OYSTER_SHELL, "Cracked Oyster Shell");
        this.addBlock(BlockInit.CRACKED_NAUTILUS_SHELL, "Cracked Nautilus Shell");
        this.addBlock(BlockInit.CRACKED_MOSSY_OYSTER_SHELL, "Cracked Mossy Oyster Shell");
        this.addBlock(BlockInit.CRACKED_MOSSY_NAUTILUS_SHELL, "Cracked Mossy Nautilus Shell");
        this.addBlock(BlockInit.MOSSY_OYSTER_SHELL, "Mossy Oyster Shell");
        this.addBlock(BlockInit.MOSSY_NAUTILUS_SHELL, "Mossy Nautilus Shell");
        this.addBlock(BlockInit.SODIUM_BOMB, "Sodium Bomb");
        this.addBlock(BlockInit.SEASALT_CHUNK, "Seasalt Chunk");
        this.addBlock(BlockInit.SUNKEN_GRAVEL, "Sunken Gravel");
        this.addBlock(BlockInit.CRACKED_GLOWSTONE, "Cracked Glowstone");
        this.addBlock(BlockInit.DEAD_GLOWSTONE, "Dead Glowstone");
        this.addBlock(BlockInit.ALGAE_DETRITUS_STONE, "Algae Detritus Stone");
        this.addBlock(BlockInit.DETRITUS_SANDSTONE, "Detritus Sandstone");
        this.addBlock(BlockInit.LUMINESCENT_PRISMARINE, "Luminescent Prismarine");
        this.addBlock(BlockInit.BUBBLE_MAGMA, "Bubble Magma");
        this.addBlock(BlockInit.PALM_LEAVES, "Palm Leaves");
        this.addBlock(BlockInit.NYMPH_LEAVES, "Nymph Leaves");
        this.addBlock(BlockInit.NYMPH_LOG, "Nymph Log");
        this.addBlock(BlockInit.AQUAMARINE_ORE, "Aquamarine Ore");
        this.addBlock(BlockInit.DEEPSLATE_AQUAMARINE_ORE, "Deepslate Aquamarine Ore");
        this.addBlock(BlockInit.SEABED, "Seabed");
        this.addBlock(BlockInit.OCEAN_LANTERN, "Ocean Lantern");
        this.addBlock(BlockInit.SURGE_LANTERN, "Surge Lantern");
        this.addBlock(BlockInit.ATLANTEAN_CORE, "Atlantean Core");
        this.addBlock(BlockInit.BLOCK_OF_AQUAMARINE, "Block Of Aquamarine");
        this.addBlock(BlockInit.CHISELED_GOLDEN_BLOCK, "Chiseled Golden Block");
        this.addBlock(BlockInit.CHISELED_GOLDEN_AQUAMARINE, "Chiseled Golden Aquamarine");
        this.addBlock(BlockInit.BLACK_PEARL_BLOCK, "Black Pearl Block");
        this.addBlock(BlockInit.BLUE_PEARL_BLOCK, "Blue Pearl Block");
        this.addBlock(BlockInit.BROWN_PEARL_BLOCK, "Brown Pearl Block");
        this.addBlock(BlockInit.CYAN_PEARL_BLOCK, "Cyan Pearl Block");
        this.addBlock(BlockInit.GRAY_PEARL_BLOCK, "Gray Pearl Block");
        this.addBlock(BlockInit.GREEN_PEARL_BLOCK, "Green Pearl Block");
        this.addBlock(BlockInit.LIGHT_BLUE_PEARL_BLOCK, "Light Blue Pearl Block");
        this.addBlock(BlockInit.LIGHT_GRAY_PEARL_BLOCK, "Light Gray Pearl Block");
        this.addBlock(BlockInit.LIME_PEARL_BLOCK, "Lime Pearl Block");
        this.addBlock(BlockInit.MAGENTA_PEARL_BLOCK, "Magenta Pearl Block");
        this.addBlock(BlockInit.ORANGE_PEARL_BLOCK, "Orange Pearl Block");
        this.addBlock(BlockInit.PINK_PEARL_BLOCK, "Pink Pearl Block");
        this.addBlock(BlockInit.PURPLE_PEARL_BLOCK, "Purple Pearl Block");
        this.addBlock(BlockInit.RED_PEARL_BLOCK, "Red Pearl Block");
        this.addBlock(BlockInit.WHITE_PEARL_BLOCK, "White Pearl Block");
        this.addBlock(BlockInit.YELLOW_PEARL_BLOCK, "Yellow Pearl Block");
        this.addBlock(BlockInit.SEABLOOM, "Seabloom");
        this.addBlock(BlockInit.RED_SEABLOOM, "Red Seabloom");
        this.addBlock(BlockInit.PURPLE_SEASHROOM, "Purple Seashroom");
        this.addBlock(BlockInit.YELLOW_SEASHROOM, "Yellow Seashroom");
        this.addBlock(BlockInit.YELLOW_SEABLOOM, "Yellow Seabloom");
        this.addBlock(BlockInit.ALGAE, "Algae");
        this.addBlock(BlockInit.AQUATIC_POWER_STONE, "Aquatic Power Stone");
        this.addBlock(BlockInit.AQUATIC_POWER_LAMP, "Aquatic Power Lamp");
        this.addBlock(BlockInit.AQUATIC_POWER_TORCH, "Aquatic Power Torch");
        this.addBlock(BlockInit.WALL_AQUATIC_POWER_TORCH, "Aquatic Power Torch");
        this.addBlock(BlockInit.AQUATIC_POWER_DUST_WIRE, "Aquatic Power Dust Wire");
        this.addBlock(BlockInit.AQUATIC_POWER_REPEATER, "Aquatic Power Repeater");
        this.addBlock(BlockInit.AQUATIC_POWER_TRIPWIRE_HOOK, "Aquatic Power Tripwire Hook");
        this.addBlock(BlockInit.AQUATIC_POWER_TRIPWIRE, "Aquatic Power Tripwire");
        this.addBlock(BlockInit.AQUATIC_POWER_LEVER, "Aquatic Power Lever");
        this.addBlock(BlockInit.AQUATIC_POWER_COMPARATOR, "Aquatic Power Comparator");
        this.addBlock(BlockInit.HARDENED_CALCITE_BLOCK, "Hardened Calcite Block");
        this.addBlock(BlockInit.PUSH_BUBBLE_COLUMN, "Push Bubble Column");
        this.addBlock(BlockInit.ALGAE_BLOCK, "Algae Block");
        this.addBlock(BlockInit.CHISELED_AQUAMARINE_BLOCK, "Chiseled Aquamarine Block");
        this.addBlock(BlockInit.LINGUISTIC_TABLE, "Linguistic Table");
        this.addBlock(BlockInit.WRITING_TABLE, "Writing Table");
        this.addBlock(BlockInit.NYMPH_SAPLING, "Nymph Sapling");
        this.addBlock(BlockInit.PALM_SAPLING, "Palm Sapling");
        this.addBlock(BlockInit.FIRE_MELON_FRUIT_SPIKED, "Fire Melon Fruit Spiked");
        this.addBlock(BlockInit.FIRE_MELON_FRUIT, "Fire Melon Fruit");
        this.addBlock(BlockInit.FIRE_MELON_STEM, "Fire Melon Stem");
        this.addBlock(BlockInit.FIRE_MELON_TOP, "Fire Melon Top");

        this.addBlocksFromType(BlockInit.ANCIENT_CHERRY, "Ancient Cherry", true);
        this.addBlocksFromType(BlockInit.SEA_GLASS, "Sea Glass", false);
        this.addBlocksFromType(BlockInit.BLUE_SEA_GLASS, "Blue Sea Glass", false);
        this.addBlocksFromType(BlockInit.RED_SEA_GLASS, "Red Sea Glass", false);
        this.addBlocksFromType(BlockInit.ORANGE_SEA_GLASS, "Orange Sea Glass", false);
        this.addBlocksFromType(BlockInit.YELLOW_SEA_GLASS, "Yellow Sea Glass", false);
        this.addBlocksFromType(BlockInit.GREEN_SEA_GLASS, "Green Sea Glass", false);
        this.addBlocksFromType(BlockInit.LIME_SEA_GLASS, "Lime Sea Glass", false);
        this.addBlocksFromType(BlockInit.LIGHT_BLUE_SEA_GLASS, "Light Blue Sea Glass", false);
        this.addBlocksFromType(BlockInit.LIGHT_GRAY_SEA_GLASS, "Light Gray Sea Glass", false);
        this.addBlocksFromType(BlockInit.MAGENTA_SEA_GLASS, "Magenta Sea Glass", false);
        this.addBlocksFromType(BlockInit.PURPLE_SEA_GLASS, "Purple Sea Glass", false);
        this.addBlocksFromType(BlockInit.PINK_SEA_GLASS, "Pink Sea Glass", false);
        this.addBlocksFromType(BlockInit.BLACK_SEA_GLASS, "Black Sea Glass", false);
        this.addBlocksFromType(BlockInit.GRAY_SEA_GLASS, "Gray Sea Glass", false);
        this.addBlocksFromType(BlockInit.WHITE_SEA_GLASS, "White Sea Glass", false);
        this.addBlocksFromType(BlockInit.BROWN_SEA_GLASS, "Brown Sea Glass", false);
        this.addBlocksFromType(BlockInit.CYAN_SEA_GLASS, "Cyan Sea Glass", false);
        this.addBlocksFromType(BlockInit.BLUE_PATTERNED_SEA_GLASS, "Blue Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.RED_PATTERNED_SEA_GLASS, "Red Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.ORANGE_PATTERNED_SEA_GLASS, "Orange Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.YELLOW_PATTERNED_SEA_GLASS, "Yellow Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.GREEN_PATTERNED_SEA_GLASS, "Green Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.LIME_PATTERNED_SEA_GLASS, "Lime Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.LIGHT_BLUE_PATTERNED_SEA_GLASS, "Light Blue Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.LIGHT_GRAY_PATTERNED_SEA_GLASS, "Light Gray Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.MAGENTA_PATTERNED_SEA_GLASS, "Magenta Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.PURPLE_PATTERNED_SEA_GLASS, "Purple Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.PINK_PATTERNED_SEA_GLASS, "Pink Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.BLACK_PATTERNED_SEA_GLASS, "Black Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.GRAY_PATTERNED_SEA_GLASS, "Gray Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.WHITE_PATTERNED_SEA_GLASS, "White Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.BROWN_PATTERNED_SEA_GLASS, "Brown Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.CYAN_PATTERNED_SEA_GLASS, "Cyan Patterned Sea Glass", false);
        this.addBlocksFromType(BlockInit.MONOCHROMATIC_SEA_GLASS, "Monochromatic Sea Glass", false);
        this.addBlocksFromType(BlockInit.MULTICOLOR_SEA_GLASS, "Multicolor Sea Glass", false);
        this.addBlocksFromType(BlockInit.ANCIENT_BAMBOO, "Ancient Bamboo", true);
        this.addBlocksFromType(BlockInit.ANCIENT_ACACIA, "Ancient Acacia", true);
        this.addBlocksFromType(BlockInit.ANCIENT_SPRUCE, "Ancient Spruce", true);
        this.addBlocksFromType(BlockInit.ANCIENT_BIRCH, "Ancient Birch", true);
        this.addBlocksFromType(BlockInit.ANCIENT_CRIMSON, "Ancient Crimson", true);
        this.addBlocksFromType(BlockInit.ANCIENT_WARPED, "Ancient Warped", true);
        this.addBlocksFromType(BlockInit.ANCIENT_JUNGLE, "Ancient Jungle", true);
        this.addBlocksFromType(BlockInit.ANCIENT_OAK, "Ancient Oak", true);
        this.addBlocksFromType(BlockInit.ANCIENT_DARK_OAK, "Ancient Dark Oak", true);
        this.addBlocksFromType(BlockInit.ANCIENT_MANGROVE, "Ancient Mangrove", true);
        this.addBlock(BlockInit.COQUINA);

        for (Map<DyeColor, RegistryObject<Block>> block : BlockInit.DYED_LINGUISTICS.values()) {
            for (DyeColor color : DyeColor.values()) {
                this.addBlock(block.get(color), WordUtils.capitalize(block.get(color).get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            }
        }

        for (RegistryObject<Block> block : BlockInit.NON_LINGUISTICS.values()) {
            this.addBlock(block, WordUtils.capitalize(block.get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
        }

        for (RegistryObject<Block> block : BlockInit.COLORED_SHELL_BLOCKS.values()) {
            this.addBlock(block, WordUtils.capitalize(block.get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
        }

        for (RegistryObject<Block> block : BlockInit.CRACKED_MOSSY_SHELL_BLOCKS.values()) {
            this.addBlock(block, WordUtils.capitalize(block.get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
        }

        for (RegistryObject<Block> block : BlockInit.CRACKED_SHELL_BLOCKS.values()) {
            this.addBlock(block, WordUtils.capitalize(block.get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
        }

        for (RegistryObject<Block> block : BlockInit.MOSSY_SHELL_BLOCKS.values()) {
            this.addBlock(block, WordUtils.capitalize(block.get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
        }
        this.add(ItemInit.AQUAMARINE_HAMMER.get(), "Aquamarine Hammer");
        this.add("aquamarine_hammer.description", "An ancient hammer from long ago");
        this.add("aquamarine_hammer.tooltip", "Right click to break blocks in a 3x3 area");
        this.add(ItemInit.ORICHALCUM_HAMMER.get(), "Orichalcum Hammer");
        this.add("orichalcum_hammer.description", "A hammer made from the rarest of materials");
        this.add("orichalcum_hammer.tooltip", "Right click to break blocks in a 5x5 area");
        this.add("enchantment.atlantis.deeper_depth", "Deeper Depth");

        this.add("item.minecraft.smithing_template.orichalcum_upgrade.ingredients", "Orichalcum Ingot");
        this.add("item.minecraft.smithing_template.orichalcum_upgrade.base_slot_description", "Add orichalcum armor, weapon, or tool");
        this.add("item.minecraft.smithing_template.orichalcum_upgrade.additions_slot_description", "Add Orichalcum Ingot");
        this.add("item.minecraft.smithing_template.orichalcum_upgrade.applies_to", "Aquamarine Equipment");
        this.add("upgrade.minecraft.orichalcum_upgrade", "Orichalcum Upgrade Smithing Template");
        this.add("enchantment.atlantis.lightning_protection", "Lightning Protection");
        this.add("container.writing", "Writing");
        this.add("container.linguistic", "Linguistic");
        this.add("effect.atlantis.spikes", "Spikes");
        this.add("effect.atlantis.spikes.description", "You attack with a thorns like effect");

        this.add(ItemInit.ATLANTEAN_AMULET.get(), "Atlantean Amulet");
        this.add(ItemInit.ATLANTEAN_SPEAR.get(), "Atlantean Spear");
        this.add(ItemInit.PALM_BOAT.get(), "Palm Boat");
        this.add(ItemInit.NYMPH_BOAT.get(), "Nymph Boat");
        this.add(ItemInit.RUBYCLAW_CRAB_EGG.get(), "Rubyclaw Crab Egg");
        this.add(ItemInit.AQUAIEL_JELLYFISH_EGG.get(), "Aquaiel Jellyfish Egg");
        this.add(ItemInit.GLITTERTAIL_SHRIMP_EGG.get(), "Glittertail Shrimp Egg");
        this.add(ItemInit.LEVIATHAN_EGG.get(), "Leviathan Egg");
        this.add(ItemInit.THALASSIAN_SEAHORSE_EGG.get(), "Thalassian Seahorse Egg");
        this.add(ItemInit.COCONUT_CRAB_EGG.get(), "Coconut Crab Egg");
        this.add(ItemInit.STARFISH_EGG.get(), "Starfish Egg");
        this.add(ItemInit.ZOMBIE_STARFISH_EGG.get(), "Zombie Starfish Egg");
        this.addRecord(ItemInit.PANBEE, "LudoCrypt - Panbee");
        this.addRecord(ItemInit.COLUMN_CAVITATION, "Firel - Column Cavitation");
        this.add(ItemInit.BROKEN_SHELLS.get(), "Broken Shells");
        this.add(ItemInit.SODIUM_NUGGET.get(), "Sodium Nugget");
        this.add(ItemInit.SEASALT.get(), "Seasalt");
        this.add(ItemInit.FIRE_MELON_JELLY_BOTTLE.get(), "Fire Melon Jelly Bottle");
        this.add(ItemInit.JELLY_BOTTLE.get(), "Jelly Bottle");
        this.add(ItemInit.AQUAMARINE_GEM.get(), "Aquamarine Gem");
        this.add(ItemInit.ORICHALCUM_INGOT.get(), "Orichalcum Ingot");
        this.add(ItemInit.ORICHALCUM_BLEND.get(), "Orichalcum Blend");
        this.add(ItemInit.ORB_OF_ATLANTIS.get(), "Orb Of Atlantis");
        this.add(ItemInit.ATLANTEAN_CRYSTAL.get(), "Flow-Water Crystal");
        this.add(ItemInit.OCEAN_STONE.get(), "Ocean Stone");
        this.add(ItemInit.DROP_OF_ATLANTIS.get(), "Drop Of Atlantis");
        this.add(ItemInit.BROWN_WROUGHT_PATCHES.get(), "Brown Wrought Patches");
        this.add(ItemInit.CRAB_LEGS.get(), "Crab Legs");
        this.add(ItemInit.SHRIMP.get(), "Shrimp");
        this.add(ItemInit.COOKED_SHRIMP.get(), "Cooked Shrimp");
        this.add(ItemInit.AQUATIC_POWER_DUST.get(), "Aquatic Power Dust");
        this.add(ItemInit.AQUAIEL_STRING.get(), "Aquariel String");
        this.add(ItemInit.SUBMARINE.get(), "Submarine");
        this.add(ItemInit.WATER_PILL.get(), "Water Pill");
        for(TrailsGroup group : BlockInit.ANCIENT_CUPRUM.values()) {
            this.add(group.block().get(), WordUtils.capitalize(group.block().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.chiseled().get(), WordUtils.capitalize(group.chiseled().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.cut().get(), WordUtils.capitalize(group.cut().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.cut_slab().get(), WordUtils.capitalize(group.cut_slab().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.cut_stairs().get(), WordUtils.capitalize(group.cut_stairs().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.trapdoor().get(), WordUtils.capitalize(group.trapdoor().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.door().get(), WordUtils.capitalize(group.door().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.grate().get(), WordUtils.capitalize(group.grate().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.bulb().get(), WordUtils.capitalize(group.bulb().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_block().get(), WordUtils.capitalize(group.waxed_block().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_chiseled().get(), WordUtils.capitalize(group.waxed_chiseled().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_cut().get(), WordUtils.capitalize(group.waxed_cut().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_cut_slab().get(), WordUtils.capitalize(group.waxed_cut_slab().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_cut_stairs().get(), WordUtils.capitalize(group.waxed_cut_stairs().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_trapdoor().get(), WordUtils.capitalize(group.waxed_trapdoor().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_door().get(), WordUtils.capitalize(group.waxed_door().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_grate().get(), WordUtils.capitalize(group.waxed_grate().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
            this.add(group.waxed_bulb().get(), WordUtils.capitalize(group.waxed_bulb().get().getDescriptionId().replace("block.atlantis.", "").replace("_", " ")));
        }

        this.addBlock(BlockInit.ANCIENT_CUPRUM_ORE, "Ancient Cuprum Ore");
        this.addBlock(BlockInit.DEEPSLATE_ANCIENT_CUPRUM_ORE, "Deepslate Ancient Cuprum Ore");
        this.addBlock(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK, "Block of Raw Ancient Cuprum");
        this.add(ItemInit.ANCIENT_CUPRUM_INGOT.get(), "Ancient Cuprum Ingot");
        this.add(ItemInit.RAW_ANCIENT_CUPRUM.get(), "Raw Ancient Cuprum");

        add("block.atlantis.nymph_sign", "Nymph Sign");
        add("block.atlantis.nymph_wall_sign", "Nymph Sign");
        add("block.atlantis.palm_sign", "Palm Sign");
        add("block.atlantis.palm_wall_sign", "Palm Sign");

        this.add(ItemInit.FIRE_MELON_FRUIT.get(), "Fire Melon Fruit");
        this.add(ItemInit.FIRE_MELON_FRUIT_SPIKED.get(), "Fire Melon Fruit Spiked");
        this.add(ItemInit.FIRE_MELON_SEEDS.get(), "Fire Melon Seeds");
        this.add(ItemInit.FIRE_MELON_SPIKE.get(), "Fire Melon Spike");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL.get(), "Linguistic Glyph Scroll");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_A.get(), "Linguistic Glyph Scroll A");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_B.get(), "Linguistic Glyph Scroll B");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_C.get(), "Linguistic Glyph Scroll C");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_D.get(), "Linguistic Glyph Scroll D");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_E.get(), "Linguistic Glyph Scroll E");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_F.get(), "Linguistic Glyph Scroll F");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_G.get(), "Linguistic Glyph Scroll G");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_H.get(), "Linguistic Glyph Scroll H");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_I.get(), "Linguistic Glyph Scroll I");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_J.get(), "Linguistic Glyph Scroll J");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_K.get(), "Linguistic Glyph Scroll K");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_L.get(), "Linguistic Glyph Scroll L");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_M.get(), "Linguistic Glyph Scroll M");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_N.get(), "Linguistic Glyph Scroll N");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_O.get(), "Linguistic Glyph Scroll O");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_P.get(), "Linguistic Glyph Scroll P");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_Q.get(), "Linguistic Glyph Scroll Q");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_R.get(), "Linguistic Glyph Scroll R");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_S.get(), "Linguistic Glyph Scroll S");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_T.get(), "Linguistic Glyph Scroll T");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_U.get(), "Linguistic Glyph Scroll U");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_V.get(), "Linguistic Glyph Scroll V");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_W.get(), "Linguistic Glyph Scroll W");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_X.get(), "Linguistic Glyph Scroll X");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_Y.get(), "Linguistic Glyph Scroll Y");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_Z.get(), "Linguistic Glyph Scroll Z");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_0.get(), "Linguistic Glyph Scroll 0");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_1.get(), "Linguistic Glyph Scroll 1");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_2.get(), "Linguistic Glyph Scroll 2");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_3.get(), "Linguistic Glyph Scroll 3");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_4.get(), "Linguistic Glyph Scroll 4");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_5.get(), "Linguistic Glyph Scroll 5");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_6.get(), "Linguistic Glyph Scroll 6");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_7.get(), "Linguistic Glyph Scroll 7");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_8.get(), "Linguistic Glyph Scroll 8");
        this.add(ItemInit.LINGUISTIC_GLYPH_SCROLL_9.get(), "Linguistic Glyph Scroll 9");
        this.add(ItemInit.JETSTREAM_WATER_BUCKET.get(), "Jetstream Water Bucket");
        this.add(ItemInit.SALTY_SEAWATER_BUCKET.get(), "Salty Seawater Bucket");
        this.add(ItemInit.RUBYCLAW_CRAB_BUCKET.get(), "Bucket of Rubyclaw Crab");
        this.add(ItemInit.AQUAIEL_JELLYFISH_BUCKET.get(), "Bucket of Aquaiel Jellyfish");
        this.add(ItemInit.GLITTERTAIL_SHRIMP_BUCKET.get(), "Bucket of Glittertail Shrimp");
        this.add(ItemInit.THALASSIAN_SEAHORSE_BUCKET.get(), "Bucket of Thalassian Seahorse");
        this.add(ItemInit.AQUAMARINE_AXE.get(), "Aquamarine Axe");
        this.add(ItemInit.AQUAMARINE_PICKAXE.get(), "Aquamarine Pickaxe");
        this.add(ItemInit.AQUAMARINE_SHOVEL.get(), "Aquamarine Shovel");
        this.add(ItemInit.AQUAMARINE_HOE.get(), "Aquamarine Hoe");
        this.add(ItemInit.AQUAMARINE_SWORD.get(), "Aquamarine Sword");
        this.add(ItemInit.ORICHALCUM_AXE.get(), "Orichalcum Axe");
        this.add(ItemInit.ORICHALCUM_PICKAXE.get(), "Orichalcum Pickaxe");
        this.add(ItemInit.ORICHALCUM_SHOVEL.get(), "Orichalcum Shovel");
        this.add(ItemInit.ORICHALCUM_HOE.get(), "Orichalcum Hoe");
        this.add(ItemInit.ORICHALCUM_SWORD.get(), "Orichalcum Sword");
        this.add(ItemInit.AQUAMARINE_HELMET.get(), "Aquamarine Helmet");
        this.add(ItemInit.AQUAMARINE_CHESTPLATE.get(), "Aquamarine Chestplate");
        this.add(ItemInit.AQUAMARINE_LEGGINGS.get(), "Aquamarine Leggings");
        this.add(ItemInit.AQUAMARINE_BOOTS.get(), "Aquamarine Boots");
        this.add(ItemInit.BROWN_WROUGHT_HELMET.get(), "Brown Wrought Helmet");
        this.add(ItemInit.BROWN_WROUGHT_CHESTPLATE.get(), "Brown Wrought Chestplate");
        this.add(ItemInit.BROWN_WROUGHT_LEGGINGS.get(), "Brown Wrought Leggings");
        this.add(ItemInit.BROWN_WROUGHT_BOOTS.get(), "Brown Wrought Boots");
        this.add(ItemInit.ORICHALCUM_UPGRADE_SMITHING_TEMPLATE.get(), "Orichalcum Upgrade Smithing Template");
        this.add(ItemInit.ORICHALCUM_HELMET.get(), "Orichalcum Helmet");
        this.add(ItemInit.ORICHALCUM_CHESTPLATE.get(), "Orichalcum Chestplate");
        this.add(ItemInit.ORICHALCUM_LEGGINGS.get(), "Orichalcum Leggings");
        this.add(ItemInit.ORICHALCUM_BOOTS.get(), "Orichalcum Boots");

        this.add(AtlantisEntityInit.PALM_BOAT.get(), "Palm Boat");
        this.add(AtlantisEntityInit.NYMPH_BOAT.get(), "Nymph Boat");
        this.add(AtlantisEntityInit.RUBYCLAW_CRAB.get(), "Rubyclaw Crab");
        this.add(AtlantisEntityInit.COCONUT_CRAB.get(), "Coconut Crab");
        this.add(AtlantisEntityInit.AQUAIEL_JELLYFISH.get(), "Aquaiel Jellyfish");
        this.add(AtlantisEntityInit.GLITTERTAIL_SHRIMP.get(), "Glittertail Shrimp");
        this.add(AtlantisEntityInit.LEVIATHAN.get(), "Leviathan");
        this.add(AtlantisEntityInit.THALASSIAN_SEAHORSE.get(), "Thalassian Seahorse");
        this.add(AtlantisEntityInit.STARFISH.get(), "Starfish");
        this.add(AtlantisEntityInit.ZOMBIE_STARFISH.get(), "Zombie Starfish");
        this.add(AtlantisEntityInit.SODIUM_BOMB.get(), "Sodium Bomb");
        this.add(AtlantisEntityInit.SUBMARINE.get(), "Submarine");

        this.add("biome.atlantis.atlantean_garden", "Atlantean Garden");
        this.add("biome.atlantis.atlantean_islands_biome", "Atlantean Islands");
        this.add("biome.atlantis.atlantis_biome", "Atlantis Biome");
        this.add("biome.atlantis.coconut_isles", "Coconut Isles");
        this.add("biome.atlantis.goo_lagoons", "Goo Lagoons");
        this.add("biome.atlantis.jellyfish_fields", "Jellyfish Fields");
        this.add("biome.atlantis.volcanic_darksea", "Volcanic Darksea");
        this.add("structure.atlantis.atlantean_village", "Atlantean Village");
        this.add("dimension.atlantis.atlantis", "Atlantis");
        this.add("structure.atlantis.configured_atlantean_fountain", "Atlantean Fountain");
        this.add("structure.atlantis.configured_atlantean_house_1", "Atlantean House Variant 1");
        this.add("structure.atlantis.configured_atlantean_house_3", "Atlantean House Variant 3");
        this.add("structure.atlantis.configured_atlantean_spire", "Atlantean Spire");
        this.add("structure.atlantis.configured_atlantean_temple", "Atlantean Temple");
        this.add("structure.atlantis.configured_atlantean_tower", "Atlantean Tower");
        this.add("structure.atlantis.configured_oyster_structure", "Giant Oyster");
        this.add("itemGroup.atlantis.general", "Atlantis");
        this.add("itemGroup.atlantis.glyph", "Glyphs");
        this.add("text.autoconfig.atlantis.option.calciteAcceleration", "Calcite Acceleration");
        this.add("text.autoconfig.atlantis.option.calciteThreshold", "Calcite Threshold");
        this.add("text.autoconfig.atlantis.option.general.calciteAcceleration", "Calcite Acceleration");
        this.add("text.autoconfig.atlantis.option.general.calciteAcceleration.@Tooltip", "How fast you start to speed up at. Must reload world for it to work!");
        this.add("text.autoconfig.atlantis.option.general.calciteThreshold", "Calcite Threshold");
        this.add("text.autoconfig.atlantis.option.general.calciteThreshold.@Tooltip", "The maximum speed you can be at. Must reload world for it to work!");
        this.add("text.autoconfig.atlantis.option.islandsOn", "Are islands on?");
        this.add("text.autoconfig.atlantis.option.maxCrabSpawnHeight", "Maximum Crab Spawn Height");
        this.add("text.autoconfig.atlantis.option.maxCrabSpawnHeight.@Tooltip", "The Maximum Height The Rubyclaw Crab Will Spawn At");
        this.add("text.autoconfig.atlantis.option.minCrabSpawnHeight", "Minimum Crab Spawn Height");
        this.add("text.autoconfig.atlantis.option.minCrabSpawnHeight.@Tooltip", "The Minimum Height The Rubyclaw Crab Will Spawn At");
        this.add("text.autoconfig.atlantis.option.volcanoesOn", "Are volcanoes on?");
        this.add("text.autoconfig.atlantis.title", "Atlantean Config");
    }

    private <T extends Item> void addRecord(RegistryObject<T> record, String s) {
        addItem(record, "Music Disc");
        add("item." + record.getId().getNamespace() + "." + record.getId().getPath() + ".desc", s);
    }

    private void addBlocksFromType(BlockType type, String base, boolean isWood) {
        addBlock(type.block(), base + (isWood ? " Planks" : ""));
        if(type.door() != null) addBlock(type.door(), base + " Door");
        if(type.button() != null) addBlock(type.button(), base + " Button");
        if(type.slab() != null) addBlock(type.slab(), base + " Slab");
        if(type.stairs() != null) addBlock(type.stairs(), base + " Stairs");
        if(type.fence() != null) addBlock(type.fence(), base + " Fence");
        if(type.fenceGate() != null) addBlock(type.fenceGate(), base + " Fence Gate");
        if(type.pressurePlate() != null) addBlock(type.pressurePlate(), base + " Pressure Plate");
        if(type.trapDoor() != null) addBlock(type.trapDoor(), base + " Trapdoor");
        if(type.wall() != null) addBlock(type.wall(), base + " Wall");
    }

    private void addItem(RegistryObject<Item> registryObject) {
        addItem(registryObject, WordUtils.capitalizeFully(registryObject.getId().getPath().replace("_", " ")));
    }

    private <T extends Block> void addBlock(RegistryObject< T> registryObject) {
        addBlock(registryObject, WordUtils.capitalizeFully(registryObject.getId().getPath().replace("_", " ")));
    }


    private void add(RegistryObject<CreativeModeTab> tab, String entry) {
        this.add("itemGroup." + tab.getId().toLanguageKey(), entry);
    }
}
