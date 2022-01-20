package com.mystic.atlantis.items.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WaterPill extends DefaultItem {
    public WaterPill() {
        super(new Settings().maxCount(16));
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack itemstack = playerIn.getStackInHand(handIn);

        if (!playerIn.getAbilities().creativeMode) {
            for (int x = 300; x >= 0; x--) {
                if (playerIn.isSubmergedInWater()) {
                    playerIn.setAir(300);
                } else {
                    playerIn.setAir(x);
                }
                itemstack.decrement(1);
            }
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, itemstack);
    }
}
