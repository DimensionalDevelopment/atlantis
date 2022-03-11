package com.mystic.atlantis.blocks;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PearlBlocks extends Block
{

    public PearlBlocks(AbstractBlock.Settings properties) {
        super(properties
                .strength(2.0F, 5.0F)
//                .breakByTool(FabricToolTags.PICKAXES, 1) //TODO: Fix this
                .requiresTool()
                .luminance((state) -> 5)
                .nonOpaque()
                .sounds(BlockSoundGroup.STONE));
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public int getOpacity(BlockState state, BlockView worldIn, BlockPos pos) {
        return 125;
    }

    @Override
    public void afterBreak(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.afterBreak(worldIn, player, pos, state, te, stack);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            worldIn.removeBlock(pos, false);
            return;
        }
    }
}
