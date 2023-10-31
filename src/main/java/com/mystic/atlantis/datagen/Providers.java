package com.mystic.atlantis.datagen;

import com.mystic.atlantis.TagsInit;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Supplier;

public class Providers {
    public static void init(IEventBus bus) {
        bus.addListener(Providers::dataGather);
    }

    public static void dataGather(GatherDataEvent event) {
        var output = event.getGenerator().getPackOutput();


        if(event.includeServer()) {
            event.getGenerator().addProvider(true, new AtlantisLootTableProvider(event.getGenerator()));
            event.getGenerator().addProvider(true, new AtlantisRecipeProvider(output));
        }

        if(event.includeClient()) {
            event.getGenerator().addProvider(true, new AtlantisBlockModelProvider(output, event.getExistingFileHelper()));
            event.getGenerator().addProvider(true, new AtlantisBlockStateProvider(output, event.getExistingFileHelper()));
            event.getGenerator().addProvider(true, new AtlantisItemModelProvider(output, event.getExistingFileHelper()));
            event.getGenerator().addProvider(true, new AtlantisEnglishLanguageProvider(output));

            BlockTagsProvider blockTagsProvider = new BlockTagsProvider(output, event.getLookupProvider(), "atlantis", event.getExistingFileHelper()) {
                @Override
                protected void addTags(HolderLookup.Provider pProvider) {
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.ORICHALCUM_BLOCK.get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.ORICHALCUM_BLOCK.get());
                }
            };

            event.getGenerator().addProvider(true, blockTagsProvider);

            event.getGenerator().addProvider(true, new ItemTagsProvider(output, event.getLookupProvider(), blockTagsProvider.contentsGetter(),"atlantis", event.getExistingFileHelper()) {
                @Override
                protected void addTags(HolderLookup.Provider pProvider) {
                    TagAppender<Item> tag = tag(TagsInit.Item.CAN_ITEM_SINK);
                    TagsInit.Item.getItemsThatCanSink().stream().map(Supplier::get).map(Item::builtInRegistryHolder).map(Holder.Reference::key).forEach(tag::add);
                }
            });
        }
    }
}
