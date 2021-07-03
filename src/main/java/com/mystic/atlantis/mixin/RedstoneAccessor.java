package com.mystic.atlantis.mixin;

import net.minecraft.block.RedstoneWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RedstoneWireBlock.class)
public interface RedstoneAccessor {
    @Accessor("wiresGivePower")
    boolean getWiresGivePower();

    @Accessor("wiresGivePower")
    void setWiresGivePower(boolean wiresGivePower);
}
