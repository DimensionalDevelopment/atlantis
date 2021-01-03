package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class AquamarineSword extends SwordItem {
    public AquamarineSword(IItemTier tier, int attack) {
        super(tier, attack, -3.2F, new Item.Properties()
                .maxStackSize(1)
                .defaultMaxDamage(tier.getMaxUses())
                .group(ItemInit.CREATIVE_TAB_ATLANTIS));
    }
}
