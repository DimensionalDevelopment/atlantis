package com.mystic.atlantis.blocks;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class SunkenGravelBlock extends GravelBlock {

    public SunkenGravelBlock(Properties settings) {
        super(settings
                .sound(SoundType.GRAVEL)
                .strength(0.6F));
    }

    @Override
    public int getDustColor(@NotNull BlockState targetState, @NotNull BlockGetter getter, @NotNull BlockPos targetPos) {
        return 0x007F7F;
    }
}
