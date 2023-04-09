package com.mystic.atlantis.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AtlantianSeaLanternBlock extends Block {
	
    public AtlantianSeaLanternBlock(Properties settings) {
        super(settings
                .strength(2.0F, 10.0F)
                .lightLevel((state) -> 10)
                .requiresCorrectToolForDrops()
                .sound(SoundType.GLASS));
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos targetPos, BlockState targetState, @Nullable BlockEntity targetTileEntity, ItemStack curStack) {
        super.playerDestroy(level, player, targetPos, targetState, targetTileEntity, curStack);
        
        if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, curStack) == 0) {
            level.removeBlock(targetPos, false);
            return;
        }
    }
}
