package com.mystic.atlantis.items;

import org.jetbrains.annotations.NotNull;

import com.mystic.atlantis.init.AtlantisGroupInit;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;

public class SodiumItem extends Item {
    public SodiumItem(Item.Properties properties) {
        super(properties
                .stacksTo(64)
                .fireResistant()
                .tab(AtlantisGroupInit.MAIN));
    }

    @Override
    public boolean canBeHurtBy(@NotNull DamageSource arg) {
        return !(arg == DamageSource.LIGHTNING_BOLT);
    }
}
