package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.init.RecipesInit;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.Modifier;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class Providers {
    public static void init(IEventBus bus) {
        bus.addListener(Providers::dataGather);
    }

    public static void dataGather(GatherDataEvent event) {
        if(event.includeServer()) {
            event.getGenerator().addProvider(new AtlantisLootTables(event.getGenerator()));
            event.getGenerator().addProvider(new RecipeProvider(event.getGenerator()) {
                @Override
                protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
                    ShapelessRecipeBuilder.shapeless(ItemInit.LINGUISTIC_GLYPH_SCROLL.get(), 1).requires(Items.INK_SAC).requires(Items.PAPER).requires(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()).unlockedBy("has_fire_melon_spike", RecipeProvider.has(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get())).save(consumer);
                    ShapedRecipeBuilder.shaped(BlockInit.getLinguisticBlock(LinguisticGlyph.BLANK, null).get(), 2).pattern("XX").pattern("XX").define('X', BlockInit.ATLANTEAN_PRISMARINE.get()).unlockedBy("has_atlantean_prismarine", RecipeProvider.has(BlockInit.ATLANTEAN_PRISMARINE.get())).save(consumer);
                    ShapedRecipeBuilder.shaped(BlockInit.WRITING_BLOCK.get())
                            .pattern("XXX")
                            .pattern("YZY")
                            .pattern("XXX")
                            .define('X', ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get())
                            .define('Y', Items.BOOK)
                            .define('Z', Blocks.CRAFTING_TABLE)
                            .unlockedBy("has_fire_melon_spike", RecipeProvider.has(ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()))
                            .save(consumer);

                    ShapedRecipeBuilder.shaped(BlockInit.LINGUISTIC_BLOCK.get())
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
                                .save(consumer, "atlantis:" + ItemInit.getScroll(glyph).get().getRegistryName().getPath() + "_writing");
                    }
                }

                public static SingleItemRecipeBuilder writing(Ingredient ingredient, ItemLike result) {
                    return new SingleItemRecipeBuilder(RecipesInit.Serializers.WRITING_SERIALIZER.get(), ingredient, result, 1);
                }
            });
        }

        if(event.includeClient()) {
            event.getGenerator().addProvider(new BlockModelProvider(event.getGenerator(), "atlantis", event.getExistingFileHelper()) {
                @Override
                protected void registerModels() {
                    this.cubeBottomTop("writing_block", Atlantis.id("block/writing_table_side"), Atlantis.id("block/atlantean_planks"), Atlantis.id("block/writing_table_top"));
                }
            });

            event.getGenerator().addProvider(new BlockStateProvider(event.getGenerator(), "altantis", event.getExistingFileHelper()) {
                @Override
                protected void registerStatesAndModels() {
                    this.horizontalBlock(BlockInit.WRITING_BLOCK.get(), new ModelFile.ExistingModelFile(Atlantis.id("block/writing_block"), event.getExistingFileHelper()));
                }
            });

            event.getGenerator().addProvider(new ItemModelProvider(event.getGenerator(), "atlantis", event.getExistingFileHelper()) {

                @Override
                protected void registerModels() {
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_A);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_B);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_C);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_D);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_E);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_F);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_G);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_H);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_I);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_J);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_K);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_L);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_M);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_N);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_O);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_P);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_Q);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_R);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_S);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_T);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_U);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_V);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_W);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_X);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_Y);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_Z);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_0);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_1);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_2);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_3);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_4);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_5);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_6);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_7);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_8);
                    glyphScroll(ItemInit.LINGUISTIC_GLYPH_SCROLL_9);

                    for(LinguisticGlyph glyph : LinguisticGlyph.values()) {
                        for(DyeColor color : DyeColor.values()) {
                            withParent(BlockInit.getLinguisticBlock(glyph, color), glyph);
                        }

                        withParent(BlockInit.getLinguisticBlock(glyph, null), glyph);
                    }

                    block(BlockInit.WRITING_BLOCK);
                }

                private void withParent(RegistryObject<Block> block, LinguisticGlyph glyph) {
                        withExistingParent(block.getId().getPath(), block(Atlantis.id("linguistic_" + glyph.name().toLowerCase())));

                }

                private void block(RegistryObject<Block> block) {
                    withExistingParent(block.getId().getPath(), block(block.getId()));
                }

                private void glyphScroll(RegistryObject<Item> block) {
                try {
                        getBuilder(block.getId().getPath())
                                .parent(getExistingFile(mcLoc("item/generated")))
                                .texture("layer0", items(block.getId()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                }

                private ResourceLocation block(ResourceLocation location) {
                    return new ResourceLocation(location.getNamespace(), "block/" + location.getPath());
                }


                private ResourceLocation items(ResourceLocation location) {
                    return new ResourceLocation(location.getNamespace(), "items/" + location.getPath());
                }
            });



            if(false) { //TODO Convert lang to pure data gen in the future. This will complain about duplicates if run without removing the existing en_us.json first
                event.getGenerator().addProvider(new LanguageProvider(event.getGenerator(), "atlantis", "en_us") {
                    @Override
                    protected void addTranslations() {
                        Consumer<RegistryObject<Item>> itemConsumer = adjust(this::addItem);

                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_A);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_B);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_C);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_D);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_E);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_F);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_G);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_H);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_I);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_J);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_K);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_L);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_M);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_N);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_O);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_P);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_Q);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_R);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_S);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_T);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_U);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_V);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_W);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_X);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_Z);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_0);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_1);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_2);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_3);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_4);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_5);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_6);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_7);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_8);
                        itemConsumer.accept(ItemInit.LINGUISTIC_GLYPH_SCROLL_9);

                        Consumer<RegistryObject<Block>> blockConsumer = adjust(this::addBlock);

                        for (LinguisticGlyph glyph : LinguisticGlyph.values()) {
                            for (DyeColor color : DyeColor.values()) {
                                blockConsumer.accept(BlockInit.getLinguisticBlock(glyph, color));
                            }

                            blockConsumer.accept(BlockInit.getLinguisticBlock(glyph, null));
                        }

//                    generateEntries(ItemInit.ITEMS, this::addItem);
//                    generateEntries(BlockInit.BLOCKS, this::addBlock);
//                    generateEntries(AtlantisEntities.ENTITIES, this::addEntityType);
                    }

                    private <K extends IForgeRegistryEntry<K>> void generateEntries(DeferredRegister<K> register, BiConsumer<RegistryObject<K>, String> consumer) {
                        register.getEntries().forEach(adjust(consumer));
                    }

                    private <K extends IForgeRegistryEntry<K>> Consumer<RegistryObject<K>> adjust(BiConsumer<RegistryObject<K>, String> consumer) {
                        return ((k) -> consumer.accept(k, WordUtils.capitalizeFully(k.getId().getPath().replace("_", " "))));
                    }

                    private <K extends IForgeRegistryEntry<K>> void generateEntries(Class<?> clazz, BiConsumer<RegistryObject<K>, String> consumer) {
                        stream(ItemInit.class.getDeclaredFields()).filter(a -> !Modifier.isPrivate(a.getModifiers())).map(a -> {
                                    try {
                                        return a.get(null);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                }).filter(RegistryObject.class::isInstance)
                                .map(obj -> (RegistryObject<K>) obj)
                                .forEach(a -> {
                                    consumer.accept(a, WordUtils.capitalizeFully(a.getId().getPath().replace("_", " ")));
                                });
                    }
                });
            }
        }
    }
}
