package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraftforge.common.ToolType;

public class AquamarineAxe extends AxeItem {
    public AquamarineAxe(IItemTier tier, float attack) {
        super(tier, attack, -3.2F, new Properties()
                .maxStackSize(1)
                .defaultMaxDamage(tier.getMaxUses())
                .group(ItemInit.CREATIVE_TAB_ATLANTIS));
    }

}
