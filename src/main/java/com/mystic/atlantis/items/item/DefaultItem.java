package com.mystic.atlantis.items.item;

import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import net.minecraft.world.item.Item;

/**
 * Legacy {@code ItemBase}.
 */
public class DefaultItem extends Item {
    public DefaultItem(Properties settings) {
        super (settings
                .stacksTo(64));
    }

    public DefaultItem() {
        this(new Properties().tab(AtlantisGroup.INSTANCE));
    }
}
