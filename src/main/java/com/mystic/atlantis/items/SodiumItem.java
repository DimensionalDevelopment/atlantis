package com.mystic.atlantis.items;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SodiumItem extends Item {
    public SodiumItem(Item.Properties properties) {
        super(properties
                .stacksTo(64)
                .fireResistant());
    }

    @Override
    public boolean canBeHurtBy(ItemStack stack, DamageSource source) {
        return !(source.is(DamageTypes.LIGHTNING_BOLT));
    }
}
