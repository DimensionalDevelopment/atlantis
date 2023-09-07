package com.mystic.atlantis.items.item.energybased;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AtlanteanCrystal extends Item {
    public AtlanteanCrystal() {
        super(new Properties().stacksTo(32));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
    {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        if (!playerIn.getAbilities().instabuild)
        {
            if(playerIn.getHealth() < playerIn.getMaxHealth())
            {
                playerIn.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 1));
                itemstack.shrink(1);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}
