package com.mystic.atlantis.tabs;

import com.mystic.atlantis.init.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AtlantisTab{

    public static final CreativeTab ATLANTIS_TAB = new CreativeTab("Atlantis") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Item.getItemFromBlock(ModBlocks.OCEAN_LANTERN));
        }
    };

}
