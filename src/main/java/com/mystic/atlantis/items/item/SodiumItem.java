package com.mystic.atlantis.items.item;

import org.jetbrains.annotations.NotNull;

import com.mystic.atlantis.itemgroup.AtlantisGroup;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;

public class SodiumItem extends Item {
    public SodiumItem(Item.Properties properties) {
        super(properties
                .stacksTo(64)
                .fireResistant()
                .tab(AtlantisGroup.MAIN));
    }

    @Override
    public boolean canBeHurtBy(@NotNull DamageSource arg) {
        return !(arg == DamageSource.LIGHTNING_BOLT);
    }
}
