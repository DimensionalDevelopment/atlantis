package com.mystic.atlantis.items.food;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;

public class CoconutSliceItem extends BlockItem {
    public CoconutSliceItem(Properties settings) {
        super(BlockInit.COCONUT_SLICE.get(), settings.stacksTo(64).food(new FoodProperties.Builder().nutrition(4).saturationModifier(2.4f).build()));
    }
}
