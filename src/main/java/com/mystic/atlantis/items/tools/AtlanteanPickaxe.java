package com.mystic.atlantis.items.tools;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class AtlanteanPickaxe extends PickaxeItem {
    public AtlanteanPickaxe(Tier tier, int attack) {
        super(tier, new Properties().attributes(createAttributes(tier, attack, 3.2F))
                .stacksTo(1)
                .durability(tier.getUses()));
    }
}