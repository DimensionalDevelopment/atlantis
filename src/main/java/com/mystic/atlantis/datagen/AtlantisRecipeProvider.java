package com.mystic.atlantis.datagen;

import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.base.LinguisticGlyph;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.init.RecipesInit;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AtlantisRecipeProvider extends RecipeProvider {
    public AtlantisRecipeProvider(PackOutput generator) {
        super(generator);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.LINGUISTIC_GLYPH_SCROLL.get(), 1).requires(Items.INK_SAC).requires(Items.PAPER).requires(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()).unlockedBy("has_fire_melon_spike", RecipeProvider.has(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.getLinguisticBlock(LinguisticGlyph.BLANK, null).get(), 2).pattern("XX").pattern("XX").define('X', BlockInit.ATLANTEAN_PRISMARINE.get()).unlockedBy("has_atlantean_prismarine", RecipeProvider.has(BlockInit.ATLANTEAN_PRISMARINE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.WRITING_BLOCK.get())
                .pattern("XXX")
                .pattern("YZY")
                .pattern("XXX")
                .define('X', ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get())
                .define('Y', Items.BOOK)
                .define('Z', Blocks.CRAFTING_TABLE)
                .unlockedBy("has_fire_melon_spike", RecipeProvider.has(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.LINGUISTIC_BLOCK.get())
                .pattern("XYX")
                .pattern("YZY")
                .pattern("XYX")
                .define('X', ItemInit.AQUAMARINE_GEM.get())
                .define('Y', BlockInit.ATLANTEAN_PLANKS.get())
                .define('Z', Blocks.CRAFTING_TABLE)
                .unlockedBy("has_atlantean_planks", RecipeProvider.has(BlockInit.ATLANTEAN_PLANKS.get()))
                .save(consumer);
        Ingredient ingredient = Ingredient.of(Stream.of(
                        LinguisticGlyph.values())
                .map(ItemInit::getScroll)
                .map(RegistryObject::get)
                .map(ItemStack::new));

        for(LinguisticGlyph glyph : LinguisticGlyph.values()) {
            writing(ingredient, ItemInit.getScroll(glyph).get())
//                                .group("glyph_scroll")
                    .unlockedBy("has_fire_melon_spike", RecipeProvider.has(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()))
                    .save(consumer, ItemInit.getScroll(glyph).getId().toString() + "_writing");
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.ORICHALCUM_BLEND.get(), 3)
                .requires(Items.RAW_COPPER, 6)
                .requires(Items.RAW_GOLD)
                .requires(ItemInit.AQUAMARINE_GEM.get())
                .unlockedBy("has_aquamarine_gem", RecipeProvider.has(ItemInit.AQUAMARINE_GEM.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.ORICHALCUM_BLOCK.get())
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemInit.ORICHALCUM_IGNOT.get())
                .unlockedBy("has_orichalcum_ignot", RecipeProvider.has(ItemInit.ORICHALCUM_IGNOT.get()))
                .save(consumer);

        orichalcumUpgrade(ItemInit.AQUAMARINE_HELMET, ItemInit.ORICHALCUM_HELMET, consumer);
        orichalcumUpgrade(ItemInit.AQUAMARINE_CHESTPLATE, ItemInit.ORICHALCUM_CHESTPLATE, consumer);
        orichalcumUpgrade(ItemInit.AQUAMARINE_LEGGINGS, ItemInit.ORICHALCUM_LEGGINGS, consumer);
        orichalcumUpgrade(ItemInit.AQUAMARINE_BOOTS, ItemInit.ORICHALCUM_BOOTS, consumer);
        orichalcumUpgrade(ItemInit.AXE_AQUAMARINE, ItemInit.ORICHALCUM_AXE, consumer);
        orichalcumUpgrade(ItemInit.PICKAXE_AQUAMARINE, ItemInit.ORICHALCUM_PICKAXE, consumer);
        orichalcumUpgrade(ItemInit.HOE_AQUAMARINE, ItemInit.ORICHALCUM_HOE, consumer);
        orichalcumUpgrade(ItemInit.SHOVEL_AQUAMARINE, ItemInit.ORICHALCUM_SHOVEL, consumer);
        orichalcumUpgrade(ItemInit.SWORD_AQUAMARINE, ItemInit.ORICHALCUM_SWORD, consumer);

        SimpleCookingRecipeBuilder.smelting(ItemInit.ORICHALCUM_BLEND.lazyMap(Ingredient::of).get(), RecipeCategory.MISC, ItemInit.ORICHALCUM_IGNOT.get(), 0.7f, 200).group("orichalcum_ignot").unlockedBy("has_orichalcum_blend", ItemInit.ORICHALCUM_BLEND.map(RecipeProvider::has).get()).save(consumer, "orichalcum_ignot_from_smelting");
        SimpleCookingRecipeBuilder.blasting(ItemInit.ORICHALCUM_BLEND.lazyMap(Ingredient::of).get(), RecipeCategory.MISC, ItemInit.ORICHALCUM_IGNOT.get(), 0.7f, 100).group("orichalcum_ignot").unlockedBy("has_orichalcum_blend", ItemInit.ORICHALCUM_BLEND.map(RecipeProvider::has).get()).save(consumer, "orichalcum_ignot_from_blasting");

        generateForEnabledBlockFamilies(consumer);
    }

    public void orichalcumUpgrade(RegistryObject<Item> base, RegistryObject<Item> result, Consumer<FinishedRecipe> consumer) {
        SmithingTransformRecipeBuilder.smithing(
                ItemInit.ORICHALCUM_UPGRADE_SMITHING_TEMPLATE.lazyMap(Ingredient::of).get(),
                        base.lazyMap(Ingredient::of).get(),
                        ItemInit.ORICHALCUM_IGNOT.lazyMap(Ingredient::of).get(),
                        RecipeCategory.MISC,
                        result.lazyMap(ItemLike::asItem).get())
                .unlocks("has_orichalcum_ignot", RecipeProvider.has(ItemInit.ORICHALCUM_IGNOT.get()))
                .save(consumer, result.getId());
    }

    public static SingleItemRecipeBuilder writing(Ingredient ingredient, ItemLike result) {
        return new SingleItemRecipeBuilder(RecipeCategory.MISC, RecipesInit.Serializers.WRITING_SERIALIZER.get(), ingredient, result, 1);
    }

    protected void generateForEnabledBlockFamilies(@NotNull Consumer<FinishedRecipe> consumer) {
        BlockType.getAllFamilies().forEach(arg -> {
            generateRecipes(consumer, arg);
            generateStoneCutterRecipesForFamily(consumer, arg);
        });
    }

    private void generateStoneCutterRecipesForFamily(@NotNull Consumer<FinishedRecipe> consumer, @NotNull BlockFamily family) {
        if (Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(family.getBaseBlock())).toString().contains("planks")) return;
        if (family.getVariants().containsKey(BlockFamily.Variant.SLAB)) stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.SLAB), family.getBaseBlock(), 2);
        if (family.getVariants().containsKey(BlockFamily.Variant.STAIRS)) stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.STAIRS), family.getBaseBlock());
        if (family.getVariants().containsKey(BlockFamily.Variant.WALL)) stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.WALL), family.getBaseBlock());
        if (family.getVariants().containsKey(BlockFamily.Variant.CHISELED)) stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, family.get(BlockFamily.Variant.CHISELED), family.getBaseBlock());
    }
}
