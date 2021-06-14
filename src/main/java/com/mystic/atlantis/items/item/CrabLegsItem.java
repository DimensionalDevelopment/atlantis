package com.mystic.atlantis.items.item;

import net.minecraft.item.FoodComponent;

public class CrabLegsItem extends DefaultItem
{
    public CrabLegsItem(Settings settings) {
        super(settings.food(new FoodComponent.Builder().meat().hunger(6).saturationModifier(10.0f).build()));
    }
}
