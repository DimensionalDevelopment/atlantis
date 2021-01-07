package com.mystic.atlantis.blocks;


import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;

public class PearlBlocks extends Block
{

    public PearlBlocks(Properties properties) {
        super(properties
                .hardnessAndResistance(2.0F, 5.0F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .setRequiresTool()
                .setLightLevel((state) -> 5)
                .notSolid()
                .sound(SoundType.STONE));
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 125;
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            worldIn.removeBlock(pos, false);
            return;
        }
    }
}
