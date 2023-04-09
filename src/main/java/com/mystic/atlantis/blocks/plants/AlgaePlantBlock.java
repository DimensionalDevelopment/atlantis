package com.mystic.atlantis.blocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class AlgaePlantBlock extends GlowLichenBlock {

    public AlgaePlantBlock(Properties settings) {
        super(settings
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
    public boolean isValidStateForPlacement(BlockGetter getter, BlockState targetState, BlockPos targetPos, Direction currentDir) {
        if (isWaterAt(targetState) && (this.isFaceSupported(currentDir) && (!targetState.is(this) || !hasFace(targetState, currentDir)))) {
            BlockPos blockpos = targetPos.relative(currentDir);
            return canAttachTo(getter, currentDir, blockpos, getter.getBlockState(blockpos));
        } else {
            return false;
        }
    }

    public static boolean isWaterAt(BlockState targetState) {
        return targetState.getMaterial() == Material.WATER;
    }
}
