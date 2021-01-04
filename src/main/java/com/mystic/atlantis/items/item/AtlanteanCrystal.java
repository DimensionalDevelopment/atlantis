package com.mystic.atlantis.items.item;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AtlanteanCrystal extends ItemBase{

        public AtlanteanCrystal() {
            new Properties()
                    .maxStackSize(16);
        }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.abilities.isCreativeMode)
        {
            if(playerIn.getHealth() < playerIn.getMaxHealth())
            {
                playerIn.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, 1).getEffectInstance());
                itemstack.shrink(1);
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
    }
}
