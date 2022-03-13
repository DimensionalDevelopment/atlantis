package com.mystic.atlantis.itemgroup;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AtlantisGroup
{
    public static void init(){
        new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "alantis.general") {
            @Override
            public ItemStack makeIcon() {
                return BlockInit.CHISELED_GOLDEN_AQUAMARINE.get().asItem().getDefaultInstance();
            }

            @Override
            public void fillItemList(NonNullList<ItemStack> stacks) {
                Registry.ITEM.stream().filter((item) -> {
                    return Registry.ITEM.getKey(item).getNamespace().equals("atlantis");
                }).forEach((item) -> stacks.add(new ItemStack(item)));
            }
        };
    }
}
