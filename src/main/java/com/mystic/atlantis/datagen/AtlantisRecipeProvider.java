package com.mystic.atlantis.datagen;

//public class AtlantisRecipeProvider extends RecipeProvider {
//    public AtlantisRecipeProvider(DataGenerator generator) {
//        super(generator);
//    }
//
//    @Override
//    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
//        ShapelessRecipeBuilder.shapeless(ItemInit.LINGUISTIC_GLYPH_SCROLL.get(), 1).requires(Items.INK_SAC).requires(Items.PAPER).requires(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()).unlockedBy("has_fire_melon_spike", RecipeProvider.has(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get())).save(consumer);
//        ShapedRecipeBuilder.shaped(BlockInit.getLinguisticBlock(LinguisticGlyph.BLANK, null).get(), 2).pattern("XX").pattern("XX").define('X', BlockInit.ATLANTEAN_PRISMARINE.get()).unlockedBy("has_atlantean_prismarine", RecipeProvider.has(BlockInit.ATLANTEAN_PRISMARINE.get())).save(consumer);
//        ShapedRecipeBuilder.shaped(BlockInit.WRITING_BLOCK.get())
//                .pattern("XXX")
//                .pattern("YZY")
//                .pattern("XXX")
//                .define('X', ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get())
//                .define('Y', Items.BOOK)
//                .define('Z', Blocks.CRAFTING_TABLE)
//                .unlockedBy("has_fire_melon_spike", RecipeProvider.has(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()))
//                .save(consumer);
//
//        ShapedRecipeBuilder.shaped(BlockInit.LINGUISTIC_BLOCK.get())
//                .pattern("XYX")
//                .pattern("YZY")
//                .pattern("XYX")
//                .define('X', ItemInit.AQUAMARINE_GEM.get())
//                .define('Y', BlockInit.ATLANTEAN_PLANKS.get())
//                .define('Z', Blocks.CRAFTING_TABLE)
//                .unlockedBy("has_atlantean_planks", RecipeProvider.has(BlockInit.ATLANTEAN_PLANKS.get()))
//                .save(consumer);
//        Ingredient ingredient = Ingredient.of(Stream.of(
//                        LinguisticGlyph.values())
//                .map(ItemInit::getScroll)
//                .map(RegistryObject::get)
//                .map(ItemStack::new));
//
//        for(LinguisticGlyph glyph : LinguisticGlyph.values()) {
//            writing(ingredient, ItemInit.getScroll(glyph).get())
////                                .group("glyph_scroll")
//                    .unlockedBy("has_fire_melon_spike", RecipeProvider.has(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()))
//                    .save(consumer, ItemInit.getScroll(glyph).getId().toString() + "_writing");
//        }
//
//        ShapelessRecipeBuilder.shapeless(ItemInit.ORICHALCUM_BLEND.get(), 3)
//                .requires(Items.RAW_COPPER, 6)
//                .requires(Items.RAW_GOLD)
//                .requires(ItemInit.AQUAMARINE_GEM.get())
//                .unlockedBy("has_aquamarine_gem", RecipeProvider.has(ItemInit.AQUAMARINE_GEM.get())).save(consumer);
//
//        ShapedRecipeBuilder.shaped(BlockInit.ORICHALCUM_BLOCK.get())
//                .pattern("XX")
//                .pattern("XX")
//                .define('X', ItemInit.ORICHALCUM_IGNOT.get())
//                .unlockedBy("has_orichalcum_ignot", RecipeProvider.has(ItemInit.ORICHALCUM_IGNOT.get()))
//                .save(consumer);
//
//        orichalcumUpgrade(ItemInit.AQUAMARINE_HELMET, ItemInit.ORICHALCUM_HELMET, consumer);
//        orichalcumUpgrade(ItemInit.AQUAMARINE_CHESTPLATE, ItemInit.ORICHALCUM_CHESTPLATE, consumer);
//        orichalcumUpgrade(ItemInit.AQUAMARINE_LEGGINGS, ItemInit.ORICHALCUM_LEGGINGS, consumer);
//        orichalcumUpgrade(ItemInit.AQUAMARINE_BOOTS, ItemInit.ORICHALCUM_BOOTS, consumer);
//        orichalcumUpgrade(ItemInit.AXE_AQUAMARINE, ItemInit.ORICHALCUM_AXE, consumer);
//        orichalcumUpgrade(ItemInit.PICKAXE_AQUAMARINE, ItemInit.ORICHALCUM_PICKAXE, consumer);
//        orichalcumUpgrade(ItemInit.HOE_AQUAMARINE, ItemInit.ORICHALCUM_HOE, consumer);
//        orichalcumUpgrade(ItemInit.SHOVEL_AQUAMARINE, ItemInit.ORICHALCUM_SHOVEL, consumer);
//        orichalcumUpgrade(ItemInit.SWORD_AQUAMARINE, ItemInit.ORICHALCUM_SWORD, consumer);
//
//        SimpleCookingRecipeBuilder.smelting(ItemInit.ORICHALCUM_BLEND.lazyMap(Ingredient::of).get(), ItemInit.ORICHALCUM_IGNOT.get(), 0.7f, 200).group("orichalcum_ignot").unlockedBy("has_orichalcum_blend", ItemInit.ORICHALCUM_BLEND.map(RecipeProvider::has).get()).save(consumer, "orichalcum_ignot_from_smelting");
//        SimpleCookingRecipeBuilder.blasting(ItemInit.ORICHALCUM_BLEND.lazyMap(Ingredient::of).get(), ItemInit.ORICHALCUM_IGNOT.get(), 0.7f, 100).group("orichalcum_ignot").unlockedBy("has_orichalcum_blend", ItemInit.ORICHALCUM_BLEND.map(RecipeProvider::has).get()).save(consumer, "orichalcum_ignot_from_blasting");
//    }
//
//    public void orichalcumUpgrade(RegistryObject<Item> base, RegistryObject<Item> result, Consumer<FinishedRecipe> consumer) {
//        UpgradeRecipeBuilder.smithing(
//                        base.lazyMap(Ingredient::of).get(),
//                        ItemInit.ORICHALCUM_IGNOT.lazyMap(Ingredient::of).get(),
//                        result.lazyMap(ItemLike::asItem).get())
//                .unlocks("has_orichalcum_ignot", RecipeProvider.has(ItemInit.ORICHALCUM_IGNOT.get()))
//                .save(consumer, result.getId());
//    }
//
//    public static SingleItemRecipeBuilder writing(Ingredient ingredient, ItemLike result) {
//        return new SingleItemRecipeBuilder(RecipesInit.Serializers.WRITING_SERIALIZER.get(), ingredient, result, 1);
//    }
//}
