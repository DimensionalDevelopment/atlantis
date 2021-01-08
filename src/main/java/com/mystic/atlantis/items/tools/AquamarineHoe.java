package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public class AquamarineHoe extends HoeItem {
    public AquamarineHoe(ToolMaterial tier, int attack) {
        super(tier, attack, -3.2F, new Settings()
                .maxCount(1)
                .maxDamageIfAbsent(tier.getDurability())
                .group(ItemInit.CREATIVE_TAB_ATLANTIS));
    }
}
