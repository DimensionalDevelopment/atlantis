package com.mystic.atlantis.items.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OrbOfAtlantis extends ItemBase
{
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode)
        {
            itemstack.shrink(1);
        }

        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);

    }
}
