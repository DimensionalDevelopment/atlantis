package com.mystic.atlantis.items.item;

import com.mystic.atlantis.itemgroup.AtlantisGroup;
import com.mystic.atlantis.items.item.energybased.AtlanteanAmuletItem;
import com.mystic.atlantis.items.item.energybased.AtlanteanSpearItem;
import net.minecraft.world.item.Item;
import org.reflections.Reflections;

/**
 * Legacy {@code ItemBase}.
 */
public class DefaultItem extends Item {
    public DefaultItem(Properties settings) {
        super (settings);
    }

    public DefaultItem() {
        this(new Properties().tab(AtlantisGroup.MAIN));
    }
}
