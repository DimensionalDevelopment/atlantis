package com.mystic.atlantis.items.item;

import com.mystic.atlantis.init.AtlantisGroupInit;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WaterPill extends DefaultItem {
    public WaterPill() {
        super(new Properties().stacksTo(16).tab(AtlantisGroupInit.MAIN));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        ItemStack itemstack = playerIn.getItemInHand(handIn);

        if (!playerIn.getAbilities().instabuild) {
            for (int x = 300; x >= 0; x--) {
                if (playerIn.isUnderWater()) {
                    playerIn.setAirSupply(300);
                } else {
                    playerIn.setAirSupply(x);
                }
                itemstack.shrink(1);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}
