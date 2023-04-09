package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.init.AtlantisGroupInit;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class AquamarineAxe extends AxeItem {
    public AquamarineAxe(Tier tier, float attack) {
        super(tier, attack, -3.2F, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses())
                .tab(AtlantisGroupInit.MAIN));
    }

}
