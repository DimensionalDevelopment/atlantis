package com.mystic.atlantis.items.item;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.item.Item;

/**
 * Legacy {@code ItemBase}.
 */
public class DefaultItem extends Item {
    public DefaultItem(Settings settings) {
        super (settings
                .maxCount(64));
    }

    public DefaultItem() {
        this(new Settings());
    }
}
