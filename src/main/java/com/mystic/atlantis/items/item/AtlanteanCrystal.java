package com.mystic.atlantis.items.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AtlanteanCrystal extends ItemBase{

        public AtlanteanCrystal() {
            new Settings()
                    .maxCount(16);
        }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getStackInHand(handIn);

        if (!playerIn.getAbilities().creativeMode)
        {
            if(playerIn.getHealth() < playerIn.getMaxHealth())
            {
                playerIn.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1));
                itemstack.decrement(1);
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, itemstack);
    }
}
