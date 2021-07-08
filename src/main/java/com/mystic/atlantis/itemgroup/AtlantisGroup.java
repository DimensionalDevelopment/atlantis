package com.mystic.atlantis.itemgroup;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.init.BlockInit;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class AtlantisGroup
{
    public static void init(){
        FabricItemGroupBuilder.create(Main.id("general")).icon(() -> BlockInit.CHISELED_GOLDEN_AQUAMARINE.asItem().getDefaultStack()).appendItems((stacks) -> {
            Registry.ITEM.stream().filter((item) -> {
                return Registry.ITEM.getId(item).getNamespace().equals("atlantis");
            }).forEach((item) -> stacks.add(new ItemStack(item)));
        }).build();
    }
}
