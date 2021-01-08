package com.mystic.atlantis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class AquamarineOre extends Block
{
    public AquamarineOre(Settings properties)
    {
        super(properties
            .sounds(BlockSoundGroup.STONE)
            .harvestLevel(2)
            .harvestTool(ToolType.PICKAXE)
            .requiresTool()
            .strength(3.0F, 15.0F)
            .luminance((state) -> 2));
    }
}
