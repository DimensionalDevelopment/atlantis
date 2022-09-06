package com.mystic.atlantis.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SunkenGravel extends GravelBlock {

    public SunkenGravel(Properties properties) {
        super(properties
                .sound(SoundType.GRAVEL)
                .strength(0.6F));
    }

    @Override
    public int getDustColor(@NotNull BlockState arg, @NotNull BlockGetter arg2, @NotNull BlockPos arg3) {
        return 0x007F7F;
    }
}
