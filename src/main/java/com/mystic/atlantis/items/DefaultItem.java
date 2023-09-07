package com.mystic.atlantis.items;

import net.minecraft.world.item.Item;

/**
 * Legacy {@code ItemBase}.
 */
public class DefaultItem extends Item {
    public DefaultItem(Properties settings) {
        super (settings);
    }

    public DefaultItem() {
        this(new Properties());
    }
}
