package com.mystic.atlantis.items.item.bottles;

import com.mystic.atlantis.itemgroup.AtlantisGroup;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class JellyfishJellyBottle extends Item {
    private static final int DRINK_DURATION = 30;

    public JellyfishJellyBottle(Properties arg) {
        super(arg.tab(AtlantisGroup.INSTANCE));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack arg, Level arg2, LivingEntity arg3) {
        super.finishUsingItem(arg, arg2, arg3);
        if (arg3 instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) arg3;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, arg);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (arg.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (arg3 instanceof Player && !((Player) arg3).getAbilities().instabuild) {
                ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
                Player player = (Player) arg3;
                if (!player.getInventory().add(itemStack)) {
                    player.drop(itemStack, false);
                }
                arg.shrink(1);
            }

            return arg;
        }
    }

    @Override
    public int getUseDuration(ItemStack arg) {
        return 30;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack arg) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level arg, Player arg2, InteractionHand arg3) {
        return ItemUtils.startUsingInstantly(arg, arg2, arg3);
    }
}
