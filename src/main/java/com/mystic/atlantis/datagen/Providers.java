package com.mystic.atlantis.datagen;

import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.commons.lang3.text.WordUtils;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.TagsInit;
import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.init.AtlantisEntityInit;
import com.mystic.atlantis.init.AtlantisGroupInit;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.EffectsInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.init.RecipesInit;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
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
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class Providers {
    public static void init(IEventBus bus) {
        bus.addListener(Providers::dataGather);
    }

    public static void dataGather(GatherDataEvent event) {
        if(event.includeServer()) {
            event.getGenerator().addProvider(true, new AtlantisLootTableProvider(event.getGenerator()));
            event.getGenerator().addProvider(true, new AtlantisRecipeProvider(event.getGenerator()));
        }

        if(event.includeClient()) {
            event.getGenerator().addProvider(true, new AtlantisBlockModelProvider(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(true, new AtlantisBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(true, new AtlantisItemModelProvider(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(true, new AtlantisEnglishLanguageProvider(event.getGenerator()));

            BlockTagsProvider blockTagsProvider = new BlockTagsProvider(event.getGenerator(), "atlantis", event.getExistingFileHelper()) {
                @Override
                protected void addTags() {
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.ORICHALCUM_BLOCK.get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.ORICHALCUM_BLOCK.get());
                }
            };

            event.getGenerator().addProvider(true, blockTagsProvider);

            event.getGenerator().addProvider(true, new ItemTagsProvider(event.getGenerator(), blockTagsProvider,"atlantis", event.getExistingFileHelper()) {
                @Override
                public void addTags() {
                    TagAppender<Item> tag = tag(TagsInit.Item.CAN_ITEM_SINK);
                    TagsInit.Item.getItemsThatCanSink().forEach(itemSupplier -> (tag).add(itemSupplier.get()));
                }
            });
        }
    }
}
