package com.mystic.atlantis.items.food;

import com.mystic.atlantis.items.DefaultItem;
import net.minecraft.world.food.FoodProperties;

public class CrabLegsItem extends DefaultItem
{
    public CrabLegsItem(Properties settings) {
        super(settings.stacksTo(64).food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5f).build()));
    }
}
