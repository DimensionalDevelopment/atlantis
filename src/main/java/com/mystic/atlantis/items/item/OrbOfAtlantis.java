package com.mystic.atlantis.items.item;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class OrbOfAtlantis extends ItemBase{

    public OrbOfAtlantis() {
        new Settings()
                .maxCount(64);
    }

        @Override
        public TypedActionResult<ItemStack> use (World worldIn, PlayerEntity playerIn, Hand handIn){

        ItemStack itemstack = playerIn.getStackInHand(handIn);
        if (!playerIn.abilities.creativeMode) {
            itemstack.decrement(1);
        }

        return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, itemstack);

    }
}
