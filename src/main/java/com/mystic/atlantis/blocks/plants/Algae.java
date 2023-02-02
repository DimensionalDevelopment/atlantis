package com.mystic.atlantis.blocks.plants;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class Algae extends GlowLichenBlock {

    public Algae(Properties properties) {
        super(properties
                .randomTicks()
                .strength(0.2F, 0.4F)
                .sound(SoundType.GRASS)
                .requiresCorrectToolForDrops()
                .noCollission()
                .noOcclusion()
                .lightLevel((value) -> 0));
        ComposterBlock.COMPOSTABLES.put(this, 0.5f);
    }



    @Override
    public boolean isValidStateForPlacement(BlockGetter p_221572_, BlockState p_221573_, BlockPos p_221574_, Direction p_221575_) {
        if (isWater(p_221573_) && (this.isFaceSupported(p_221575_) && (!p_221573_.is(this) || !hasFace(p_221573_, p_221575_)))) {
            BlockPos blockpos = p_221574_.relative(p_221575_);
            return canAttachTo(p_221572_, p_221575_, blockpos, p_221572_.getBlockState(blockpos));
        } else {
            return false;
        }
    }

    public static boolean isWater(BlockState state)
    {
        return state.getMaterial() == Material.WATER;
    }
}
