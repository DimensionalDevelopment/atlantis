package com.mystic.atlantis.items.tools;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

public class AquamarineAxe extends AxeItem {
    public AquamarineAxe(ToolMaterial tier, float attack) {
        super(tier, attack, -3.2F, new Settings()
                .maxCount(1)
                .maxDamageIfAbsent(tier.getDurability()));
    }

}
