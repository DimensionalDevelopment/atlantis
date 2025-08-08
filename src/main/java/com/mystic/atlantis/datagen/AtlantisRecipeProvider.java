package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.blocks.ancient_cuprum.WeatheringCuprum;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.recipes.WritingRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.mystic.atlantis.init.BlockInit.*;

public class AtlantisRecipeProvider extends RecipeProvider {
    private static CompletableFuture<HolderLookup.Provider> lookup;

    public AtlantisRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
        AtlantisRecipeProvider.lookup = lookup;
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput out) {
        var list = ItemInit.getScrolls();
        var ingredient = Ingredient.of(list.toArray(Item[]::new));
        for (var result : list) {
            glyphScroll(out, result, ingredient);
        }

        for (WeatheringCuprum.WeatherState state : WeatheringCuprum.WeatherState.values()) {
            var group = BlockInit.ANCIENT_CUPRUM.get(state);
            registerGroup(group, out);

            if (WeatheringCuprum.WeatherState.UNAFFECTED == group.block().get().getAge()) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, group.block().get(), 0.7F, 200)
                        .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                        .save(out, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_smelting"));

                SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, group.block().get(), 0.7F, 100)
                        .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                        .save(out, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_blasting"));
            }
        }

        registerWood(ANCIENT_BAMBOO, out);
        registerWood(ANCIENT_ACACIA, out);
        registerWood(ANCIENT_SPRUCE, out);
        registerWood(ANCIENT_BIRCH, out);
        registerWood(ANCIENT_CRIMSON, out);
        registerWood(ANCIENT_WARPED, out);
        registerWood(ANCIENT_JUNGLE, out);
        registerWood(ANCIENT_OAK, out);
        registerWood(ANCIENT_DARK_OAK, out);
        registerWood(ANCIENT_MANGROVE, out);
        registerWood(ANCIENT_CHERRY, out);
        registerWood(NYMPH_PLANKS, out);
        registerWood(PALM_PLANKS, out);

        registerSeaGlass(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:algae"), 9)
                .requires(i("atlantis:algae_block"))
                .unlockedBy("has_algae-1", has(i("atlantis:algae_block")))
                .save(out, Atlantis.id("algae-1"));

        // recipes/algae-2.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:algae"), 4)
                .pattern(" B ")
                .pattern("BAB")
                .pattern(" B ")
                .define('B', i("minecraft:kelp"))
                .define('A', i("minecraft:sand"))
                .unlockedBy("has_algae-2", has(i("minecraft:kelp")))
                .save(out, Atlantis.id("algae-2"));

        // recipes/algae_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:algae_block"), 1)
                .pattern("WWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('W', i("atlantis:algae"))
                .unlockedBy("has_algae_block", has(i("atlantis:algae")))
                .save(out, Atlantis.id("algae_block"));

        // recipes/algae_detritus_stone.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:algae_detritus_stone"), 1)
                .requires(i("atlantis:algae"))
                .requires(i("atlantis:detritus_sandstone"))
                .unlockedBy("has_algae_detritus_stone", has(i("atlantis:algae")))
                .save(out, Atlantis.id("algae_detritus_stone"));

        // recipes/ancient_acacia_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_acacia_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:acacia_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_acacia_planks", has(i("minecraft:acacia_planks")))
                .save(out, Atlantis.id("ancient_acacia_planks"));

        // recipes/ancient_bamboo_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_bamboo_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:bamboo_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_bamboo_planks", has(i("minecraft:bamboo_planks")))
                .save(out, Atlantis.id("ancient_bamboo_planks"));

        // recipes/ancient_birch_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_birch_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:birch_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_birch_planks", has(i("minecraft:birch_planks")))
                .save(out, Atlantis.id("ancient_birch_planks"));

        // recipes/ancient_cherry_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_cherry_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:cherry_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_cherry_planks", has(i("minecraft:cherry_planks")))
                .save(out, Atlantis.id("ancient_cherry_planks"));

        // recipes/ancient_crimson_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_crimson_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:crimson_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_crimson_planks", has(i("minecraft:crimson_planks")))
                .save(out, Atlantis.id("ancient_crimson_planks"));

        // recipes/ancient_dark_oak_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_dark_oak_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:dark_oak_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_dark_oak_planks", has(i("minecraft:dark_oak_planks")))
                .save(out, Atlantis.id("ancient_dark_oak_planks"));

        // recipes/ancient_jungle_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_jungle_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:jungle_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_jungle_planks", has(i("minecraft:jungle_planks")))
                .save(out, Atlantis.id("ancient_jungle_planks"));

        // recipes/ancient_mangrove_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_mangrove_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:mangrove_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_mangrove_planks", has(i("minecraft:mangrove_planks")))
                .save(out, Atlantis.id("ancient_mangrove_planks"));

        // recipes/ancient_oak_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_oak_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:oak_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_oak_planks", has(i("minecraft:oak_planks")))
                .save(out, Atlantis.id("ancient_oak_planks"));

        // recipes/ancient_spruce_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_spruce_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:spruce_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_spruce_planks", has(i("minecraft:spruce_planks")))
                .save(out, Atlantis.id("ancient_spruce_planks"));

        // recipes/ancient_warped_planks.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ancient_warped_planks"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:warped_planks"))
                .define('S', i("atlantis:algae"))
                .unlockedBy("has_ancient_warped_planks", has(i("minecraft:warped_planks")))
                .save(out, Atlantis.id("ancient_warped_planks"));

        // recipes/aquaiel_string.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:aquaiel_string"), 2)
                .requires(i("minecraft:string"))
                .requires(i("atlantis:ocean_stone"))
                .unlockedBy("has_aquaiel_string", has(i("minecraft:string")))
                .save(out, Atlantis.id("aquaiel_string"));

        // recipes/aquamarine_axe.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_axe"), 1)
                .pattern("II ")
                .pattern("IC ")
                .pattern(" C ")
                .define('C', i("minecraft:stick"))
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_axe", has(i("minecraft:stick")))
                .save(out, Atlantis.id("aquamarine_axe"));

        // recipes/aquamarine_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_block"), 1)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_block", has(i("atlantis:aquamarine_gem")))
                .save(out, Atlantis.id("aquamarine_block"));

        // recipes/aquamarine_chestplate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_chestplate"), 1)
                .pattern("I I")
                .pattern("III")
                .pattern("III")
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_chestplate", has(i("atlantis:aquamarine_gem")))
                .save(out, Atlantis.id("aquamarine_chestplate"));

        // recipes/aquamarine_gem_blasting.json
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(i("atlantis:aquamarine_ore")), RecipeCategory.MISC, i("atlantis:aquamarine_gem"), 0.3F, 140)
                .unlockedBy("has_aquamarine_gem_blasting", has(i("atlantis:aquamarine_ore")))
                .save(out, Atlantis.id("aquamarine_gem_blasting"));

        // recipes/aquamarine_gem_smelting.json
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(i("atlantis:aquamarine_ore")), RecipeCategory.MISC, i("atlantis:aquamarine_gem"), 0.3F, 180)
                .unlockedBy("has_aquamarine_gem_smelting", has(i("atlantis:aquamarine_ore")))
                .save(out, Atlantis.id("aquamarine_gem_smelting"));

        // recipes/aquamarine_hammer.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_hammer"), 1)
                .pattern("SSS")
                .pattern("SWS")
                .pattern(" W ")
                .define('W', i("minecraft:stick"))
                .define('S', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_hammer", has(i("minecraft:stick")))
                .save(out, Atlantis.id("aquamarine_hammer"));

        // recipes/aquamarine_helmet.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_helmet"), 1)
                .pattern("III")
                .pattern("I I")
                .pattern("   ")
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_helmet", has(i("atlantis:aquamarine_gem")))
                .save(out, Atlantis.id("aquamarine_helmet"));

        // recipes/aquamarine_hoe.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_hoe"), 1)
                .pattern("II ")
                .pattern(" C ")
                .pattern(" C ")
                .define('C', i("minecraft:stick"))
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_hoe", has(i("minecraft:stick")))
                .save(out, Atlantis.id("aquamarine_hoe"));

        // recipes/aquamarine_leggings.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_leggings"), 1)
                .pattern("III")
                .pattern("I I")
                .pattern("I I")
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_leggings", has(i("atlantis:aquamarine_gem")))
                .save(out, Atlantis.id("aquamarine_leggings"));

        // recipes/aquamarine_pickaxe.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_pickaxe"), 1)
                .pattern("III")
                .pattern(" C ")
                .pattern(" C ")
                .define('C', i("minecraft:stick"))
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_pickaxe", has(i("minecraft:stick")))
                .save(out, Atlantis.id("aquamarine_pickaxe"));

        // recipes/aquamarine_shovel.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_shovel"), 1)
                .pattern(" I ")
                .pattern(" C ")
                .pattern(" C ")
                .define('C', i("minecraft:stick"))
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_shovel", has(i("minecraft:stick")))
                .save(out, Atlantis.id("aquamarine_shovel"));

        // recipes/aquamarine_sword.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_sword"), 1)
                .pattern(" I ")
                .pattern(" I ")
                .pattern(" C ")
                .define('C', i("minecraft:stick"))
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aquamarine_sword", has(i("minecraft:stick")))
                .save(out, Atlantis.id("aquamarine_sword"));

        // recipes/aquatic_power_comparator.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquatic_power_comparator"), 1)
                .pattern(" S ")
                .pattern("SWS")
                .pattern("EEE")
                .define('W', i("atlantis:ocean_stone"))
                .define('S', i("atlantis:aquatic_power_torch"))
                .define('E', i("minecraft:stone"))
                .unlockedBy("has_aquatic_power_comparator", has(i("atlantis:ocean_stone")))
                .save(out, Atlantis.id("aquatic_power_comparator"));

        // recipes/aquatic_power_dust.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:aquatic_power_dust"), 1)
                .requires(i("minecraft:redstone"))
                .requires(i("atlantis:ocean_stone"))
                .unlockedBy("has_aquatic_power_dust", has(i("minecraft:redstone")))
                .save(out, Atlantis.id("aquatic_power_dust"));

        // recipes/aquatic_power_lamp.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquatic_power_lamp"), 1)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('W', i("minecraft:glowstone"))
                .define('S', i("atlantis:aquatic_power_dust"))
                .unlockedBy("has_aquatic_power_lamp", has(i("minecraft:glowstone")))
                .save(out, Atlantis.id("aquatic_power_lamp"));

        // recipes/aquatic_power_lever.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:aquatic_power_lever"), 2)
                .requires(i("minecraft:lever"))
                .requires(i("atlantis:ocean_stone"))
                .unlockedBy("has_aquatic_power_lever", has(i("minecraft:lever")))
                .save(out, Atlantis.id("aquatic_power_lever"));

        // recipes/aquatic_power_repeater.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquatic_power_repeater"), 1)
                .pattern("EWE")
                .pattern("SSS")
                .pattern("   ")
                .define('W', i("atlantis:aquatic_power_dust"))
                .define('S', i("minecraft:stone"))
                .define('E', i("atlantis:aquatic_power_torch"))
                .unlockedBy("has_aquatic_power_repeater", has(i("atlantis:aquatic_power_dust")))
                .save(out, Atlantis.id("aquatic_power_repeater"));

        // recipes/aquatic_power_stone.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquatic_power_stone"), 1)
                .pattern("ESE")
                .pattern("SWS")
                .pattern("ESE")
                .define('W', i("minecraft:iron_ingot"))
                .define('S', i("atlantis:aquamarine_gem"))
                .define('E', i("atlantis:aquatic_power_dust"))
                .unlockedBy("has_aquatic_power_stone", has(i("minecraft:iron_ingot")))
                .save(out, Atlantis.id("aquatic_power_stone"));

        // recipes/aquatic_power_torch.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquatic_power_torch"), 1)
                .pattern(" S ")
                .pattern(" W ")
                .pattern("   ")
                .define('W', i("minecraft:stick"))
                .define('S', i("atlantis:aquatic_power_dust"))
                .unlockedBy("has_aquatic_power_torch", has(i("minecraft:stick")))
                .save(out, Atlantis.id("aquatic_power_torch"));

        // recipes/aquatic_power_tripwire_hook.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:aquatic_power_tripwire_hook"), 2)
                .requires(i("minecraft:tripwire_hook"))
                .requires(i("atlantis:aquatic_power_dust"))
                .unlockedBy("has_aquatic_power_tripwire_hook", has(i("minecraft:tripwire_hook")))
                .save(out, Atlantis.id("aquatic_power_tripwire_hook"));

        // recipes/aqumarine_boots.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:aquamarine_boots"), 1)
                .pattern("   ")
                .pattern("I I")
                .pattern("I I")
                .define('I', i("atlantis:aquamarine_gem"))
                .unlockedBy("has_aqumarine_boots", has(i("atlantis:aquamarine_gem")))
                .save(out, Atlantis.id("aqumarine_boots"));

        // recipes/atlantean_amulet.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:atlantean_amulet"), 1)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('W', i("minecraft:fire_charge"))
                .define('S', i("atlantis:atlantean_crystal"))
                .unlockedBy("has_atlantean_amulet", has(i("minecraft:fire_charge")))
                .save(out, Atlantis.id("atlantean_amulet"));

        // recipes/atlantean_bow.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("minecraft:stick"), 2)
                .pattern(" SW")
                .pattern("S W")
                .pattern(" SW")
                .define('W', i("atlantis:aquaiel_string"))
                .define('S', i("minecraft:stick"))
                .unlockedBy("has_atlantean_bow", has(i("atlantis:aquaiel_string")))
                .save(out, Atlantis.id("atlantean_bow"));

        // recipes/atlantean_core.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:atlantean_core"), 1)
                .pattern("CIC")
                .pattern("IOI")
                .pattern("CIC")
                .define('C', i("minecraft:cobblestone"))
                .define('I', i("minecraft:iron_ingot"))
                .define('O', i("atlantis:ocean_lantern"))
                .unlockedBy("has_atlantean_core", has(i("minecraft:cobblestone")))
                .save(out, Atlantis.id("atlantean_core"));

        // recipes/atlantean_crystal.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:atlantean_crystal"), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('W', i("minecraft:gold_nugget"))
                .define('S', i("atlantis:ocean_stone"))
                .unlockedBy("has_atlantean_crystal", has(i("minecraft:gold_nugget")))
                .save(out, Atlantis.id("atlantean_crystal"));

        // recipes/atlantean_portal_frame.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:atlantean_portal_frame"), 1)
                .pattern("SOS")
                .pattern("OWO")
                .pattern("SOS")
                .define('W', i("atlantis:atlantean_core"))
                .define('S', i("atlantis:drop_of_atlantis"))
                .define('O', i("atlantis:ocean_stone"))
                .unlockedBy("has_atlantean_portal_frame", has(i("atlantis:atlantean_core")))
                .save(out, Atlantis.id("atlantean_portal_frame"));

        // recipes/atlantean_sign.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:nymph_sign"), 2)
                .pattern("SSS")
                .pattern("SSS")
                .pattern(" W ")
                .define('W', i("minecraft:stick"))
                .define('S', i("atlantis:nymph_planks"))
                .unlockedBy("has_atlantean_sign", has(i("minecraft:stick")))
                .save(out, Atlantis.id("atlantean_sign"));

        // recipes/atlantean_spear.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:atlantean_spear"), 1)
                .pattern("  S")
                .pattern(" W ")
                .pattern("W  ")
                .define('W', i("minecraft:iron_ingot"))
                .define('S', i("atlantis:atlantean_crystal"))
                .unlockedBy("has_atlantean_spear", has(i("minecraft:iron_ingot")))
                .save(out, Atlantis.id("atlantean_spear"));

        // recipes/belen_pot.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:belen_pot"), 1)
                .pattern("TNT")
                .pattern("TNT")
                .pattern("ANA")
                .define('N', i("atlantis:broken_shells"))
                .define('T', i("minecraft:brick"))
                .define('A', i("minecraft:clay"))
                .unlockedBy("has_belen_pot", has(i("atlantis:broken_shells")))
                .save(out, Atlantis.id("belen_pot"));

        // recipes/black_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:black_pearl_block"), 1)
                .requires(i("minecraft:black_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_black_pearl_block", has(i("minecraft:black_dye")))
                .save(out, Atlantis.id("black_pearl_block"));

        // recipes/black_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:black_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:black_dye"))
                .unlockedBy("has_black_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("black_sea_glass"));

        // recipes/black_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:black_sea_glass_button"), 1)
                .requires(i("atlantis:black_sea_glass"))
                .unlockedBy("has_black_sea_glass_button", has(i("atlantis:black_sea_glass")))
                .save(out, Atlantis.id("black_sea_glass_button"));

        // recipes/black_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:black_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:black_sea_glass"))
                .unlockedBy("has_black_sea_glass_pressure_plate", has(i("atlantis:black_sea_glass")))
                .save(out, Atlantis.id("black_sea_glass_pressure_plate"));

        // recipes/black_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:black_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:black_sea_glass"))
                .unlockedBy("has_black_sea_glass_slab", has(i("atlantis:black_sea_glass")))
                .save(out, Atlantis.id("black_sea_glass_slab"));

        // recipes/black_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:black_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:black_sea_glass"))
                .unlockedBy("has_black_sea_glass_wall", has(i("atlantis:black_sea_glass")))
                .save(out, Atlantis.id("black_sea_glass_wall"));

        // recipes/black_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:black_shell_block"), 1)
                .requires(i("minecraft:black_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_black_shell_block", has(i("minecraft:black_dye")))
                .save(out, Atlantis.id("black_shell_block"));

        // recipes/blue_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:blue_pearl_block"), 1)
                .requires(i("minecraft:blue_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_blue_pearl_block", has(i("minecraft:blue_dye")))
                .save(out, Atlantis.id("blue_pearl_block"));

        // recipes/blue_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:blue_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:blue_dye"))
                .unlockedBy("has_blue_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("blue_sea_glass"));

        // recipes/blue_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:blue_sea_glass_button"), 1)
                .requires(i("atlantis:blue_sea_glass"))
                .unlockedBy("has_blue_sea_glass_button", has(i("atlantis:blue_sea_glass")))
                .save(out, Atlantis.id("blue_sea_glass_button"));

        // recipes/blue_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:blue_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:blue_sea_glass"))
                .unlockedBy("has_blue_sea_glass_pressure_plate", has(i("atlantis:blue_sea_glass")))
                .save(out, Atlantis.id("blue_sea_glass_pressure_plate"));

        // recipes/blue_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:blue_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:blue_sea_glass"))
                .unlockedBy("has_blue_sea_glass_slab", has(i("atlantis:blue_sea_glass")))
                .save(out, Atlantis.id("blue_sea_glass_slab"));

        // recipes/blue_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:blue_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:blue_sea_glass"))
                .unlockedBy("has_blue_sea_glass_wall", has(i("atlantis:blue_sea_glass")))
                .save(out, Atlantis.id("blue_sea_glass_wall"));

        // recipes/blue_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:blue_shell_block"), 1)
                .requires(i("minecraft:blue_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_blue_shell_block", has(i("minecraft:blue_dye")))
                .save(out, Atlantis.id("blue_shell_block"));

        // recipes/brown_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:brown_pearl_block"), 1)
                .requires(i("minecraft:brown_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_brown_pearl_block", has(i("minecraft:brown_dye")))
                .save(out, Atlantis.id("brown_pearl_block"));

        // recipes/brown_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:brown_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:brown_dye"))
                .unlockedBy("has_brown_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("brown_sea_glass"));

        // recipes/brown_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:brown_sea_glass_button"), 1)
                .requires(i("atlantis:brown_sea_glass"))
                .unlockedBy("has_brown_sea_glass_button", has(i("atlantis:brown_sea_glass")))
                .save(out, Atlantis.id("brown_sea_glass_button"));

        // recipes/brown_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:brown_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:brown_sea_glass"))
                .unlockedBy("has_brown_sea_glass_pressure_plate", has(i("atlantis:brown_sea_glass")))
                .save(out, Atlantis.id("brown_sea_glass_pressure_plate"));

        // recipes/brown_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:brown_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:brown_sea_glass"))
                .unlockedBy("has_brown_sea_glass_slab", has(i("atlantis:brown_sea_glass")))
                .save(out, Atlantis.id("brown_sea_glass_slab"));

        // recipes/brown_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:brown_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:brown_sea_glass"))
                .unlockedBy("has_brown_sea_glass_wall", has(i("atlantis:brown_sea_glass")))
                .save(out, Atlantis.id("brown_sea_glass_wall"));

        // recipes/brown_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:brown_shell_block"), 1)
                .requires(i("minecraft:brown_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_brown_shell_block", has(i("minecraft:brown_dye")))
                .save(out, Atlantis.id("brown_shell_block"));

        // recipes/brown_wrought_boots.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:brown_wrought_boots"), 1)
                .pattern("   ")
                .pattern("# #")
                .pattern("# #")
                .define('#', i("atlantis:brown_wrought_patches"))
                .unlockedBy("has_brown_wrought_boots", has(i("atlantis:brown_wrought_patches")))
                .save(out, Atlantis.id("brown_wrought_boots"));

        // recipes/brown_wrought_chestplate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:brown_wrought_chestplate"), 1)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:brown_wrought_patches"))
                .unlockedBy("has_brown_wrought_chestplate", has(i("atlantis:brown_wrought_patches")))
                .save(out, Atlantis.id("brown_wrought_chestplate"));

        // recipes/brown_wrought_helmet.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:brown_wrought_helmet"), 1)
                .pattern("###")
                .pattern("# #")
                .pattern("   ")
                .define('#', i("atlantis:brown_wrought_patches"))
                .unlockedBy("has_brown_wrought_helmet", has(i("atlantis:brown_wrought_patches")))
                .save(out, Atlantis.id("brown_wrought_helmet"));

        // recipes/brown_wrought_leggings.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:brown_wrought_leggings"), 1)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', i("atlantis:brown_wrought_patches"))
                .unlockedBy("has_brown_wrought_leggings", has(i("atlantis:brown_wrought_patches")))
                .save(out, Atlantis.id("brown_wrought_leggings"));

        // recipes/brown_wrought_patches.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:brown_wrought_patches"), 6)
                .pattern("#L#")
                .pattern("LPL")
                .pattern("#L#")
                .define('#', i("minecraft:vine"))
                .define('L', i("minecraft:leather"))
                .define('P', i("minecraft:paper"))
                .unlockedBy("has_brown_wrought_patches", has(i("minecraft:vine")))
                .save(out, Atlantis.id("brown_wrought_patches"));

        // recipes/bubble_magma.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:bubble_magma"), 1)
                .requires(i("atlantis:ocean_stone"))
                .requires(i("minecraft:magma_block"))
                .unlockedBy("has_bubble_magma", has(i("atlantis:ocean_stone")))
                .save(out, Atlantis.id("bubble_magma"));

        // recipes/celen_pot.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:celen_pot"), 1)
                .pattern("ANT")
                .pattern("NNN")
                .pattern("ANT")
                .define('N', i("atlantis:broken_shells"))
                .define('T', i("minecraft:brick"))
                .define('A', i("minecraft:clay"))
                .unlockedBy("has_celen_pot", has(i("atlantis:broken_shells")))
                .save(out, Atlantis.id("celen_pot"));

        // recipes/chiseled_aquamarine_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:chiseled_aquamarine_block"), 2)
                .requires(i("atlantis:aquamarine_block"))
                .requires(i("atlantis:aquamarine_block"))
                .unlockedBy("has_chiseled_aquamarine_block", has(i("atlantis:aquamarine_block")))
                .save(out, Atlantis.id("chiseled_aquamarine_block"));

        // recipes/chiseled_golden_aquamarine.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:chiseled_golden_aquamarine"), 1)
                .requires(i("atlantis:chiseled_golden_block"))
                .requires(i("atlantis:aquamarine_gem"))
                .unlockedBy("has_chiseled_golden_aquamarine", has(i("atlantis:chiseled_golden_block")))
                .save(out, Atlantis.id("chiseled_golden_aquamarine"));

        // recipes/chiseled_golden_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:chiseled_golden_block"), 2)
                .requires(i("minecraft:gold_block"))
                .requires(i("minecraft:gold_block"))
                .unlockedBy("has_chiseled_golden_block", has(i("minecraft:gold_block")))
                .save(out, Atlantis.id("chiseled_golden_block"));

        // recipes/coconut_slice.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:coconut_slice"), 2)
                .requires(i("atlantis:coconut"))
                .unlockedBy("has_coconut_slice", has(i("atlantis:coconut")))
                .save(out, Atlantis.id("coconut_slice"));

        // recipes/cracked_glowstone.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:cracked_glowstone"), 2)
                .pattern(" W ")
                .pattern("WSW")
                .pattern(" W ")
                .define('W', i("atlantis:ocean_stone"))
                .define('S', i("minecraft:glowstone"))
                .unlockedBy("has_cracked_glowstone", has(i("atlantis:ocean_stone")))
                .save(out, Atlantis.id("cracked_glowstone"));

        // recipes/crystal_transference_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:crystal_transference_block"), 1)
                .pattern("RAR")
                .pattern("IRI")
                .pattern("AIA")
                .define('R', i("minecraft:redstone_block"))
                .define('I', i("minecraft:iron_ingot"))
                .define('A', i("atlantis:atlantean_crystal"))
                .unlockedBy("has_crystal_transference_block", has(i("minecraft:redstone_block")))
                .save(out, Atlantis.id("crystal_transference_block"));

        // recipes/cyan_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:cyan_pearl_block"), 1)
                .requires(i("minecraft:cyan_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_cyan_pearl_block", has(i("minecraft:cyan_dye")))
                .save(out, Atlantis.id("cyan_pearl_block"));

        // recipes/cyan_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:cyan_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:cyan_dye"))
                .unlockedBy("has_cyan_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("cyan_sea_glass"));

        // recipes/cyan_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:cyan_sea_glass_button"), 1)
                .requires(i("atlantis:cyan_sea_glass"))
                .unlockedBy("has_cyan_sea_glass_button", has(i("atlantis:cyan_sea_glass")))
                .save(out, Atlantis.id("cyan_sea_glass_button"));

        // recipes/cyan_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:cyan_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:cyan_sea_glass"))
                .unlockedBy("has_cyan_sea_glass_pressure_plate", has(i("atlantis:cyan_sea_glass")))
                .save(out, Atlantis.id("cyan_sea_glass_pressure_plate"));

        // recipes/cyan_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:cyan_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:cyan_sea_glass"))
                .unlockedBy("has_cyan_sea_glass_slab", has(i("atlantis:cyan_sea_glass")))
                .save(out, Atlantis.id("cyan_sea_glass_slab"));

        // recipes/cyan_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:cyan_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:cyan_sea_glass"))
                .unlockedBy("has_cyan_sea_glass_wall", has(i("atlantis:cyan_sea_glass")))
                .save(out, Atlantis.id("cyan_sea_glass_wall"));

        // recipes/cyan_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:cyan_shell_block"), 1)
                .requires(i("minecraft:cyan_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_cyan_shell_block", has(i("minecraft:cyan_dye")))
                .save(out, Atlantis.id("cyan_shell_block"));

        // recipes/dead_glowstone.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:dead_glowstone"), 3)
                .pattern("   ")
                .pattern("WSW")
                .pattern("   ")
                .define('W', i("atlantis:sunken_gravel"))
                .define('S', i("atlantis:cracked_glowstone"))
                .unlockedBy("has_dead_glowstone", has(i("atlantis:sunken_gravel")))
                .save(out, Atlantis.id("dead_glowstone"));

        // recipes/drop_of_atlantis.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:drop_of_atlantis"), 2)
                .pattern("OSO")
                .pattern("SWS")
                .pattern("OSO")
                .define('W', i("atlantis:ancient_cuprum_ingot"))
                .define('S', i("atlantis:aquamarine_gem"))
                .define('O', i("minecraft:gold_nugget"))
                .unlockedBy("has_drop_of_atlantis", has(i("atlantis:ancient_cuprum_ingot")))
                .save(out, Atlantis.id("drop_of_atlantis"));

        // recipes/fire_melon_fruit_jelly.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:fire_melon_jelly_bottle"), 3)
                .requires(i("atlantis:jellyfish_jelly_bottle"))
                .requires(i("atlantis:fire_melon_fruit"))
                .unlockedBy("has_fire_melon_fruit_jelly", has(i("atlantis:jellyfish_jelly_bottle")))
                .save(out, Atlantis.id("fire_melon_fruit_jelly"));

        // recipes/gray_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:gray_pearl_block"), 1)
                .requires(i("minecraft:gray_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_gray_pearl_block", has(i("minecraft:gray_dye")))
                .save(out, Atlantis.id("gray_pearl_block"));

        // recipes/gray_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:gray_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:gray_dye"))
                .unlockedBy("has_gray_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("gray_sea_glass"));

        // recipes/gray_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:gray_sea_glass_button"), 1)
                .requires(i("atlantis:gray_sea_glass"))
                .unlockedBy("has_gray_sea_glass_button", has(i("atlantis:gray_sea_glass")))
                .save(out, Atlantis.id("gray_sea_glass_button"));

        // recipes/gray_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:gray_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:gray_sea_glass"))
                .unlockedBy("has_gray_sea_glass_pressure_plate", has(i("atlantis:gray_sea_glass")))
                .save(out, Atlantis.id("gray_sea_glass_pressure_plate"));

        // recipes/gray_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:gray_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:gray_sea_glass"))
                .unlockedBy("has_gray_sea_glass_slab", has(i("atlantis:gray_sea_glass")))
                .save(out, Atlantis.id("gray_sea_glass_slab"));

        // recipes/gray_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:gray_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:gray_sea_glass"))
                .unlockedBy("has_gray_sea_glass_wall", has(i("atlantis:gray_sea_glass")))
                .save(out, Atlantis.id("gray_sea_glass_wall"));

        // recipes/gray_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:gray_shell_block"), 1)
                .requires(i("minecraft:gray_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_gray_shell_block", has(i("minecraft:gray_dye")))
                .save(out, Atlantis.id("gray_shell_block"));

        // recipes/green_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:green_pearl_block"), 1)
                .requires(i("minecraft:green_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_green_pearl_block", has(i("minecraft:green_dye")))
                .save(out, Atlantis.id("green_pearl_block"));

        // recipes/green_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:green_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:green_dye"))
                .unlockedBy("has_green_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("green_sea_glass"));

        // recipes/green_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:green_sea_glass_button"), 1)
                .requires(i("atlantis:green_sea_glass"))
                .unlockedBy("has_green_sea_glass_button", has(i("atlantis:green_sea_glass")))
                .save(out, Atlantis.id("green_sea_glass_button"));

        // recipes/green_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:green_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:green_sea_glass"))
                .unlockedBy("has_green_sea_glass_pressure_plate", has(i("atlantis:green_sea_glass")))
                .save(out, Atlantis.id("green_sea_glass_pressure_plate"));

        // recipes/green_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:green_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:green_sea_glass"))
                .unlockedBy("has_green_sea_glass_slab", has(i("atlantis:green_sea_glass")))
                .save(out, Atlantis.id("green_sea_glass_slab"));

        // recipes/green_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:green_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:green_sea_glass"))
                .unlockedBy("has_green_sea_glass_wall", has(i("atlantis:green_sea_glass")))
                .save(out, Atlantis.id("green_sea_glass_wall"));

        // recipes/green_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:green_shell_block"), 1)
                .requires(i("minecraft:green_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_green_shell_block", has(i("minecraft:green_dye")))
                .save(out, Atlantis.id("green_shell_block"));

        // recipes/hardened_calcite_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:hardened_calcite_block"), 4)
                .pattern("SS ")
                .pattern("SS ")
                .pattern("   ")
                .define('S', i("minecraft:calcite"))
                .unlockedBy("has_hardened_calcite_block", has(i("minecraft:calcite")))
                .save(out, Atlantis.id("hardened_calcite_block"));

        // recipes/horpen_pot.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:horpen_pot"), 1)
                .pattern("TAT")
                .pattern("NNN")
                .pattern("TAT")
                .define('N', i("atlantis:broken_shells"))
                .define('T', i("minecraft:brick"))
                .define('A', i("minecraft:clay"))
                .unlockedBy("has_horpen_pot", has(i("atlantis:broken_shells")))
                .save(out, Atlantis.id("horpen_pot"));

        // recipes/light_blue_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:light_blue_pearl_block"), 1)
                .requires(i("minecraft:light_blue_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_light_blue_pearl_block", has(i("minecraft:light_blue_dye")))
                .save(out, Atlantis.id("light_blue_pearl_block"));

        // recipes/light_blue_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:light_blue_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:light_blue_dye"))
                .unlockedBy("has_light_blue_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("light_blue_sea_glass"));

        // recipes/light_blue_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:light_blue_sea_glass_button"), 1)
                .requires(i("atlantis:light_blue_sea_glass"))
                .unlockedBy("has_light_blue_sea_glass_button", has(i("atlantis:light_blue_sea_glass")))
                .save(out, Atlantis.id("light_blue_sea_glass_button"));

        // recipes/light_blue_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:light_blue_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:light_blue_sea_glass"))
                .unlockedBy("has_light_blue_sea_glass_pressure_plate", has(i("atlantis:light_blue_sea_glass")))
                .save(out, Atlantis.id("light_blue_sea_glass_pressure_plate"));

        // recipes/light_blue_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:light_blue_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:light_blue_sea_glass"))
                .unlockedBy("has_light_blue_sea_glass_slab", has(i("atlantis:light_blue_sea_glass")))
                .save(out, Atlantis.id("light_blue_sea_glass_slab"));

        // recipes/light_blue_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:light_blue_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:light_blue_sea_glass"))
                .unlockedBy("has_light_blue_sea_glass_wall", has(i("atlantis:light_blue_sea_glass")))
                .save(out, Atlantis.id("light_blue_sea_glass_wall"));

        // recipes/light_blue_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:light_blue_shell_block"), 1)
                .requires(i("minecraft:light_blue_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_light_blue_shell_block", has(i("minecraft:light_blue_dye")))
                .save(out, Atlantis.id("light_blue_shell_block"));

        // recipes/light_gray_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:light_gray_pearl_block"), 1)
                .requires(i("minecraft:light_gray_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_light_gray_pearl_block", has(i("minecraft:light_gray_dye")))
                .save(out, Atlantis.id("light_gray_pearl_block"));

        // recipes/light_gray_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:light_gray_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:light_gray_dye"))
                .unlockedBy("has_light_gray_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("light_gray_sea_glass"));

        // recipes/light_gray_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:light_gray_sea_glass_button"), 1)
                .requires(i("atlantis:light_gray_sea_glass"))
                .unlockedBy("has_light_gray_sea_glass_button", has(i("atlantis:light_gray_sea_glass")))
                .save(out, Atlantis.id("light_gray_sea_glass_button"));

        // recipes/light_gray_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:light_gray_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:light_gray_sea_glass"))
                .unlockedBy("has_light_gray_sea_glass_pressure_plate", has(i("atlantis:light_gray_sea_glass")))
                .save(out, Atlantis.id("light_gray_sea_glass_pressure_plate"));

        // recipes/light_gray_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:light_gray_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:light_gray_sea_glass"))
                .unlockedBy("has_light_gray_sea_glass_slab", has(i("atlantis:light_gray_sea_glass")))
                .save(out, Atlantis.id("light_gray_sea_glass_slab"));

        // recipes/light_gray_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:light_gray_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:light_gray_sea_glass"))
                .unlockedBy("has_light_gray_sea_glass_wall", has(i("atlantis:light_gray_sea_glass")))
                .save(out, Atlantis.id("light_gray_sea_glass_wall"));

        // recipes/light_gray_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:light_gray_shell_block"), 1)
                .requires(i("minecraft:light_gray_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_light_gray_shell_block", has(i("minecraft:light_gray_dye")))
                .save(out, Atlantis.id("light_gray_shell_block"));

        // recipes/lime_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:lime_pearl_block"), 1)
                .requires(i("minecraft:lime_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_lime_pearl_block", has(i("minecraft:lime_dye")))
                .save(out, Atlantis.id("lime_pearl_block"));

        // recipes/lime_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:lime_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:lime_dye"))
                .unlockedBy("has_lime_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("lime_sea_glass"));

        // recipes/lime_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:lime_sea_glass_button"), 1)
                .requires(i("atlantis:lime_sea_glass"))
                .unlockedBy("has_lime_sea_glass_button", has(i("atlantis:lime_sea_glass")))
                .save(out, Atlantis.id("lime_sea_glass_button"));

        // recipes/lime_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:lime_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:lime_sea_glass"))
                .unlockedBy("has_lime_sea_glass_pressure_plate", has(i("atlantis:lime_sea_glass")))
                .save(out, Atlantis.id("lime_sea_glass_pressure_plate"));

        // recipes/lime_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:lime_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:lime_sea_glass"))
                .unlockedBy("has_lime_sea_glass_slab", has(i("atlantis:lime_sea_glass")))
                .save(out, Atlantis.id("lime_sea_glass_slab"));

        // recipes/lime_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:lime_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:lime_sea_glass"))
                .unlockedBy("has_lime_sea_glass_wall", has(i("atlantis:lime_sea_glass")))
                .save(out, Atlantis.id("lime_sea_glass_wall"));

        // recipes/lime_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:lime_shell_block"), 1)
                .requires(i("minecraft:lime_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_lime_shell_block", has(i("minecraft:lime_dye")))
                .save(out, Atlantis.id("lime_shell_block"));

        // recipes/linguistic_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:linguistic_table"), 1)
                .pattern("XYX")
                .pattern("YZY")
                .pattern("XYX")
                .define('X', i("atlantis:aquamarine_gem"))
                .define('Y', i("atlantis:nymph_planks"))
                .define('Z', i("minecraft:crafting_table"))
                .unlockedBy("has_linguistic_block", has(i("atlantis:aquamarine_gem")))
                .save(out, Atlantis.id("linguistic_block"));

        // recipes/linguistic_glyph.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:linguistic_glyph"), 2)
                .pattern("XX")
                .pattern("XX")
                .define('X', i("atlantis:luminescent_prismarine"))
                .unlockedBy("has_linguistic_glyph", has(i("atlantis:luminescent_prismarine")))
                .save(out, Atlantis.id("linguistic_glyph"));

        // recipes/linguistic_glyph_scroll.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:linguistic_glyph_scroll"), 1)
                .requires(i("minecraft:ink_sac"))
                .requires(i("minecraft:paper"))
                .requires(i("atlantis:fire_melon_spike"))
                .unlockedBy("has_linguistic_glyph_scroll", has(i("minecraft:ink_sac")))
                .save(out, Atlantis.id("linguistic_glyph_scroll"));

        // recipes/luminescent_prismarine.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:luminescent_prismarine"), 1)
                .requires(i("minecraft:prismarine"))
                .requires(i("atlantis:ocean_stone"))
                .unlockedBy("has_luminescent_prismarine", has(i("minecraft:prismarine")))
                .save(out, Atlantis.id("luminescent_prismarine"));

        // recipes/magenta_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:magenta_pearl_block"), 1)
                .requires(i("minecraft:magenta_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_magenta_pearl_block", has(i("minecraft:magenta_dye")))
                .save(out, Atlantis.id("magenta_pearl_block"));

        // recipes/magenta_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:magenta_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:magenta_dye"))
                .unlockedBy("has_magenta_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("magenta_sea_glass"));

        // recipes/magenta_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:magenta_sea_glass_button"), 1)
                .requires(i("atlantis:magenta_sea_glass"))
                .unlockedBy("has_magenta_sea_glass_button", has(i("atlantis:magenta_sea_glass")))
                .save(out, Atlantis.id("magenta_sea_glass_button"));

        // recipes/magenta_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:magenta_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:magenta_sea_glass"))
                .unlockedBy("has_magenta_sea_glass_pressure_plate", has(i("atlantis:magenta_sea_glass")))
                .save(out, Atlantis.id("magenta_sea_glass_pressure_plate"));

        // recipes/magenta_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:magenta_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:magenta_sea_glass"))
                .unlockedBy("has_magenta_sea_glass_slab", has(i("atlantis:magenta_sea_glass")))
                .save(out, Atlantis.id("magenta_sea_glass_slab"));

        // recipes/magenta_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:magenta_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:magenta_sea_glass"))
                .unlockedBy("has_magenta_sea_glass_wall", has(i("atlantis:magenta_sea_glass")))
                .save(out, Atlantis.id("magenta_sea_glass_wall"));

        // recipes/magenta_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:magenta_shell_block"), 1)
                .requires(i("minecraft:magenta_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_magenta_shell_block", has(i("minecraft:magenta_dye")))
                .save(out, Atlantis.id("magenta_shell_block"));

        // recipes/monochromatic_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:monochromatic_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:black_dye"))
                .requires(i("minecraft:black_dye"))
                .requires(i("minecraft:white_dye"))
                .requires(i("minecraft:white_dye"))
                .requires(i("minecraft:gray_dye"))
                .requires(i("minecraft:gray_dye"))
                .unlockedBy("has_monochromatic_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("monochromatic_sea_glass"));

        // recipes/monochromatic_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:monochromatic_sea_glass_button"), 1)
                .requires(i("atlantis:monochromatic_sea_glass"))
                .unlockedBy("has_monochromatic_sea_glass_button", has(i("atlantis:monochromatic_sea_glass")))
                .save(out, Atlantis.id("monochromatic_sea_glass_button"));

        // recipes/monochromatic_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:monochromatic_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:monochromatic_sea_glass"))
                .unlockedBy("has_monochromatic_sea_glass_pressure_plate", has(i("atlantis:monochromatic_sea_glass")))
                .save(out, Atlantis.id("monochromatic_sea_glass_pressure_plate"));

        // recipes/monochromatic_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:monochromatic_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:monochromatic_sea_glass"))
                .unlockedBy("has_monochromatic_sea_glass_slab", has(i("atlantis:monochromatic_sea_glass")))
                .save(out, Atlantis.id("monochromatic_sea_glass_slab"));

        // recipes/monochromatic_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:monochromatic_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:monochromatic_sea_glass"))
                .unlockedBy("has_monochromatic_sea_glass_wall", has(i("atlantis:monochromatic_sea_glass")))
                .save(out, Atlantis.id("monochromatic_sea_glass_wall"));

        // recipes/multicolor_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:multicolor_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:red_dye"))
                .requires(i("minecraft:orange_dye"))
                .requires(i("minecraft:yellow_dye"))
                .requires(i("minecraft:lime_dye"))
                .requires(i("minecraft:green_dye"))
                .requires(i("minecraft:blue_dye"))
                .requires(i("minecraft:magenta_dye"))
                .requires(i("minecraft:purple_dye"))
                .unlockedBy("has_multicolor_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("multicolor_sea_glass"));

        // recipes/multicolor_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:multicolor_sea_glass_button"), 1)
                .requires(i("atlantis:multicolor_sea_glass"))
                .unlockedBy("has_multicolor_sea_glass_button", has(i("atlantis:multicolor_sea_glass")))
                .save(out, Atlantis.id("multicolor_sea_glass_button"));

        // recipes/multicolor_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:multicolor_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:multicolor_sea_glass"))
                .unlockedBy("has_multicolor_sea_glass_pressure_plate", has(i("atlantis:multicolor_sea_glass")))
                .save(out, Atlantis.id("multicolor_sea_glass_pressure_plate"));

        // recipes/multicolor_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:multicolor_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:multicolor_sea_glass"))
                .unlockedBy("has_multicolor_sea_glass_slab", has(i("atlantis:multicolor_sea_glass")))
                .save(out, Atlantis.id("multicolor_sea_glass_slab"));

        // recipes/multicolor_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:multicolor_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:multicolor_sea_glass"))
                .unlockedBy("has_multicolor_sea_glass_wall", has(i("atlantis:multicolor_sea_glass")))
                .save(out, Atlantis.id("multicolor_sea_glass_wall"));

        // recipes/nautilus_shell.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("minecraft:nautilus_shell"), 9)
                .requires(i("atlantis:nautilus_shell_block"))
                .unlockedBy("has_nautilus_shell", has(i("atlantis:nautilus_shell_block")))
                .save(out, Atlantis.id("nautilus_shell"));

        // recipes/nautilus_shell_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:nautilus_shell_block"), 1)
                .pattern("WWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('W', i("minecraft:nautilus_shell"))
                .unlockedBy("has_nautilus_shell_block", has(i("minecraft:nautilus_shell")))
                .save(out, Atlantis.id("nautilus_shell_block"));

        // recipes/nymph_boat.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:nymph_boat"), 1)
                .pattern("   ")
                .pattern("S S")
                .pattern("SSS")
                .define('S', i("atlantis:nymph_planks"))
                .unlockedBy("has_nymph_boat", has(i("atlantis:nymph_planks")))
                .save(out, Atlantis.id("nymph_boat"));

        // recipes/nymph_log.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:nymph_planks"), 4)
                .requires(i("atlantis:nymph_log"))
                .unlockedBy("has_nymph_log", has(i("atlantis:nymph_log")))
                .save(out, Atlantis.id("nymph_log"));

        // recipes/obemo_pot.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:obemo_pot"), 1)
                .pattern("ANT")
                .pattern("TNT")
                .pattern("TNA")
                .define('N', i("atlantis:broken_shells"))
                .define('T', i("minecraft:brick"))
                .define('A', i("minecraft:clay"))
                .unlockedBy("has_obemo_pot", has(i("atlantis:broken_shells")))
                .save(out, Atlantis.id("obemo_pot"));

        // recipes/ocean_lantern.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ocean_lantern"), 2)
                .pattern("   ")
                .pattern("SWS")
                .pattern("   ")
                .define('S', i("minecraft:sea_lantern"))
                .define('W', i("minecraft:iron_ingot"))
                .unlockedBy("has_ocean_lantern", has(i("minecraft:sea_lantern")))
                .save(out, Atlantis.id("ocean_lantern"));

        // recipes/ocean_stone.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:ocean_stone"), 4)
                .pattern(" S ")
                .pattern("SBS")
                .pattern(" S ")
                .define('S', i("minecraft:stone"))
                .define('B', i("minecraft:water_bucket"))
                .unlockedBy("has_ocean_stone", has(i("minecraft:stone")))
                .save(out, Atlantis.id("ocean_stone"));

        // recipes/orange_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:orange_pearl_block"), 1)
                .requires(i("minecraft:orange_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_orange_pearl_block", has(i("minecraft:orange_dye")))
                .save(out, Atlantis.id("orange_pearl_block"));

        // recipes/orange_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:orange_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:orange_dye"))
                .unlockedBy("has_orange_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("orange_sea_glass"));

        // recipes/orange_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:orange_sea_glass_button"), 1)
                .requires(i("atlantis:orange_sea_glass"))
                .unlockedBy("has_orange_sea_glass_button", has(i("atlantis:orange_sea_glass")))
                .save(out, Atlantis.id("orange_sea_glass_button"));

        // recipes/orange_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:orange_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:orange_sea_glass"))
                .unlockedBy("has_orange_sea_glass_pressure_plate", has(i("atlantis:orange_sea_glass")))
                .save(out, Atlantis.id("orange_sea_glass_pressure_plate"));

        // recipes/orange_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:orange_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:orange_sea_glass"))
                .unlockedBy("has_orange_sea_glass_slab", has(i("atlantis:orange_sea_glass")))
                .save(out, Atlantis.id("orange_sea_glass_slab"));

        // recipes/orange_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:orange_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:orange_sea_glass"))
                .unlockedBy("has_orange_sea_glass_wall", has(i("atlantis:orange_sea_glass")))
                .save(out, Atlantis.id("orange_sea_glass_wall"));

        // recipes/orange_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:orange_shell_block"), 1)
                .requires(i("minecraft:orange_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_orange_shell_block", has(i("minecraft:orange_dye")))
                .save(out, Atlantis.id("orange_shell_block"));

        // recipes/orb_of_atlantis.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:orb_of_atlantis"), 1)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('S', i("atlantis:atlantean_crystal"))
                .define('W', i("atlantis:drop_of_atlantis"))
                .unlockedBy("has_orb_of_atlantis", has(i("atlantis:atlantean_crystal")))
                .save(out, Atlantis.id("orb_of_atlantis"));

        // Orichalcum Smithing Upgrades
        smithingTransform(out, "orichalcum_axe", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_axe", "atlantis:orichalcum_ingot", "atlantis:orichalcum_axe");
        smithingTransform(out, "orichalcum_boots", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_boots", "atlantis:orichalcum_ingot", "atlantis:orichalcum_boots");
        smithingTransform(out, "orichalcum_chestplate", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_chestplate", "atlantis:orichalcum_ingot", "atlantis:orichalcum_chestplate");
        smithingTransform(out, "orichalcum_hammer", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_hammer", "atlantis:orichalcum_ingot", "atlantis:orichalcum_hammer");
        smithingTransform(out, "orichalcum_helmet", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_helmet", "atlantis:orichalcum_ingot", "atlantis:orichalcum_helmet");
        smithingTransform(out, "orichalcum_hoe", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_hoe", "atlantis:orichalcum_ingot", "atlantis:orichalcum_hoe");
        smithingTransform(out, "orichalcum_leggings", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_leggings", "atlantis:orichalcum_ingot", "atlantis:orichalcum_leggings");
        smithingTransform(out, "orichalcum_pickaxe", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_pickaxe", "atlantis:orichalcum_ingot", "atlantis:orichalcum_pickaxe");
        smithingTransform(out, "orichalcum_shovel", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_shovel", "atlantis:orichalcum_ingot", "atlantis:orichalcum_shovel");
        smithingTransform(out, "orichalcum_sword", "atlantis:orichalcum_upgrade_smithing_template", "atlantis:aquamarine_sword", "atlantis:orichalcum_ingot", "atlantis:orichalcum_sword");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:black_sea_glass")), RecipeCategory.MISC, i("atlantis:black_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:black_sea_glass")), has(i("atlantis:black_sea_glass")))
                .save(out, Atlantis.id("black_sea_glass_slab_from_black_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:black_sea_glass")), RecipeCategory.MISC, i("atlantis:black_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:black_sea_glass")), has(i("atlantis:black_sea_glass")))
                .save(out, Atlantis.id("black_sea_glass_wall_from_black_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:blue_sea_glass")), RecipeCategory.MISC, i("atlantis:blue_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:blue_sea_glass")), has(i("atlantis:blue_sea_glass")))
                .save(out, Atlantis.id("blue_sea_glass_slab_from_blue_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:blue_sea_glass")), RecipeCategory.MISC, i("atlantis:blue_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:blue_sea_glass")), has(i("atlantis:blue_sea_glass")))
                .save(out, Atlantis.id("blue_sea_glass_wall_from_blue_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:brown_sea_glass")), RecipeCategory.MISC, i("atlantis:brown_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:brown_sea_glass")), has(i("atlantis:brown_sea_glass")))
                .save(out, Atlantis.id("brown_sea_glass_slab_from_brown_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:brown_sea_glass")), RecipeCategory.MISC, i("atlantis:brown_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:brown_sea_glass")), has(i("atlantis:brown_sea_glass")))
                .save(out, Atlantis.id("brown_sea_glass_wall_from_brown_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:cyan_sea_glass")), RecipeCategory.MISC, i("atlantis:cyan_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:cyan_sea_glass")), has(i("atlantis:cyan_sea_glass")))
                .save(out, Atlantis.id("cyan_sea_glass_slab_from_cyan_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:cyan_sea_glass")), RecipeCategory.MISC, i("atlantis:cyan_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:cyan_sea_glass")), has(i("atlantis:cyan_sea_glass")))
                .save(out, Atlantis.id("cyan_sea_glass_wall_from_cyan_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:gray_sea_glass")), RecipeCategory.MISC, i("atlantis:gray_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:gray_sea_glass")), has(i("atlantis:gray_sea_glass")))
                .save(out, Atlantis.id("gray_sea_glass_slab_from_gray_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:gray_sea_glass")), RecipeCategory.MISC, i("atlantis:gray_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:gray_sea_glass")), has(i("atlantis:gray_sea_glass")))
                .save(out, Atlantis.id("gray_sea_glass_wall_from_gray_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:green_sea_glass")), RecipeCategory.MISC, i("atlantis:green_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:green_sea_glass")), has(i("atlantis:green_sea_glass")))
                .save(out, Atlantis.id("green_sea_glass_slab_from_green_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:green_sea_glass")), RecipeCategory.MISC, i("atlantis:green_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:green_sea_glass")), has(i("atlantis:green_sea_glass")))
                .save(out, Atlantis.id("green_sea_glass_wall_from_green_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:light_blue_sea_glass")), RecipeCategory.MISC, i("atlantis:light_blue_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:light_blue_sea_glass")), has(i("atlantis:light_blue_sea_glass")))
                .save(out, Atlantis.id("light_blue_sea_glass_slab_from_light_blue_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:light_blue_sea_glass")), RecipeCategory.MISC, i("atlantis:light_blue_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:light_blue_sea_glass")), has(i("atlantis:light_blue_sea_glass")))
                .save(out, Atlantis.id("light_blue_sea_glass_wall_from_light_blue_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:light_gray_sea_glass")), RecipeCategory.MISC, i("atlantis:light_gray_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:light_gray_sea_glass")), has(i("atlantis:light_gray_sea_glass")))
                .save(out, Atlantis.id("light_gray_sea_glass_slab_from_light_gray_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:light_gray_sea_glass")), RecipeCategory.MISC, i("atlantis:light_gray_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:light_gray_sea_glass")), has(i("atlantis:light_gray_sea_glass")))
                .save(out, Atlantis.id("light_gray_sea_glass_wall_from_light_gray_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:lime_sea_glass")), RecipeCategory.MISC, i("atlantis:lime_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:lime_sea_glass")), has(i("atlantis:lime_sea_glass")))
                .save(out, Atlantis.id("lime_sea_glass_slab_from_lime_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:lime_sea_glass")), RecipeCategory.MISC, i("atlantis:lime_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:lime_sea_glass")), has(i("atlantis:lime_sea_glass")))
                .save(out, Atlantis.id("lime_sea_glass_wall_from_lime_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:magenta_sea_glass")), RecipeCategory.MISC, i("atlantis:magenta_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:magenta_sea_glass")), has(i("atlantis:magenta_sea_glass")))
                .save(out, Atlantis.id("magenta_sea_glass_slab_from_magenta_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:magenta_sea_glass")), RecipeCategory.MISC, i("atlantis:magenta_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:magenta_sea_glass")), has(i("atlantis:magenta_sea_glass")))
                .save(out, Atlantis.id("magenta_sea_glass_wall_from_magenta_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:monochromatic_sea_glass")), RecipeCategory.MISC, i("atlantis:monochromatic_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:monochromatic_sea_glass")), has(i("atlantis:monochromatic_sea_glass")))
                .save(out, Atlantis.id("monochromatic_sea_glass_slab_from_monochromatic_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:monochromatic_sea_glass")), RecipeCategory.MISC, i("atlantis:monochromatic_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:monochromatic_sea_glass")), has(i("atlantis:monochromatic_sea_glass")))
                .save(out, Atlantis.id("monochromatic_sea_glass_wall_from_monochromatic_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:multicolor_sea_glass")), RecipeCategory.MISC, i("atlantis:multicolor_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:multicolor_sea_glass")), has(i("atlantis:multicolor_sea_glass")))
                .save(out, Atlantis.id("multicolor_sea_glass_slab_from_multicolor_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:multicolor_sea_glass")), RecipeCategory.MISC, i("atlantis:multicolor_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:multicolor_sea_glass")), has(i("atlantis:multicolor_sea_glass")))
                .save(out, Atlantis.id("multicolor_sea_glass_wall_from_multicolor_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:orange_sea_glass")), RecipeCategory.MISC, i("atlantis:orange_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:orange_sea_glass")), has(i("atlantis:orange_sea_glass")))
                .save(out, Atlantis.id("orange_sea_glass_slab_from_orange_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:orange_sea_glass")), RecipeCategory.MISC, i("atlantis:orange_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:orange_sea_glass")), has(i("atlantis:orange_sea_glass")))
                .save(out, Atlantis.id("orange_sea_glass_wall_from_orange_sea_glass_stonecutting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(i("atlantis:orichalcum_blend")), RecipeCategory.MISC, i("atlantis:orichalcum_ingot"), 0.7F, 100)
                .unlockedBy(getHasName(i("atlantis:orichalcum_blend")), has(i("atlantis:orichalcum_blend")))
                .save(out, Atlantis.id("orichalcum_ignot_from_blasting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(i("atlantis:orichalcum_blend")), RecipeCategory.MISC, i("atlantis:orichalcum_ingot"), 0.7F, 200)
                .unlockedBy(getHasName(i("atlantis:orichalcum_blend")), has(i("atlantis:orichalcum_blend")))
                .save(out, Atlantis.id("orichalcum_ignot_from_smelting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(i("atlantis:orichalcum_blend")), RecipeCategory.MISC, i("atlantis:orichalcum_ingot"), 0.7F, 100)
                .unlockedBy(getHasName(i("atlantis:orichalcum_blend")), has(i("atlantis:orichalcum_blend")))
                .save(out, Atlantis.id("orichalcum_ingot_from_blasting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(i("atlantis:orichalcum_blend")), RecipeCategory.MISC, i("atlantis:orichalcum_ingot"), 0.7F, 200)
                .unlockedBy(getHasName(i("atlantis:orichalcum_blend")), has(i("atlantis:orichalcum_blend")))
                .save(out, Atlantis.id("orichalcum_ingot_from_smelting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:pink_sea_glass")), RecipeCategory.MISC, i("atlantis:pink_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:pink_sea_glass")), has(i("atlantis:pink_sea_glass")))
                .save(out, Atlantis.id("pink_sea_glass_slab_from_pink_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:pink_sea_glass")), RecipeCategory.MISC, i("atlantis:pink_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:pink_sea_glass")), has(i("atlantis:pink_sea_glass")))
                .save(out, Atlantis.id("pink_sea_glass_wall_from_pink_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:purple_sea_glass")), RecipeCategory.MISC, i("atlantis:purple_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:purple_sea_glass")), has(i("atlantis:purple_sea_glass")))
                .save(out, Atlantis.id("purple_sea_glass_slab_from_purple_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:purple_sea_glass")), RecipeCategory.MISC, i("atlantis:purple_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:purple_sea_glass")), has(i("atlantis:purple_sea_glass")))
                .save(out, Atlantis.id("purple_sea_glass_wall_from_purple_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:red_sea_glass")), RecipeCategory.MISC, i("atlantis:red_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:red_sea_glass")), has(i("atlantis:red_sea_glass")))
                .save(out, Atlantis.id("red_sea_glass_slab_from_red_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:red_sea_glass")), RecipeCategory.MISC, i("atlantis:red_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:red_sea_glass")), has(i("atlantis:red_sea_glass")))
                .save(out, Atlantis.id("red_sea_glass_wall_from_red_sea_glass_stonecutting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(i("atlantis:sunken_gravel")), RecipeCategory.BUILDING_BLOCKS, i("atlantis:sea_glass"), 0.5F, 100)
                .unlockedBy(getHasName(i("atlantis:sunken_gravel")), has(i("atlantis:sunken_gravel")))
                .save(out, Atlantis.id("sea_glass_from_blasting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(i("atlantis:sunken_gravel")), RecipeCategory.BUILDING_BLOCKS, i("atlantis:sea_glass"), 0.5F, 50)
                .unlockedBy(getHasName(i("atlantis:sunken_gravel")), has(i("atlantis:sunken_gravel")))
                .save(out, Atlantis.id("sea_glass_from_smelting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:sea_glass")), RecipeCategory.MISC, i("atlantis:sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:sea_glass")), has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("sea_glass_slab_from_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:sea_glass")), RecipeCategory.MISC, i("atlantis:sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:sea_glass")), has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("sea_glass_wall_from_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:white_sea_glass")), RecipeCategory.MISC, i("atlantis:white_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:white_sea_glass")), has(i("atlantis:white_sea_glass")))
                .save(out, Atlantis.id("white_sea_glass_slab_from_white_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:white_sea_glass")), RecipeCategory.MISC, i("atlantis:white_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:white_sea_glass")), has(i("atlantis:white_sea_glass")))
                .save(out, Atlantis.id("white_sea_glass_wall_from_white_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:yellow_sea_glass")), RecipeCategory.MISC, i("atlantis:yellow_sea_glass_slab"), 2)
                .unlockedBy(getHasName(i("atlantis:yellow_sea_glass")), has(i("atlantis:yellow_sea_glass")))
                .save(out, Atlantis.id("yellow_sea_glass_slab_from_yellow_sea_glass_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(i("atlantis:yellow_sea_glass")), RecipeCategory.MISC, i("atlantis:yellow_sea_glass_wall"), 1)
                .unlockedBy(getHasName(i("atlantis:yellow_sea_glass")), has(i("atlantis:yellow_sea_glass")))
                .save(out, Atlantis.id("yellow_sea_glass_wall_from_yellow_sea_glass_stonecutting"));

        // Shrimp Cooking
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(i("atlantis:shrimp")), RecipeCategory.FOOD, i("atlantis:cooked_shrimp"), 0.5f, 300)
                .unlockedBy(getHasName(i("atlantis:shrimp")), has(i("atlantis:shrimp")))
                .save(out, Atlantis.id("shrimp_from_campfire_cooking"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(i("atlantis:shrimp")), RecipeCategory.FOOD, i("atlantis:cooked_shrimp"), 0.5f, 50)
                .unlockedBy(getHasName(i("atlantis:shrimp")), has(i("atlantis:shrimp")))
                .save(out, Atlantis.id("shrimp_from_smoking"));

        // recipes/orichalcum_blend.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:orichalcum_blend"), 3)
                .requires(i("minecraft:raw_copper"))
                .requires(i("minecraft:raw_copper"))
                .requires(i("minecraft:raw_copper"))
                .requires(i("minecraft:raw_copper"))
                .requires(i("minecraft:raw_copper"))
                .requires(i("minecraft:raw_copper"))
                .requires(i("minecraft:raw_gold"))
                .requires(i("atlantis:aquamarine_gem"))
                .unlockedBy("has_orichalcum_blend", has(i("minecraft:raw_copper")))
                .save(out, Atlantis.id("orichalcum_blend"));

        // recipes/orichalcum_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:orichalcum_block"), 1)
                .pattern("XX")
                .pattern("XX")
                .define('X', i("atlantis:orichalcum_ingot"))
                .unlockedBy("has_orichalcum_block", has(i("atlantis:orichalcum_ingot")))
                .save(out, Atlantis.id("orichalcum_block"));

        // recipes/orichalcum_upgrade_smithing_template.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:orichalcum_upgrade_smithing_template"), 2)
                .pattern("SCS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', i("atlantis:aquamarine_gem"))
                .define('W', i("atlantis:orichalcum_ingot"))
                .define('C', i("atlantis:orichalcum_upgrade_smithing_template"))
                .unlockedBy("has_orichalcum_upgrade_smithing_template", has(i("atlantis:aquamarine_gem")))
                .save(out, Atlantis.id("orichalcum_upgrade_smithing_template"));

        // recipes/palm_boat.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:palm_boat"), 1)
                .pattern("   ")
                .pattern("S S")
                .pattern("SSS")
                .define('S', i("atlantis:palm_planks"))
                .unlockedBy("has_palm_boat", has(i("atlantis:palm_planks")))
                .save(out, Atlantis.id("palm_boat"));

        // recipes/palm_log.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:palm_planks"), 4)
                .requires(i("atlantis:palm_log"))
                .unlockedBy("has_palm_log", has(i("atlantis:palm_log")))
                .save(out, Atlantis.id("palm_log"));

        // recipes/palm_sign.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:palm_sign"), 2)
                .pattern("SSS")
                .pattern("SSS")
                .pattern(" W ")
                .define('W', i("minecraft:stick"))
                .define('S', i("atlantis:palm_planks"))
                .unlockedBy("has_palm_sign", has(i("minecraft:stick")))
                .save(out, Atlantis.id("palm_sign"));

        // recipes/pink_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:pink_pearl_block"), 1)
                .requires(i("minecraft:pink_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_pink_pearl_block", has(i("minecraft:pink_dye")))
                .save(out, Atlantis.id("pink_pearl_block"));

        // recipes/pink_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:pink_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:pink_dye"))
                .unlockedBy("has_pink_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("pink_sea_glass"));

        // recipes/pink_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:pink_sea_glass_button"), 1)
                .requires(i("atlantis:pink_sea_glass"))
                .unlockedBy("has_pink_sea_glass_button", has(i("atlantis:pink_sea_glass")))
                .save(out, Atlantis.id("pink_sea_glass_button"));

        // recipes/pink_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:pink_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:pink_sea_glass"))
                .unlockedBy("has_pink_sea_glass_pressure_plate", has(i("atlantis:pink_sea_glass")))
                .save(out, Atlantis.id("pink_sea_glass_pressure_plate"));

        // recipes/pink_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:pink_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:pink_sea_glass"))
                .unlockedBy("has_pink_sea_glass_slab", has(i("atlantis:pink_sea_glass")))
                .save(out, Atlantis.id("pink_sea_glass_slab"));

        // recipes/pink_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:pink_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:pink_sea_glass"))
                .unlockedBy("has_pink_sea_glass_wall", has(i("atlantis:pink_sea_glass")))
                .save(out, Atlantis.id("pink_sea_glass_wall"));

        // recipes/pink_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:pink_shell_block"), 1)
                .requires(i("minecraft:pink_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_pink_shell_block", has(i("minecraft:pink_dye")))
                .save(out, Atlantis.id("pink_shell_block"));

        // recipes/purple_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:purple_pearl_block"), 1)
                .requires(i("minecraft:purple_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_purple_pearl_block", has(i("minecraft:purple_dye")))
                .save(out, Atlantis.id("purple_pearl_block"));

        // recipes/purple_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:purple_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:purple_dye"))
                .unlockedBy("has_purple_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("purple_sea_glass"));

        // recipes/purple_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:purple_sea_glass_button"), 1)
                .requires(i("atlantis:purple_sea_glass"))
                .unlockedBy("has_purple_sea_glass_button", has(i("atlantis:purple_sea_glass")))
                .save(out, Atlantis.id("purple_sea_glass_button"));

        // recipes/purple_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:purple_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:purple_sea_glass"))
                .unlockedBy("has_purple_sea_glass_pressure_plate", has(i("atlantis:purple_sea_glass")))
                .save(out, Atlantis.id("purple_sea_glass_pressure_plate"));

        // recipes/purple_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:purple_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:purple_sea_glass"))
                .unlockedBy("has_purple_sea_glass_slab", has(i("atlantis:purple_sea_glass")))
                .save(out, Atlantis.id("purple_sea_glass_slab"));

        // recipes/purple_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:purple_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:purple_sea_glass"))
                .unlockedBy("has_purple_sea_glass_wall", has(i("atlantis:purple_sea_glass")))
                .save(out, Atlantis.id("purple_sea_glass_wall"));

        // recipes/purple_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:purple_shell_block"), 1)
                .requires(i("minecraft:purple_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_purple_shell_block", has(i("minecraft:purple_dye")))
                .save(out, Atlantis.id("purple_shell_block"));

        // recipes/red_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:red_pearl_block"), 1)
                .requires(i("minecraft:red_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_red_pearl_block", has(i("minecraft:red_dye")))
                .save(out, Atlantis.id("red_pearl_block"));

        // recipes/red_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:red_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:red_dye"))
                .unlockedBy("has_red_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("red_sea_glass"));

        // recipes/red_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:red_sea_glass_button"), 1)
                .requires(i("atlantis:red_sea_glass"))
                .unlockedBy("has_red_sea_glass_button", has(i("atlantis:red_sea_glass")))
                .save(out, Atlantis.id("red_sea_glass_button"));

        // recipes/red_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:red_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:red_sea_glass"))
                .unlockedBy("has_red_sea_glass_pressure_plate", has(i("atlantis:red_sea_glass")))
                .save(out, Atlantis.id("red_sea_glass_pressure_plate"));

        // recipes/red_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:red_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:red_sea_glass"))
                .unlockedBy("has_red_sea_glass_slab", has(i("atlantis:red_sea_glass")))
                .save(out, Atlantis.id("red_sea_glass_slab"));

        // recipes/red_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:red_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:red_sea_glass"))
                .unlockedBy("has_red_sea_glass_wall", has(i("atlantis:red_sea_glass")))
                .save(out, Atlantis.id("red_sea_glass_wall"));

        // recipes/red_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:red_shell_block"), 1)
                .requires(i("minecraft:red_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_red_shell_block", has(i("minecraft:red_dye")))
                .save(out, Atlantis.id("red_shell_block"));

        // recipes/salt_from_smelting.json
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(i("atlantis:salty_seawater_bucket")), RecipeCategory.MISC, i("atlantis:seasalt"), 0.25F, 150)
                .unlockedBy("has_salt_from_smelting", has(i("atlantis:salty_seawater_bucket")))
                .save(out, Atlantis.id("salt_from_smelting"));

        // recipes/satire_lantern.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:satire_lantern"), 1)
                .pattern("A")
                .pattern("B")
                .define('A', i("atlantis:carved_coconut"))
                .define('B', i("minecraft:torch"))
                .unlockedBy("has_satire_lantern", has(i("atlantis:carved_coconut")))
                .save(out, Atlantis.id("satire_lantern"));

        // recipes/seasalt_chunk.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:seasalt_chunk"), 1)
                .pattern("SS ")
                .pattern("SS ")
                .pattern("   ")
                .define('S', i("atlantis:seasalt"))
                .unlockedBy("has_seasalt_chunk", has(i("atlantis:seasalt")))
                .save(out, Atlantis.id("seasalt_chunk"));

        // recipes/sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:sea_glass_button"), 1)
                .requires(i("atlantis:sea_glass"))
                .unlockedBy("has_sea_glass_button", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("sea_glass_button"));

        // recipes/sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:sea_glass"))
                .unlockedBy("has_sea_glass_pressure_plate", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("sea_glass_pressure_plate"));

        // recipes/sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:sea_glass"))
                .unlockedBy("has_sea_glass_slab", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("sea_glass_slab"));

        // recipes/sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:sea_glass"))
                .unlockedBy("has_sea_glass_wall", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("sea_glass_wall"));

        // recipes/shrimp_from_smelting.json
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(i("atlantis:shrimp")), RecipeCategory.MISC, i("atlantis:cooked_shrimp"), 0.5F, 200)
                .unlockedBy("has_shrimp_from_smelting", has(i("atlantis:shrimp")))
                .save(out, Atlantis.id("shrimp_from_smelting"));

        // recipes/snown_pot.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:snown_pot"), 1)
                .pattern("TNA")
                .pattern("NNN")
                .pattern("TNA")
                .define('N', i("atlantis:broken_shells"))
                .define('T', i("minecraft:brick"))
                .define('A', i("minecraft:clay"))
                .unlockedBy("has_snown_pot", has(i("atlantis:broken_shells")))
                .save(out, Atlantis.id("snown_pot"));

        // recipes/sodium_bomb.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:sodium_bomb"), 1)
                .pattern(" S ")
                .pattern("STS")
                .pattern(" S ")
                .define('S', i("atlantis:sodium_nugget"))
                .define('T', i("minecraft:tnt"))
                .unlockedBy("has_sodium_bomb", has(i("atlantis:sodium_nugget")))
                .save(out, Atlantis.id("sodium_bomb"));

        // recipes/stripped_atlantean_log.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:nymph_planks"), 4)
                .requires(i("atlantis:stripped_nymph_log"))
                .unlockedBy("has_stripped_atlantean_log", has(i("atlantis:stripped_nymph_log")))
                .save(out, Atlantis.id("stripped_atlantean_log"));

        // recipes/stripped_palm_log.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:palm_planks"), 4)
                .requires(i("atlantis:stripped_palm_log"))
                .unlockedBy("has_stripped_palm_log", has(i("atlantis:stripped_palm_log")))
                .save(out, Atlantis.id("stripped_palm_log"));

        // recipes/submarine.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:submarine"), 1)
                .pattern(" IC")
                .pattern("IIC")
                .pattern("CCV")
                .define('C', i("minecraft:raw_iron_block"))
                .define('I', i("minecraft:glass"))
                .define('V', i("atlantis:orb_of_atlantis"))
                .unlockedBy("has_submarine", has(i("minecraft:raw_iron_block")))
                .save(out, Atlantis.id("submarine"));

        // recipes/sunken_gravel.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:sunken_gravel"), 1)
                .requires(i("atlantis:ocean_stone"))
                .requires(i("minecraft:gravel"))
                .unlockedBy("has_sunken_gravel", has(i("atlantis:ocean_stone")))
                .save(out, Atlantis.id("sunken_gravel"));

        // recipes/surge_lantern.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:surge_lantern"), 2)
                .pattern("   ")
                .pattern("WSW")
                .pattern("   ")
                .define('W', i("atlantis:ocean_lantern"))
                .define('S', i("atlantis:ocean_stone"))
                .unlockedBy("has_surge_lantern", has(i("atlantis:ocean_lantern")))
                .save(out, Atlantis.id("surge_lantern"));

        // recipes/toper_pot.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:toper_pot"), 1)
                .pattern("ANA")
                .pattern("TNT")
                .pattern("TNT")
                .define('N', i("atlantis:broken_shells"))
                .define('T', i("minecraft:brick"))
                .define('A', i("minecraft:clay"))
                .unlockedBy("has_toper_pot", has(i("atlantis:broken_shells")))
                .save(out, Atlantis.id("toper_pot"));

        // recipes/tuben_pot.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:tuben_pot"), 1)
                .pattern("TNT")
                .pattern("ANA")
                .pattern("TNT")
                .define('N', i("atlantis:broken_shells"))
                .define('T', i("minecraft:brick"))
                .define('A', i("minecraft:clay"))
                .unlockedBy("has_tuben_pot", has(i("atlantis:broken_shells")))
                .save(out, Atlantis.id("tuben_pot"));

        // recipes/waterfall_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:waterfall_block"), 3)
                .pattern("CWC")
                .pattern("CWC")
                .pattern("CCC")
                .define('C', i("atlantis:atlantean_crystal"))
                .define('W', i("atlantis:ocean_stone"))
                .unlockedBy("has_waterfall_block", has(i("atlantis:atlantean_crystal")))
                .save(out, Atlantis.id("waterfall_block"));

        // recipes/water_pill.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:water_pill"), 1)
                .pattern("WSW")
                .pattern("SWS")
                .pattern("WSW")
                .define('W', i("minecraft:sugar"))
                .define('S', i("minecraft:kelp"))
                .unlockedBy("has_water_pill", has(i("minecraft:sugar")))
                .save(out, Atlantis.id("water_pill"));

        // recipes/wave_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:wave_block"), 3)
                .pattern("CWC")
                .pattern("WCW")
                .pattern("CWC")
                .define('C', i("atlantis:atlantean_crystal"))
                .define('W', i("atlantis:ocean_stone"))
                .unlockedBy("has_wave_block", has(i("atlantis:atlantean_crystal")))
                .save(out, Atlantis.id("wave_block"));

        // recipes/white_colored_shell_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:white_shell_block"), 2)
                .pattern(" O ")
                .pattern("SWS")
                .pattern(" O ")
                .define('S', i("minecraft:white_dye"))
                .define('W', i("minecraft:stone"))
                .define('O', i("atlantis:ocean_stone"))
                .unlockedBy("has_white_colored_shell_block", has(i("minecraft:white_dye")))
                .save(out, Atlantis.id("white_colored_shell_block"));

        // recipes/white_pearl_block.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:white_pearl_block"), 2)
                .pattern(" S ")
                .pattern("SWS")
                .pattern(" S ")
                .define('S', i("minecraft:white_dye"))
                .define('W', i("atlantis:hardened_calcite_block"))
                .unlockedBy("has_white_pearl_block", has(i("minecraft:white_dye")))
                .save(out, Atlantis.id("white_pearl_block"));

        // recipes/white_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:white_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:white_dye"))
                .unlockedBy("has_white_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("white_sea_glass"));

        // recipes/white_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:white_sea_glass_button"), 1)
                .requires(i("atlantis:white_sea_glass"))
                .unlockedBy("has_white_sea_glass_button", has(i("atlantis:white_sea_glass")))
                .save(out, Atlantis.id("white_sea_glass_button"));

        // recipes/white_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:white_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:white_sea_glass"))
                .unlockedBy("has_white_sea_glass_pressure_plate", has(i("atlantis:white_sea_glass")))
                .save(out, Atlantis.id("white_sea_glass_pressure_plate"));

        // recipes/white_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:white_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:white_sea_glass"))
                .unlockedBy("has_white_sea_glass_slab", has(i("atlantis:white_sea_glass")))
                .save(out, Atlantis.id("white_sea_glass_slab"));

        // recipes/white_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:white_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:white_sea_glass"))
                .unlockedBy("has_white_sea_glass_wall", has(i("atlantis:white_sea_glass")))
                .save(out, Atlantis.id("white_sea_glass_wall"));

        // recipes/writing_table.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:writing_table"), 1)
                .pattern("XXX")
                .pattern("YZY")
                .pattern("XXX")
                .define('X', i("atlantis:fire_melon_spike"))
                .define('Y', i("minecraft:book"))
                .define('Z', i("minecraft:crafting_table"))
                .unlockedBy("has_writing_table", has(i("atlantis:fire_melon_spike")))
                .save(out, Atlantis.id("writing_table"));

        // recipes/yellow_pearl_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:yellow_pearl_block"), 1)
                .requires(i("minecraft:yellow_dye"))
                .requires(i("atlantis:white_pearl_block"))
                .unlockedBy("has_yellow_pearl_block", has(i("minecraft:yellow_dye")))
                .save(out, Atlantis.id("yellow_pearl_block"));

        // recipes/yellow_sea_glass.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:yellow_sea_glass"), 1)
                .requires(i("atlantis:sea_glass"))
                .requires(i("minecraft:yellow_dye"))
                .unlockedBy("has_yellow_sea_glass", has(i("atlantis:sea_glass")))
                .save(out, Atlantis.id("yellow_sea_glass"));

        // recipes/yellow_sea_glass_button.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:yellow_sea_glass_button"), 1)
                .requires(i("atlantis:yellow_sea_glass"))
                .unlockedBy("has_yellow_sea_glass_button", has(i("atlantis:yellow_sea_glass")))
                .save(out, Atlantis.id("yellow_sea_glass_button"));

        // recipes/yellow_sea_glass_pressure_plate.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:yellow_sea_glass_pressure_plate"), 1)
                .pattern("##")
                .define('#', i("atlantis:yellow_sea_glass"))
                .unlockedBy("has_yellow_sea_glass_pressure_plate", has(i("atlantis:yellow_sea_glass")))
                .save(out, Atlantis.id("yellow_sea_glass_pressure_plate"));

        // recipes/yellow_sea_glass_slab.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:yellow_sea_glass_slab"), 6)
                .pattern("###")
                .define('#', i("atlantis:yellow_sea_glass"))
                .unlockedBy("has_yellow_sea_glass_slab", has(i("atlantis:yellow_sea_glass")))
                .save(out, Atlantis.id("yellow_sea_glass_slab"));

        // recipes/yellow_sea_glass_wall.json
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i("atlantis:yellow_sea_glass_wall"), 6)
                .pattern("###")
                .pattern("###")
                .define('#', i("atlantis:yellow_sea_glass"))
                .unlockedBy("has_yellow_sea_glass_wall", has(i("atlantis:yellow_sea_glass")))
                .save(out, Atlantis.id("yellow_sea_glass_wall"));

        // recipes/yellow_shell_block.json
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i("atlantis:yellow_shell_block"), 1)
                .requires(i("minecraft:yellow_dye"))
                .requires(i("atlantis:white_shell_block"))
                .unlockedBy("has_yellow_shell_block", has(i("minecraft:yellow_dye")))
                .save(out, Atlantis.id("yellow_shell_block"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemInit.RAW_ANCIENT_CUPRUM.get()), RecipeCategory.BUILDING_BLOCKS, ItemInit.ANCIENT_CUPRUM_INGOT.get(), 0.7F, 200)
                .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_CUPRUM.get()), has(ItemInit.RAW_ANCIENT_CUPRUM.get()))
                .save(out, Atlantis.id(ItemInit.ANCIENT_CUPRUM_INGOT.get().getDescriptionId().replace("item.atlantis.", "") + "_smelting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemInit.RAW_ANCIENT_CUPRUM.get()), RecipeCategory.BUILDING_BLOCKS, ItemInit.ANCIENT_CUPRUM_INGOT.get(), 0.7F, 100)
                .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_CUPRUM.get()), has(ItemInit.RAW_ANCIENT_CUPRUM.get()))
                .save(out, Atlantis.id(ItemInit.ANCIENT_CUPRUM_INGOT.get().getDescriptionId().replace("item.atlantis.", "") + "_blasting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get(), 1)
                .pattern("###").pattern("###").pattern("###")
                .define('#', ItemInit.RAW_ANCIENT_CUPRUM.get())
                .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_CUPRUM.get()), has(ItemInit.RAW_ANCIENT_CUPRUM.get()))
                .save(out, Atlantis.id(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
    }

    private static void registerWood(com.mystic.atlantis.blocks.BlockType blockType, RecipeOutput out) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, blockType.button().get())
                .requires(blockType.block().get())
                .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                .save(out, Atlantis.id(blockType.button().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.door().get(), 6)
                .pattern("## ").pattern("## ").pattern("## ")
                .define('#', blockType.block().get())
                .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                .save(out, Atlantis.id(blockType.door().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.trapDoor().get(), 6)
                .pattern("###").pattern("###")
                .define('#', blockType.block().get())
                .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                .save(out, Atlantis.id(blockType.trapDoor().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.slab().get(), 6)
                .pattern("###")
                .define('#', blockType.block().get())
                .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                .save(out, Atlantis.id(blockType.slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.stairs().get(), 4)
                .pattern("#  ").pattern("## ").pattern("###")
                .define('#', blockType.block().get())
                .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                .save(out, Atlantis.id(blockType.stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.fence().get(), 3)
                .pattern("#W#").pattern("#W#")
                .define('W', Items.STICK)
                .define('#', blockType.block().get())
                .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                .save(out, Atlantis.id(blockType.fence().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.fenceGate().get(), 1)
                .pattern("#W#").pattern("#W#")
                .define('W', Items.STICK)
                .define('#', blockType.block().get())
                .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                .save(out, Atlantis.id(blockType.fenceGate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, blockType.pressurePlate().get(), 1)
                .pattern("## ")
                .define('#', blockType.block().get())
                .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                .save(out, Atlantis.id(blockType.pressurePlate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
    }

    private static void registerSeaGlass(RecipeOutput out) {
        for (DyeColor color : DyeColor.values()) {
            var blockType = BlockInit.SEA_GLASS_PATTERNS.get(color);
            var base = SEA_GLASS_LIST.get(color);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, base.stairs().get(), 4)
                    .pattern("#  ")
                    .pattern("## ")
                    .pattern("###")
                    .define('#', base.block().get())
                    .unlockedBy(getHasName(base.block().get().asItem()), has(base.block().get().asItem()))
                    .save(out, Atlantis.id(base.stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));


            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.block().get(), 4)
                    .pattern("## ").pattern("## ").pattern("   ")
                    .define('#', base.block().get())
                    .unlockedBy(getHasName(base.block().get().asItem()), has(base.block().get().asItem()))
                    .save(out, Atlantis.id(blockType.block().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, blockType.button().get())
                    .requires(blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(out, Atlantis.id(blockType.button().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.slab().get(), 6)
                    .pattern("###")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(out, Atlantis.id(blockType.slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.stairs().get(), 4)
                    .pattern("#  ").pattern("## ").pattern("###")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(out, Atlantis.id(blockType.stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.wall().get(), 6)
                    .pattern("###").pattern("###")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(out, Atlantis.id(blockType.wall().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, blockType.pressurePlate().get(), 1)
                    .pattern("## ")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(out, Atlantis.id(blockType.pressurePlate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
        }
    }

    private static void registerGroup(TrailsGroup group, RecipeOutput out) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.bulb().get(), 1)
                .pattern(" # ").pattern("#X#").pattern(" R ")
                .define('#', group.block().get())
                .define('X', Items.BLAZE_ROD)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.bulb().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.block().get(), 1)
                .pattern("###").pattern("###").pattern("###")
                .define('#', group.block().get())
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut().get(), 4)
                .pattern("##").pattern("##")
                .define('#', group.block().get())
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.cut().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut_slab().get(), 6)
                .pattern("###")
                .define('#', group.cut().get())
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.cut_slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut_stairs().get(), 4)
                .pattern("#  ").pattern("## ").pattern("###")
                .define('#', group.cut().get())
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.cut_stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.chiseled().get(), 1)
                .pattern(" # ").pattern(" # ").pattern("   ")
                .define('#', group.cut_slab().get())
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.chiseled().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.door().get(), 3)
                .pattern("##").pattern("##").pattern("##")
                .define('#', ItemInit.ANCIENT_CUPRUM_INGOT.get())
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.door().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.trapdoor().get(), 2)
                .pattern("###").pattern("###")
                .define('#', ItemInit.ANCIENT_CUPRUM_INGOT.get())
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.trapdoor().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.grate().get(), 3)
                .pattern(" # ").pattern("# #").pattern(" # ")
                .define('#', group.block().get())
                .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                .save(out, Atlantis.id(group.grate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_block().get(), 1)
                .requires(group.block().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                .save(out, Atlantis.id(group.waxed_block().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut().get(), 1)
                .requires(group.cut().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.cut().get()), has(group.cut().get()))
                .save(out, Atlantis.id(group.waxed_cut().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_chiseled().get(), 1)
                .requires(group.chiseled().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.chiseled().get()), has(group.chiseled().get()))
                .save(out, Atlantis.id(group.waxed_chiseled().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut_slab().get(), 1)
                .requires(group.cut_slab().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.cut_slab().get()), has(group.cut_slab().get()))
                .save(out, Atlantis.id(group.waxed_cut_slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut_stairs().get(), 1)
                .requires(group.cut_stairs().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.cut_stairs().get()), has(group.cut_stairs().get()))
                .save(out, Atlantis.id(group.waxed_cut_stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_door().get(), 1)
                .requires(group.door().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.door().get()), has(group.door().get()))
                .save(out, Atlantis.id(group.waxed_door().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_trapdoor().get(), 1)
                .requires(group.trapdoor().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.trapdoor().get()), has(group.trapdoor().get()))
                .save(out, Atlantis.id(group.waxed_trapdoor().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_grate().get(), 1)
                .requires(group.grate().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.grate().get()), has(group.grate().get()))
                .save(out, Atlantis.id(group.waxed_grate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_bulb().get(), 1)
                .requires(group.bulb().get()).requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(group.bulb().get()), has(group.bulb().get()))
                .save(out, Atlantis.id(group.waxed_bulb().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
    }

    private static void glyphScroll(RecipeOutput out, ItemLike result, Ingredient material) {
        writing(material, RecipeCategory.MISC, result, 1)
                .unlockedBy(getHasName(ItemInit.LINGUISTIC_GLYPH_SCROLL.get()), has(ItemInit.LINGUISTIC_GLYPH_SCROLL.get()))
                .save(out, getConversionRecipeName(result, ItemInit.LINGUISTIC_GLYPH_SCROLL.get()) + "_writing");
    }

    public static SingleItemRecipeBuilder writing(Ingredient ingredient, RecipeCategory category, ItemLike result, int count) {
        return new SingleItemRecipeBuilder(category, WritingRecipe::new, ingredient, result, count);
    }

    private static ItemLike i(String id) {
        var lookupRegister = lookup.getNow(null);
        if (lookupRegister == null) {
            throw new IllegalStateException("HolderLookup not ready yet");
        }

        var rl = ResourceLocation.parse(id);
        var key = net.minecraft.core.registries.Registries.ITEM; // The item registry
        var registryLookup = lookupRegister.lookupOrThrow(key);
        var itemKey = net.minecraft.resources.ResourceKey.create(key, rl);

        return registryLookup.getOrThrow(itemKey).value();
    }

    private static void smithingTransform(RecipeOutput out, String name, String template, String base, String addition, String result) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(i(template)),
                        Ingredient.of(i(base)),
                        Ingredient.of(i(addition)),
                        RecipeCategory.COMBAT,
                        i(result).asItem()
                ).unlocks(getHasName(i(base)), has(i(base)))
                .save(out, Atlantis.id(name + "_smithing"));
    }
}
