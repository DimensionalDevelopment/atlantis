package com.mystic.atlantis.items.tools;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class AquamarineHoe extends HoeItem {
    public AquamarineHoe(Tier tier, int attack) {
        super(tier, attack, -3.2F, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses()));
    }
}
