package com.mystic.atlantis.blocks.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class SunkenGravelBlock extends FallingBlock {

    public SunkenGravelBlock(BlockBehaviour.Properties settings) {
        super(settings
                .sound(SoundType.GRAVEL)
                .strength(0.6F));
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return simpleCodec(SunkenGravelBlock::new);
    }

    @Override
    public int getDustColor(@NotNull BlockState targetState, @NotNull BlockGetter getter, @NotNull BlockPos targetPos) {
        return 0x007F7F;
    }
}
