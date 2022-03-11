package com.mystic.atlantis.itemgroup;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class AtlantisGroup
{
    public static void init(){
        new ItemGroup(ItemGroup.getGroupCountSafe(), "alantis.general") {
            @Override
            public ItemStack createIcon() {
                return BlockInit.CHISELED_GOLDEN_AQUAMARINE.asItem().getDefaultStack();
            }

            @Override
            public void appendStacks(DefaultedList<ItemStack> stacks) {
                Registry.ITEM.stream().filter((item) -> {
                    return Registry.ITEM.getId(item).getNamespace().equals("atlantis");
                }).forEach((item) -> stacks.add(new ItemStack(item)));
            }
        };
    }
}
