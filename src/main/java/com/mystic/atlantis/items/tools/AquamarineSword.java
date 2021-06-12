package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class AquamarineSword extends SwordItem {
    public AquamarineSword(ToolMaterial tier, int attack) {
        super(tier, attack, -3.2F, new Item.Settings()
                .maxCount(1)
                .maxDamageIfAbsent(tier.getDurability()));
    }
}
