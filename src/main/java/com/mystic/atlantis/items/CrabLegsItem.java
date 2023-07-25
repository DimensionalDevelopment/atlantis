package com.mystic.atlantis.items;

import net.minecraft.world.food.FoodProperties;

public class CrabLegsItem extends DefaultItem
{
    public CrabLegsItem(Properties settings) {
        super(settings.stacksTo(64).food(new FoodProperties.Builder().meat().nutrition(6).saturationMod(0.5f).build()));
    }
}
