package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class AquamarinePickaxe extends PickaxeItem {
    public AquamarinePickaxe(ToolMaterial tier, int attack) {
        super(tier, attack, -3.2F, new Settings()
                .maxCount(1)
                .maxDamageIfAbsent(tier.getDurability()));
    }
}
