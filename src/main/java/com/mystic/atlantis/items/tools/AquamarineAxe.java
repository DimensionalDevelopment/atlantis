package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraftforge.common.ToolType;

public class AquamarineAxe extends AxeItem {
    public AquamarineAxe(ToolMaterial tier, float attack) {
        super(tier, attack, -3.2F, new Settings()
                .maxCount(1)
                .maxDamageIfAbsent(tier.getDurability())
                .group(ItemInit.CREATIVE_TAB_ATLANTIS));
    }

}
