package com.mystic.atlantis.items.item;

import com.mystic.atlantis.itemgroup.AtlantisGroup;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SodiumItem extends Item {
    public SodiumItem(Item.Properties properties) {
        super(properties
                .stacksTo(64)
                .fireResistant()
                .tab(AtlantisGroup.MAIN));
    }

}
