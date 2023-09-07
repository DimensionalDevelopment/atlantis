package com.mystic.atlantis.items.tools;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class AquamarinePickaxe extends PickaxeItem {
    public AquamarinePickaxe(Tier tier, int attack) {
        super(tier, attack, -3.2F, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses()));
    }
}
