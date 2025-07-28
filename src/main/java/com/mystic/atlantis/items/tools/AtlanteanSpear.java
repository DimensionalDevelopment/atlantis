package com.mystic.atlantis.items.tools;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class AtlanteanSpear extends SwordItem {
    public AtlanteanSpear(Tier tier, Properties properties) {
        super(tier, properties
                .stacksTo(1));
    }
}