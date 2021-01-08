package com.mystic.atlantis.items.item;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase() {
        super (new Settings()
                .maxCount(64)
                .group(ItemInit.CREATIVE_TAB_ATLANTIS));
    }
}
