package com.mystic.atlantis.datagen;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class Providers {
    public static void init(IEventBus bus) {
        bus.addListener(Providers::dataGather);
    }

    public static void dataGather(GatherDataEvent event) {
//        if(event.includeServer()) {
//            event.getGenerator().addProvider(true, new AtlantisLootTableProvider(event.getGenerator()));
//            event.getGenerator().addProvider(true, new AtlantisRecipeProvider(event.getGenerator()));
//        }
//
//        if(event.includeClient()) {
//            event.getGenerator().addProvider(true, new AtlantisBlockModelProvider(event.getGenerator(), event.getExistingFileHelper()));
//            event.getGenerator().addProvider(true, new AtlantisBlockStateProvider(event.getGenerator(), event.getExistingFileHelper()));
//            event.getGenerator().addProvider(true, new AtlantisItemModelProvider(event.getGenerator(), event.getExistingFileHelper()));
//            event.getGenerator().addProvider(true, new AtlantisEnglishLanguageProvider(event.getGenerator()));
//
//            BlockTagsProvider blockTagsProvider = new BlockTagsProvider(event.getGenerator(), "atlantis", event.getExistingFileHelper()) {
//                @Override
//                protected void addTags() {
//                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.ORICHALCUM_BLOCK.get());
//                    tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.ORICHALCUM_BLOCK.get());
//                }
//            };
//
//            event.getGenerator().addProvider(true, blockTagsProvider);
//
//            event.getGenerator().addProvider(true, new ItemTagsProvider(event.getGenerator(), blockTagsProvider,"atlantis", event.getExistingFileHelper()) {
//                @Override
//                public void addTags() {
//                    TagAppender<Item> tag = tag(TagsInit.Item.CAN_ITEM_SINK);
//                    TagsInit.Item.getItemsThatCanSink().forEach(itemSupplier -> (tag).add(itemSupplier.get()));
//                }
//            });
        }
//    }
}
