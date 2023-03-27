package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.itemgroup.AtlantisGroup;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

public class AquamarineShovel extends ShovelItem {
    public AquamarineShovel(Tier tier, int attack) {
        super(tier, attack, -3.2F, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses())
                .tab(AtlantisGroup.MAIN));
    }
}
