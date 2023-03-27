package com.mystic.atlantis.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.block.RedStoneWireBlock;

@Mixin(RedStoneWireBlock.class)
public interface RedstoneAccessor {
    @Accessor("shouldSignal")
    boolean getShouldSignal();

    @Accessor("shouldSignal")
    void setShouldSignal(boolean shouldSignal);
}
