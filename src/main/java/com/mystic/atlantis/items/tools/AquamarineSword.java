package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.itemgroup.AtlantisGroup;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class AquamarineSword extends SwordItem {
    public AquamarineSword(Tier tier, int attack) {
        super(tier, attack, -3.2F, new Item.Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses())
                .tab(AtlantisGroup.MAIN));
    }
}
