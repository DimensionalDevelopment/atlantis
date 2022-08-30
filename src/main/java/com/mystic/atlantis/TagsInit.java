package com.mystic.atlantis;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.function.Supplier;

public class TagsInit {
    public static void init() {
        Item.init();
    }

    public static class Item {
        public static TagKey<net.minecraft.world.item.Item> CAN_ITEM_SINK = ItemTags.create(Atlantis.id("can_item_sink"));

        public static Set<Supplier<net.minecraft.world.item.Item>> getItemsThatCanSink() {
            return Set.of(
                    ItemInit.ORICHALCUM_BLEND,
                    ItemInit.ORICHALCUM_IGNOT,
                    ItemInit.ORICHALCUM_AXE,
                    ItemInit.ORICHALCUM_PICKAXE,
                    ItemInit.ORICHALCUM_SHOVEL,
                    ItemInit.ORICHALCUM_HOE,
                    ItemInit.ORICHALCUM_SWORD,
                    ItemInit.ORICHALCUM_HELMET,
                    ItemInit.ORICHALCUM_CHESTPLATE,
                    ItemInit.ORICHALCUM_LEGGINGS,
                    ItemInit.ORICHALCUM_BOOTS,
                    BlockInit.ORICHALCUM_BLOCK.lazyMap(Block::asItem));
        }

        public static void init() {

        }
    }
}
