package com.mystic.atlantis.items;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class SodiumItem extends Item {
    public SodiumItem(Item.Properties properties) {
        super(properties
                .stacksTo(64)
                .fireResistant());
    }

    @Override
    public boolean canBeHurtBy(@NotNull DamageSource arg) {
        return !(arg.is(DamageTypes.LIGHTNING_BOLT));
    }
}
