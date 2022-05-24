package com.mystic.atlantis;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Set;

public class TagsInit {
    public static void init() {
        Item.init();
    }

    public static class Item {
        public static Tags.IOptionalNamedTag<net.minecraft.world.item.Item> CAN_ITEM_SINK = ItemTags.createOptional(Atlantis.id("can_item_sink"), Set.of(ItemInit.ORICHALCUM_BLEND, ItemInit.ORICHALCUM_IGNOT, BlockInit.ORICHALCUM_BLOCK.lazyMap(Block::asItem)));

        public static void init() {}
    }
}
