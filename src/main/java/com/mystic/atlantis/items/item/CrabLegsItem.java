package com.mystic.atlantis.items.item;

import net.minecraft.world.food.FoodProperties;

public class CrabLegsItem extends DefaultItem
{
    public CrabLegsItem(Properties settings) {
        super(settings.stacksTo(64).food(new FoodProperties.Builder().meat().nutrition(6).saturationMod(10.0f).build()));
    }
}
