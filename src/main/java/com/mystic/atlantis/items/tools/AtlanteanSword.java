package com.mystic.atlantis.items.tools;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class AtlanteanSword extends SwordItem {
    public AtlanteanSword(Tier tier, int attack) {
        super(tier, new Properties().attributes(createAttributes(tier, attack, -3.2F))
                .stacksTo(1)
                .durability(tier.getUses()));
    }
}