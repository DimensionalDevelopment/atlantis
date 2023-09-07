package com.mystic.atlantis.blocks.base;


import static com.mystic.atlantis.blocks.base.AtlanteanWoodDoorBlock.WATERLOGGED;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class PearlBlock extends HalfTransparentBlock implements SimpleWaterloggedBlock {

    public PearlBlock(Properties settings) {
        super(settings
                .strength(2.0F, 5.0F)
                .requiresCorrectToolForDrops()
                .lightLevel((state) -> 5)
                .noOcclusion()
                .sound(SoundType.STONE));
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, true));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState targetState) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState targetState, BlockGetter getter, BlockPos targetPos) {
        return 125;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos targetPos, BlockState targetState, @Nullable BlockEntity targetTileEntity, ItemStack curStack) {
        super.playerDestroy(level, player, targetPos, targetState, targetTileEntity, curStack);
        
        if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, curStack) == 0) {
            level.removeBlock(targetPos, false);
            return;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}
